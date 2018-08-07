package com.pst.slamma;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.UserAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *   Loop through each participant
 *   Using game ID numbers retrieved from ScrapePSNPLog,
 *   Scrape PSNP profile page row by row for following logic:
 *   1) Is this a THL game?
 *      YES - pull Full game name, platform, indicate if the game was platinum, 100% or incomplete
 *      NO - does this game have a last played month/year of Jan - Jun 2018?
 *          NO  - does it have a last played year of 2010 - 2017
 *  *                  YES - we're done yo. Don't keep checking
 *  *                  NO  - This is an August game I would assume. Ignore.
 *  *              YES - we're done yo. Don't keep checking
 */
public class ScrapePSNPProfile {

//    public Game pullGameInfo(int game_id) {
//        Game game = new Game();
//
//
//
//        return game;
//    }

    public List<Game> checkForTHLGames(List<Game> games, String psn) {
        List<Integer> unique_game_ids = new ArrayList<>();
        for (Game game : games) {
            unique_game_ids.add(game.getGame_id());
        }
        List<Game> thl_games = new ArrayList<>();
        Elements all_rows;
        Element e_current_url;
        String current_url;
        int current_game_id;
        String url = "https://psnprofiles.com/" + psn;
        try {
            UserAgent userAgent = new UserAgent();
            userAgent.visit(url);

            all_rows = userAgent.doc.findFirst("<div class='box no-top-border'>").findEvery("<tr>");

            for (Element row : all_rows) {
                // get game ID to check for THL game
                Elements info_things = row.findEach("<td>");
                Element game_id = info_things.getElement(0);
                e_current_url = game_id.findFirst("<a>");
                current_url = e_current_url.getAt("href");
                System.out.println("Current url = " + current_url);
                String tmp = current_url.substring(33);
                List<String> tmp_splt = Arrays.asList(tmp.split("-"));
                current_game_id = Integer.parseInt(tmp_splt.get(0));
                // if THL game, access game page and pull info
                if (unique_game_ids.contains(current_game_id)) {
                    System.out.println("I am pulling info for " + current_game_id);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return thl_games;
    }

    public static void main(String args[]) {

    }
}
