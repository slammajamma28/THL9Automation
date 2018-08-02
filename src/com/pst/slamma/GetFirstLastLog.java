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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetFirstLastLog {

    private DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy");

    public List<String> trophiesFromLog(String psn) {
        List<String> log_numbers = new ArrayList<>();
        int page = 1;
        boolean last_found = false;
        return trophiesFromLog(psn, last_found, log_numbers, page);
    }

    public List<String> trophiesFromLog(String psn, boolean last_found, List<String> log_numbers, int page) {
        Element table;
        Elements all_rows;

        Element e_current_log_num;
        String current_log_num;
        String url;
//        System.out.println("Current page #" + page);
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
                current_log_num = e_current_log_num.innerText().trim()
                        .replaceAll("#", "").replaceAll(",", "");
//                System.out.println("Current Log: " + current_log_num);

                String trophy_date = row.findFirst("<span class='typo-top-date'>").findFirst("<nobr>").getChildText().trim();
                String date_stamp = trophy_date
                        .replaceAll("st","")
                        .replaceAll("nd","")
                        .replaceAll("rd", "")
                        .replaceAll("th","");
                LocalDate trophy_stamp = LocalDate.parse(date_stamp, DATE_FORMAT);
//                LocalDateTime trophy_stamp = LocalDateTime.parse(date_stamp, DATE_FORMAT);
//                System.out.println("Current date: " + trophy_date.toString());

                // Looking for the last THL trophy first
                // Want the first July 2018 trophy listed on the log page
                if (!last_found) {
//                    System.out.println(trophy_stamp.getMonth().toString());
                    if (trophy_stamp.getMonth().equals(Month.JULY)) {
//                        System.out.println("Found the last THL trophy!!!!");
                        last_found = true;
                        log_numbers.add(current_log_num);
                    }
                }

                // Then we look for the last THL trophy... that was not earned in July
                if (last_found) {
//                    System.out.println(trophy_stamp.getMonth().toString());
                    if (!trophy_stamp.getMonth().equals(Month.JULY)) {
//                        System.out.println("Found the trophy before THL!");
                        log_numbers.add(current_log_num);
                        return log_numbers;
                    }
                }
            }
            trophiesFromLog(psn, last_found, log_numbers, ++page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return log_numbers;
    }

    private void writeToFile(String writethis) {
        String filename = "final_list_for_stats";
        File newFile = new File(filename);
        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            FileWriter fw = new FileWriter(filename,true);
            fw.write(writethis);
            fw.write("\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String args[]) {
        GetFirstLastLog test = new GetFirstLastLog();
//        String line = "Velvet,Blood_Velvet,Team,0,0";
        String line;
        try {
            FileReader fr = new FileReader("participant_list");
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if (line.substring(0, 1).equals("+")) {
                    test.writeToFile(line);
                } else {
                    List<String> id = Arrays.asList(line.split(","));
                    String psn = id.get(1);
                    System.out.println("Retrieving for " + psn);
                    List<String> result = test.trophiesFromLog(psn);
                    System.out.println("Starting log: " + result.get(1));
                    System.out.println("Ending log: " + result.get(0));
                    test.writeToFile(id.get(0) + "," + id.get(1) + "," + id.get(2) + "," + result.get(1) + "," + result.get(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
