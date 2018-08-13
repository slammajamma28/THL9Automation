package com.pst.slamma;

import com.jaunt.*;
import com.jaunt.component.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrapePSNPGamePage {

    private DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy h:mm:ss a");
    private LocalDateTime NOW = LocalDateTime.now();
    private Month JULY = Month.JULY;

    public boolean checkGameCompletion(Game game, String psn) {
        Element main_area;
        Element game_bar;
        Element base_game_bar;
        String url = game.getUrl() + psn;
        //Begin jaunt stuff
        try {
            UserAgent userAgent = new UserAgent();
            userAgent.visit(url);

            // Check for platinum in main bar
            main_area = userAgent.doc.findFirst("<div class='col-xs'>");
            game_bar = main_area.findFirst("<tr>");
            String completion_type = game_bar.getAt("class").trim();

            if (completion_type.equals("platinum") || completion_type.equals("completed")) {
                System.out.println("Game completed: " + url);
//                        game.setStarted_during_thl(true);
                // Now check if it started before THL
            } else {
                // If no platinum or completed, check to see if basegame bar exists

                // check for base game bar for 100%
                base_game_bar = main_area.findFirst("<div class='box no-top-border'>").findFirst("<tr>");

                if (base_game_bar.findFirst("<span class='title'>").innerText().equals("Base Game")) {
                    if (base_game_bar.getAt("class").trim().equals("completed")) {
                        System.out.println("Base game completed: " + url);
//                        game.setStarted_during_thl(true);
                        // Now check if it started before THL
                    }
                }
            }
        } catch (NotFound nf) {
            // Typically caught if this is a game without DLC
            // Indicates that the 100% hasn't been achieved on the base game
            System.out.println("Game is incomplete: " + url);
//            game.setStarted_during_thl(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Game is incomplete: " + url);
        return false;
//        game.setStarted_during_thl(true);
    }

    public boolean wasStartedBeforeTHL(UserAgent userAgent) {
        Element backlog_box;

        try {
            backlog_box = userAgent.doc.findFirst("<div class='col-xs-4 col-xs-max-320'>").findFirst("<table class='box zebra'>");
            try {
                Elements backlog_rows = backlog_box.findEvery("<tr>");
                for (Element row : backlog_rows) {
                    Elements info = row.findEvery("<td>").getElement(0).findEvery("<td>");
                    String date = info.findFirst("<nobr>").getChildText().trim().replaceAll("st","").replaceAll("nd","").replaceAll("rd", "").replaceAll("th","");
                    LocalDate trophyDate = LocalDate.parse(date,DateTimeFormatter.ofPattern("d MMM yyyy"));
                    if (trophyDate.isBefore(LocalDate.parse("01 Jun 2018",DateTimeFormatter.ofPattern("d MMM yyyy")))) {
//                        game.setStarted_during_thl(false);
                        return false;
                    } else {
//                        game.setStarted_during_thl(true);
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (NotFound nfe) {
            System.out.println("ERROR: Could not find backlog box");
        }
        return false;
    }

    public Game pullGameInfo(Game game, String psn) {
        UserAgent userAgent = new UserAgent();
        Element stat_bar, game_name_bar, game_info_box, backlog_box;
        Elements trophies;
        Table backlog_table;
        try {
            userAgent.visit(game.getUrl() + psn + "?order=date");
        } catch (ResponseException re) {
            System.out.println("ERROR: Could not reach " + game.getUrl());
        }

        try {
            stat_bar = userAgent.doc.findFirst("<div class='stats flex'>");
            try {
                Elements stats_elements = stat_bar.findEach("<span class='stat grow'>");
                game.setGame_owners(Integer.parseInt(stats_elements.getElement(0).getChildText().replaceAll(",","")));
                try {
                    if (stats_elements.getElement(2).findFirst("<span>").getChildText().startsWith("Platinum")) {
                        game.setPlat_game(true);
                    } else {
                        game.setPlat_game(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    game.setPlat_game(false);
                }
            } catch (NotFound nfe2) {
                game.setGame_owners(0);
            } catch (Exception e) {
                e.printStackTrace();
                game.setGame_owners(0);
            }
        } catch (NotFound nfe) {
            System.out.println("ERROR: Could not pull stats bar for " + game.getUrl());
        }

        try {
            game_name_bar = userAgent.doc.findFirst("<div class='title-bar flex v-align'>")
                    .findFirst("<div class='grow'>")
                    .findFirst("<h3>");
            try {
                game.setGame_name(game_name_bar.getChildText());
            } catch (Exception e) {
                game.setGame_name("ISSUE FINDING NAME");
            }
        } catch (NotFound nfe) {
            game.setGame_name("ISSUE FINDING NAME");
        } catch (Exception e) {
            game.setGame_name("ISSUE FINDING NAME");
        }

        try {
            game_info_box = userAgent.doc.findFirst("<div class='col-xs-4 col-xs-max-320'>").findFirst("<div class='box no-top-border'>");
            try {
                Elements game_info = game_info_box.findEvery("<td>");
                Elements platforms = game_info_box.findFirst("<div class='platforms'>").getEach("<span>");
                Element total_trophies = game_info_box.findFirst("<div class='trophy-count'>").findFirst("<span class='small-info floatr'>").findFirst("<b>");
                game.setTotal_trophies(Integer.parseInt(total_trophies.getChildText()));
                List<String> list_platforms = new ArrayList<>();
                for (Element platform : platforms) {
                    list_platforms.add(platform.getChildText());
                }
                game.setPlatforms(list_platforms);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR: Could not pull platform for " + game.getUrl());
            }

//            Elements info_elements = game_info_box.findFirst("<table class='gameInfo zebra'>").findEvery("<tr>");
//            for (Element info : info_elements) {
//                Elements td_els = info.findEvery("<td>");
//                switch(td_els.getElement(0).getChildText()) {
//                    case "Developer":
//                        game.setGame_dev(td_els.getElement(1).getChildText());
//                        break;
//                    case "Publisher":
//                        game.setGame_publisher(td_els.getElement(1).getChildText());
//                        break;
//                    case "Genre":
//                        game.setGame_dev(td_els.getElement(1).getChildText());
//                        break;
//                }
//            }
//            try {
//                game.setGame_dev("Test Dev");
////            } catch (NotFound nfe2) {
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
////                Elements pubs = game_info_box.get;
//                game.setGame_publisher("Test Pub");
////            } catch (NotFound nfe2) {
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                List<String> test = new ArrayList<>();
//                test.add("Test Genre 1");
//                game.setGenre(test);
////            } catch (NotFound nfe2) {
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                List<String> test = new ArrayList<>();
//                test.add("Test Theme 1");
//                test.add("Test Theme 2");
//                game.setThemes(test);
////            } catch (NotFound nfe2) {
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                List<String> test = new ArrayList<>();
//                test.add("Test Mode 1");
//                test.add("Test Mode 2");
//                test.add("Test Mode 3");
//                game.setModes(test);
////            } catch (NotFound nfe2) {
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        } catch (NotFound nfe) {
            System.out.println("ERROR: Could not pull game info box for " + game.getUrl());
        }

//        try {
//            backlog_box = userAgent.doc.findFirst("<div class='col-xs-4 col-xs-max-320'>").findFirst("<table class='box zebra'>");
//            try {
//                Elements backlog_rows = backlog_box.findEvery("<tr>");
//                for (Element row : backlog_rows) {
//                    Elements info = row.findEvery("<td>");
//                    Element first_trophy =
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (NotFound nfe) {
//            System.out.println("ERROR: Could not find backlog box");
//        }

        try {
            Element main_part =userAgent.doc.findFirst("<div class='col-xs'>").findFirst("<div class='box no-top-border'>");
            trophies = main_part.findEvery("<tr class='completed'>");
            Element start_date = trophies.getElement(0).findEvery("<td>").getElement(2).findEvery("<nobr>");
//            System.out.println("Start: " + start_date.getElement(0).getChildText().trim().replaceAll("st","").replaceAll("nd","").replaceAll("rd", "").replaceAll("th","")
//                        + " " + start_date.getElement(1).getChildText());
            game.setStart_timestamp(start_date.getElement(0).getChildText().trim().replaceAll("st","").replaceAll("nd","").replaceAll("rd", "").replaceAll("th","")
                        + " " + start_date.getElement(1).getChildText());
            // End date depends on JULY month. So let's go backwards with it.
            for (int i = trophies.size()-1; i >= 0; i--) {
                Element end_date = trophies.getElement(i).findEvery("<td>").getElement(2).findEvery("<nobr>");
                String tmp = end_date.getElement(0).getChildText().trim().replaceAll("st", "").replaceAll("nd", "").replaceAll("rd", "").replaceAll("th", "");
                List<String> tmp_splt = Arrays.asList(tmp.split(" "));
                if (tmp_splt.get(1).equals("Jul") && tmp_splt.get(2).equals("2018")) {
//                    System.out.println("End: " + tmp + " " + end_date.getElement(1).getChildText());
                    game.setEnd_thl_timestamp(tmp + " " + end_date.getElement(1).getChildText());
                    i=0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with trophies for " + game.getUrl());
        }
//        try {
//            backlog_table = userAgent.doc.getTable("<table class='box zebra'>");
//        } catch (NotFound nfe) {
//            System.out.println("ERROR: Could not pull table for backlog time for " + game.getUrl());
//        }
//    private List<String> platforms;
//    private String game_name;
//    private int game_owners;
//    private boolean plat_game;
//    private String game_dev;
//    private String game_publisher;
//    private List<String> genre;
//    private List<String> modes;
        return game;
    }

    public static void main(String args[]) {
        ScrapePSNPGamePage test = new ScrapePSNPGamePage();
        Game testGame = new Game(123,"https://psnprofiles.com/trophies/5050-doodle-devil/slammajamma28");

//        test.checkGameCompletion("https://psnprofiles.com/trophies/6053-full-throttle-remastered/slammajamma28"); // Platinum, no DLC
//        test.checkGameCompletion("https://psnprofiles.com/trophies/3154-never-alone/slammajamma28"); // Base 100% complete, DLC incomplete
//        test.checkGameCompletion("https://psnprofiles.com/trophies/3725-kings-quest/slammajamma28"); // base incomplete, DLC incomplete
        test.checkGameCompletion(testGame, "slammajamma28"); // Base 100% complete, no DLC

    }
}
