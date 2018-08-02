package com.pst.slamma;

import com.jaunt.Element;
import com.jaunt.UserAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will be responsible for scraping all the necessary information from a
 * PSNTL page and returning it to the PSTMember object
 */
public class ScrapePSNTL {

    public static List<Integer> pullInfo(String psn) {
        Element bar;
        Element sidebar;
        Element psnLevel = null;
        Element totalT = null;
        Element platinumT = null;
        Element goldT = null;
        Element silverT = null;
        Element bronzeT = null;
        List<Integer> result = new ArrayList<>();
        String url = "http://psntrophyleaders.com/user/view/" + psn;
        // Begin Jaunt Stuff
        try {
            UserAgent userAgent = new UserAgent();
            userAgent.visit(url);
            sidebar= userAgent.doc.findFirst("<div id='boardrank'>");
            platinumT = sidebar.findFirst("<td class='platinum'>");
            goldT = sidebar.findFirst("<td class='gold'>");
            silverT = sidebar.findFirst("<td class='silver'>");
            bronzeT = sidebar.findFirst("<td class='bronze'>");
            totalT = sidebar.findFirst("<div class='rankhead'>");
            bar = userAgent.doc.findFirst("<td class='userstats'>");
            psnLevel = bar.findFirst("<div id='leveltext'>");
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }

        result.add(Integer.parseInt(psnLevel.innerText().trim()));
        result.add(Integer.parseInt(totalT.innerText().trim().replaceAll(",","").replaceAll("Trophies \\(","").replaceAll("\\)","")));
        result.add(Integer.parseInt(platinumT.innerText().trim().replaceAll(",","")));
        result.add(Integer.parseInt(goldT.innerText().trim().replaceAll(",","")));
        result.add(Integer.parseInt(silverT.innerText().trim().replaceAll(",","")));
        result.add(Integer.parseInt(bronzeT.innerText().trim().replaceAll(",","")));

        return result;
    }

    // Testing purposes only
    public static void main(String args[]) {
        // This is to test the actual information pulled from PSNTL
        List<Integer> testResult = pullInfo("slammajamma28");
        for (int i : testResult) {
            System.out.println(i);
        }
    }
}
