package com.pst.slamma;

import com.jaunt.Element;
import com.jaunt.NotFound;
import com.jaunt.UserAgent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class UniqueGames {

    public boolean isGameComplete(String game_url) {
        Element main_area;
        Element game_bar;
        Element base_game_bar;
//        String url = "https://psnprofiles.com/trophies/" + game_id + "/" + psn;

        //Begin jaunt stuff
        try {
            UserAgent userAgent = new UserAgent();
            userAgent.visit(game_url);

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
//            System.out.println("There was an issue with " + psn + ", " + game_id + " - " + url);
            e.printStackTrace();
        }

        return false;
    }

    public void checkGameCompletions() {
        String line;
        String psn;
        int game_id;

        try {
            FileReader fr = new FileReader("unique_games");
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                // Parse out all da stuff
                List<String> id = Arrays.asList(line.split("/"));
                // 4 is game ID, needs splitting
                // 5 is PSN
                game_id = Integer.parseInt(Arrays.asList(id.get(4).split("-")).get(0));
                psn = id.get(5);
                if (isGameComplete(line)) {
                    writeToLBFile(psn + "," + game_id);
                } else {
                    System.out.println("Incomplete game: " + line);
                }
                // Write to file
            }
        } catch (Exception e) {
            System.out.println("Unique Game File Issue");
        }
    }

    private void writeToLBFile(String line) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
        String filename = ".\\shared\\" + dtf.format(LocalDateTime.now()) + "-unique-games.csv";
        File newFile = new File(filename);
        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            FileWriter fw = new FileWriter(filename,true);
            fw.write(line);
            fw.write("\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        UniqueGames uniqueGames= new UniqueGames();
        uniqueGames.checkGameCompletions();
    }
}
