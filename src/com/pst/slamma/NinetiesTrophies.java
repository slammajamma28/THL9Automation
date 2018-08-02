package com.pst.slamma;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.UserAgent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class NinetiesTrophies {

    private final List<Integer> NINETIES_GAMES = Arrays.asList(
            4318,
            1825,
            1827,
            2677,
            686,
            6441,
            7362,
            5731,
            5957,
            6407,
            7606,
            7220,
            6604,
            6717,
            5679,
            6283,
            6576,
            6357,
            6019,
            7554,
            7505,
            6929,
            6324,
            6107,
            6256,
            7289,
            6491,
            5607,
            6219,
            6753,
            7820,
            6868,
            5892,
            5827,
            7720,
            6061,
            7107,
            6328,
            6526,
            6791,
            7322,
            6357,
            7730,
            6821,
            5660,
            6668,
            7194,
            7515,
            5868,
            6203,
            3678,
            7167,
            7314,
            6951,
            6591,
            7572,
            6907,
            6289,
            6412,
            7601,
            7770,
            5502,
            6088,
            6459,
            6896,
            7085,
            7466,
            6153,
            7247,
            7006,
            7679,
            7403,
            5744,
            6179,
            6978,
            6463,
            6564,
            5079,
            4187,
            5682,
            2119,
            2112,
            7875,
            7887,
            7907,
            992,
            6131,
            6245,
            6246,
            6247,
            458,
            1842,
            4392,
            6096,
            1872,
            4456,
            1127,
            1748,
            956,
            5362,
            3045,
            380,
            5965,
            1768,
            4126,
            2158,
            5702,
            7034,
            6053,
            5562,
            3304,
            300,
            1572,
            544,
            796,
            534,
            2617,
            1927,
            1810,
            6894,
            1631,
            3802,
            376,
            1466,
            1018,
            6476,
            1642,
            2742,
            5977,
            2627,
            2284,
            2559,
            287,
            3200,
            4485,
            7223,
            1090,
            384,
            1227,
            1772,
            808,
            851,
            4017,
            1009,
            886,
            4049,
            2060,
            185,
            339,
            1183,
            7385,
            7253,
            7254,
            4788,
            2061,
            399,
            6050,
            7372,
            1273,
            1743,
            1747,
            830,
            1591,
            1770,
            1841,
            35,
            5667,
            6551,
            6531,
            124,
            1465,
            668
    );
    private final List<Integer> COLLECTION_GAMES = Arrays.asList(
            6046,
            60,
            472,
            7682,
            1765,
            3802,
            6451,
            158,
            933,
            1414,
            1416,
            2335,
            7667
    );
    private DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy");
    private final Month THIS_MONTH = Month.JULY;
    private final int THIS_YEAR = 2018;

    public void checkNinetiesTrophies() {

        String line;
        String psn;
        int game_id;
        int total_trophs = 0;

        try {
            FileReader fr = new FileReader("nineties_submissions");
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                // Parse out all da stuff
                List<String> id = Arrays.asList(line.split("/"));
                // 4 is game ID, needs splitting
                // 5 is PSN
                game_id = Integer.parseInt(Arrays.asList(id.get(4).split("-")).get(0));
                psn = id.get(5);
                if (NINETIES_GAMES.contains(game_id)) {
                    total_trophs = checkGame(line);
                    if ( total_trophs != 0 ) {
                        writeToLBFile(psn + "," + game_id + "," + total_trophs);
                    }
                } else if (COLLECTION_GAMES.contains(game_id)) {
                    System.out.println("CHECK MANUALLY... THIS IS A COLLECTION! " + line);
                }
                else {
                    System.out.println("NOT A NINETIES GAME: " + line);
                }

                total_trophs = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToLBFile(String line) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
        String filename = ".\\shared\\" + dtf.format(LocalDateTime.now()) + "-nineties.csv";
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

    public int checkGame(String URL) {
        int results = 0;
        String date_url = URL + "?order=date";
        Element page, table;
        Elements trophies;
        try {
            UserAgent userAgent = new UserAgent();
            userAgent.visit(date_url);
            table = userAgent.doc.findFirst("<div class='box no-top-border'>");
            trophies = table.findEvery("<tr class='completed'>");
            for (Element trophy : trophies) {
                Element datestamp = trophy.findFirst("<span class='typo-top-date'>");
                String date_stamp = datestamp.innerText().trim()
                        .replaceAll("st","").replaceAll("nd","").replaceAll("rd", "").replaceAll("th","");
                LocalDate trophy_stamp = LocalDate.parse(date_stamp, DATE_FORMAT);
                if (trophy_stamp.getYear() == THIS_YEAR
                        && trophy_stamp.getMonth().equals(THIS_MONTH)
                        ){
                    results++;
                }
            }
        } catch (Exception e) {
            System.out.println("Issue with " + URL);
            e.printStackTrace();
        }

        return results;
    }

    public static void main(String args[]) {
        NinetiesTrophies ninetiesTrophies = new NinetiesTrophies();
        ninetiesTrophies.checkNinetiesTrophies();
    }
}
