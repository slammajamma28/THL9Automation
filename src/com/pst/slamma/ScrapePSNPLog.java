package com.pst.slamma;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrapePSNPLog {

    private DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy h:mm:ss a");

    public List<Trophy> trophiesFromLog(String psn, int firstLog, int lastLog) {
        List<Trophy> all_trophies = new ArrayList<>();
        int page = 1;
        return trophiesFromLog(psn, firstLog, lastLog, all_trophies, page);
    }

    public List<Trophy> trophiesFromLog(String psn, int firstLog, int lastLog, List<Trophy> all_trophies, int page) {
        Element table;
        Elements all_rows;

        Element e_current_log_num;
        int current_log_num;
        Element current_tile_type;
        String url;
        if (page == 1) {
            url = "https://psnprofiles.com/" + psn + "/log";
        }
        else {
            url = "https://psnprofiles.com/" + psn + "/log?page=" + page;
        }
        // Begin jaunt stuff
        try {
            UserAgent userAgent = new UserAgent();
            userAgent.visit(url);

            table = userAgent.doc.findFirst("<div class='box'>");
            all_rows = table.findEvery("<tr>");

            for (Element row : all_rows) {
                e_current_log_num = row.findFirst("<td style='text-align: right;'>");
                current_log_num = Integer.parseInt(e_current_log_num.innerText().trim().replaceAll("#","").replaceAll(",",""));
//                System.out.println("Current Log: " + current_log_num);
                if (current_log_num > lastLog) {
                    System.out.println("We have not reached the beginning of THL trophies..." + current_log_num + " > " + firstLog);
                }
                else if (current_log_num == firstLog) {
//                    System.out.println("I have reached the end of current processing, quit.");
                    return all_trophies;
                } else {
//                    System.out.println("I am still on new rows, continue");
                    all_trophies.add(getTrophyInfo(row, psn));
                }
            }
            trophiesFromLog(psn, firstLog, lastLog, all_trophies, ++page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return all_trophies;
    }

    private Trophy getTrophyInfo(Element row, String psn) {
        Trophy trophy = new Trophy();
        Element e_current_url;
        String current_url;
        int current_game_id;
        try {
            Elements info_things = row.findEach("<td>");

            // Game ID
            Element game_id = info_things.getElement(0);
            e_current_url = game_id.findFirst("<a>");
            current_url = e_current_url.getAt("href");
//            System.out.println("Current URL = " + current_url);
            String tmp = current_url.substring(33);
            List<String> tmp_splt = Arrays.asList(tmp.split("-"));
            current_game_id = Integer.parseInt(tmp_splt.get(0));
//            System.out.println("Game ID = " + current_game_id);
            trophy.setGame_id(current_game_id);
            trophy.setGame_url(current_url.replaceAll(psn,""));
//            games.add(new Game(current_game_id, current_url.replaceAll(psn, "")));

            // Trophy Title and Description
            Element title_desc = info_things.getElement(2);
            String title = title_desc.findFirst("<a>").getChildText();
            String desc = title_desc.getChildText().trim();
//            System.out.println("Title = " + title);
//            System.out.println("Desc = " + desc);
            trophy.setTrophy_title(title);
            trophy.setTrophy_description(desc);

            // Log number
            Element log = info_things.getElement(4);
            String lognum = log.innerText().replaceAll("#","").replaceAll(",","").trim();
            trophy.setLog_num(Integer.parseInt(lognum));

            // Time stamp
            Element datestamp = info_things.getElement(5);
            String trophy_date = datestamp.findFirst("<span class='typo-top-date'>").findFirst("<nobr>").getChildText().trim();
            String trophy_time = datestamp.findFirst("<span class='typo-bottom-date'>").findFirst("<nobr>").getChildText().trim();
//            System.out.println("Trophy date = " + trophy_date);
//            System.out.println("Trophy time = " + trophy_time);
            String date_stamp = trophy_date.replaceAll("st","").replaceAll("nd","").replaceAll("rd", "").replaceAll("th","").concat(" " + trophy_time);
            LocalDateTime trophy_stamp = LocalDateTime.parse(date_stamp, DATE_FORMAT);
            trophy.setTimestamp(trophy_stamp);

            // Achievers
            Element achievers = info_things.getElement(6);
            String number_achieved = achievers.findFirst("<span class='typo-top'>").getChildText().replaceAll(",", "").trim();
//            System.out.println("Number of achievers = " + number_achieved);
            trophy.setAchievers(Integer.parseInt(number_achieved));

            // Owners
            Element owners = info_things.getElement(7);
            String number_owned = owners.findFirst("<span class='typo-top'>").getChildText().replaceAll(",", "").trim();
//            System.out.println("Number of owners = " + number_owned);
            trophy.setOwners(Integer.parseInt(number_owned));

            // Rarity
            Element rarity = info_things.getElement(8);
            String rarity_percent = rarity.findFirst("<span class='typo-top'>").getChildText();
            String rarity_type = rarity.findFirst("<span class='typo-bottom'>").findFirst("<nobr>").getChildText();
//            System.out.println("Rarity = " + rarity_percent + " (" + rarity_type + ")");
            trophy.setRarity_type(rarity_type);
            trophy.setRarity_percentage(Double.parseDouble(rarity_percent.replaceAll("%", "")));

            // Type
            Element trophy_value = info_things.getElement(9);
            String value = trophy_value.findFirst("<img>").getAt("title");
//            System.out.println("Trophy value = " + value);
            trophy.setTrophy_value(value);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return trophy;
    }
//    public List<Integer> checkForCompletion(List<Integer> games, String psn) {
//        List<Integer> return_list = new ArrayList<>();
//        for (int game : games) {
//            ScrapePSNPGamePage sgp = new ScrapePSNPGamePage();
//            if(sgp.isGameComplete(game, psn)) {
//                return_list.add(game);
//            }
//        }
//        return return_list;
//    }
//
//    public int checkForNintiesTrophies(List<Integer> games, String psn) {
//        ScrapePSNPGamePage spgp = new ScrapePSNPGamePage();
//        int total_ninties_trophies = 0;
//        for (int game : games) {
//            total_ninties_trophies =+ spgp.countNintiesTrophies(game, psn);
//        }
//        return total_ninties_trophies;
//    }
    private void writeToFile(Trophy trophy, String user, String team) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
        String filename = ".\\overall\\" + team + "\\" + user + ".csv";
        File newFile = new File(filename);
        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            FileWriter fw = new FileWriter(filename,true);
            fw.write(trophy.getGame_id() + "|" + trophy.getTrophy_title() + "|" +
                        trophy.getTrophy_description() + "|" + trophy.getLog_num() + "|" +
                        trophy.getTimestamp().format(DATE_FORMAT) + "|" + trophy.getAchievers() + "|" +
                        trophy.getOwners() + "|" + trophy.getRarity_percentage() + "|" +
                        trophy.getRarity_type() + "|" + trophy.getTrophy_value());
            fw.write("\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String args[]){
        ScrapePSNPLog test = new ScrapePSNPLog();
//        List<Game> games = new ArrayList<>();
        String psn = "slammajamma28";
        String team = "Dabaholics Anonymous";
        // Slamma,slammajamma28,Dabaholics Anonymous,7577,104
        List<Trophy> trophies = new ArrayList<>();
        List<Integer> game_ids = new ArrayList<>();
        List<Game> games = new ArrayList<>();
        String line;
//        try {
//            FileReader fr = new FileReader("participant_list");
//            BufferedReader br = new BufferedReader(fr);
//            while ((line = br.readLine()) != null) {
//                if (line.substring(0, 1).equals("+")) {
//                    // Do nothing
//                } else {
//                    List<String> id = Arrays.asList(line.split(","));
//                    String psn = id.get(1);
//                    String team = id.get(2).replaceAll(":","").replaceAll("?","");
//                    int last_log = Integer.parseInt(id.get(3));
//                    int first_log = Integer.parseInt(id.get(4));
                    System.out.println("Pulling log info for " + psn);
//                    trophies = test.trophiesFromLog(psn, first_log, last_log);
                    trophies = test.trophiesFromLog(psn, 7577, 8079);
                    System.out.println("Printing to file for " + psn);
                    for (Trophy trophy : trophies) {
//                        test.writeToFile(trophy, psn, team);
                        test.printTrophy(trophy);
                        if (! game_ids.contains(trophy.getGame_id())) {
                            game_ids.add(trophy.getGame_id());
                            games.add(new Game(trophy.getGame_id(), trophy.getGame_url()));
                        }
                    }
                    System.out.println("\nNumber of unique games played by " + psn + ": " + games.size() + "\n");
                    for (Game game : games) {
                        System.out.println(game.getUrl());
                    }

                    ScrapePSNPProfile scrapePSNPProfile = new ScrapePSNPProfile();
                    scrapePSNPProfile.checkForTHLGames(games, psn);

                    System.out.println("\n****************************\n");
                    trophies = null;
//                }
//            }
//        } catch (Exception e) {
//                e.printStackTrace();
//        }
    }

    public void printTrophy(Trophy trophy) {
        System.out.println("Game ID #" + trophy.getGame_id());
        System.out.println("Trophy title: " + trophy.getTrophy_title());
        System.out.println("Trophy description: " + trophy.getTrophy_description());
        System.out.println("Log number: " + trophy.getLog_num());
        System.out.println("Trophy timestamp: " + trophy.getTimestamp());
        System.out.println("Trophy achievers: " + trophy.getAchievers());
        System.out.println("Game owners: " + trophy.getOwners());
        System.out.println("Trophy rarity: " + trophy.getRarity_percentage() + " (" + trophy.getRarity_type() + ")");
        System.out.println("Trophy value: " + trophy.getTrophy_value());
        System.out.println("\n *************************** \n ");
    }
}