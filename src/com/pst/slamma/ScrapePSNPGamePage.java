package com.pst.slamma;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ScrapePSNPGamePage {

    private List<Integer> NINETIES_GAME_ID = Arrays.asList(992, 6245, 6246, 6247);
    private DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy h:mm:ss a");
    private LocalDateTime NOW = LocalDateTime.now();
//    private Month JULY = Month.JULY;
    private Month JULY = Month.APRIL;

    public boolean isGameComplete(int game_id, String psn) {
        Element main_area;
        Element game_bar;
        Element base_game_bar;
        String url = "https://psnprofiles.com/trophies/" + game_id + "/" + psn;

        //Begin jaunt stuff
        try {
            UserAgent userAgent = new UserAgent();
            userAgent.visit(url);

            // Check for platinum in main bar
            main_area = userAgent.doc.findFirst("<div class='col-xs'>");
            game_bar = main_area.findFirst("<tr>");
            String completion_type = game_bar.getAt("class").trim();

            if (completion_type.equals("platinum") || completion_type.equals("completed")) {
                return true;
            } else {
                // If no platinum or completed, check to see if basegame bar exists

                // check for base game bar for 100%
                base_game_bar = main_area.findFirst("<div class='box no-top-border'>").findFirst("<tr>");

                if (base_game_bar.findFirst("<span class='title'>").innerText().equals("Base Game")) {
                    if (base_game_bar.getAt("class").trim().equals("completed")) {
                        return true;
                    }
                }
            }
        } catch (NotFound nf) {
            // Typically caught if this is a game without DLC
            // Indicates that the 100% hasn't been achieved on the base game
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public int countNintiesTrophies(int game_id, String psn) {
        int earned_trophies = 0;

        Element main_area;
        Elements trophies_complete;
        String url = "https://psnprofiles.com/trophies/" + game_id + "/" + psn;

        //Begin jaunt stuff
        try {
            UserAgent userAgent = new UserAgent();
            userAgent.visit(url);

            // Check for platinum in main bar
            main_area = userAgent.doc.findFirst("<div class='col-xs'>");
            trophies_complete = main_area.findEvery("<tr class='completed'>");

            for (Element trophy : trophies_complete) {
                Element datestamp = trophy.findFirst("<span class='typo-top-date'>");
                Element timestamp = trophy.findFirst("<span class='typo-bottom-date'>");
                String date_stamp = datestamp.innerText().trim()
                        .replaceAll("st","").replaceAll("nd","").replaceAll("rd", "").replaceAll("th","");

                String complete_stamp = date_stamp + " " + timestamp.innerText().trim();
                LocalDateTime trophy_stamp = LocalDateTime.parse(complete_stamp, DATE_FORMAT);

                if (trophy_stamp.getMonth().equals(JULY)
                        && trophy_stamp.getYear() == NOW.getYear()
//                        && trophy_stamp.getDayOfMonth() == NOW.getDayOfMonth()
                        ){
                    earned_trophies++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return earned_trophies;
    }

    public static void main(String args[]) {
        ScrapePSNPGamePage test = new ScrapePSNPGamePage();
//        System.out.println("Is this game complete? Expect true, actual " + test.isGameComplete(3154, "slammajamma28")); // true - Never Alone, not 100% but base complete
//        System.out.println("Is this game complete? Expect true, actual " + test.isGameComplete(1932, "slammajamma28")); // true - Thomas was alone, 100% completed
//        System.out.println("Is this game complete? Expect true, actual " + test.isGameComplete(5442, "slammajamma28")); // true - Battlefield 1, platinum not 100%
//        System.out.println("Is this game complete? Expect false, actual " + test.isGameComplete(4784, "slammajamma28")); // false - Downwell, not platinumed
//        System.out.println("Is this game complete? Expect false, actual " + test.isGameComplete(2814, "slammajamma28")); // false - CounterSpy, not 100%, no DLC
//        System.out.println("Is this game complete? Expect false, actual " + test.isGameComplete(3269, "slammajamma28")); // false - Switch Galaxy Ultra, not 100% DLC included
//        System.out.println("Is this game complete? Expect false, actual " + test.isGameComplete(2434, "slammajamma28")); // false - Rayman Legends, not complete
//        System.out.println("Is this game complete? Expect true, actual " + test.isGameComplete(2717, "slammajamma28")); // true - Mousecraft, 100%
//        System.out.println("Is this game complete? Expect true, actual " + test.isGameComplete(4719, "slammajamma28")); // true - Uncharted 4, platinum but not 100%
//        System.out.println("Is this game complete? Expect true, actual " + test.isGameComplete(761, "slammajamma28"));  // true - Stacking, base 100% but not DLC
//        System.out.println("Is this game complete? Expect true, actual " + test.isGameComplete(992, "slammajamma28"));  // true - Comix Zone, 100% - also counts as 90s game
//        System.out.println("Is this game complete? Expect true, actual " + test.isGameComplete(124, "slammajamma28"));  // true - Wolf 3D, 100% - also counts as 90s game
//        System.out.println("Is this game complete? Expect false, actual " + test.isGameComplete(808, "slammajamma28"));  // false - Sonic 1, not 100% - but counts as 90s game
//        System.out.println("Is this game complete? Expect true, actual " + test.isGameComplete(3304, "slammajamma28")); // true - Grim Fangago, plat - also counts as 90s game
//        System.out.println("Is this game complete? Expect false, actual " + test.isGameComplete(6245, "slammajamma28")); // false - Crash 1, no plat - but counts as 90s game

        System.out.println("Number of 90s trophies: " + test.countNintiesTrophies(6245, "slammajamma28"));
    }
}
