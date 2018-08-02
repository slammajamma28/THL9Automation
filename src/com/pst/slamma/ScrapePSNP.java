package com.pst.slamma;

import com.jaunt.Element;
import com.jaunt.UserAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will be responsible for scraping all the necessary information from a
 * PSNP page and returning it to the PSTMember object
 */
public class ScrapePSNP {

    public static List<Integer> pullInfo(String psn) {
        Element bar;
        Element psnLevel = null;
        Element totalT = null;
        Element platinumT = null;
        Element goldT = null;
        Element silverT = null;
        Element bronzeT = null;
        List<Integer> result = new ArrayList<>();
        String url = "https://psnprofiles.com/" + psn;
        // Begin Jaunt Stuff
        try {
            UserAgent userAgent = new UserAgent();
            userAgent.visit(url);
            bar = userAgent.doc.findFirst("<ul class='profile-bar'>");
            psnLevel = bar.findFirst("<li class='icon-sprite level'>");
            totalT = bar.findFirst("<li class='total'>");
            platinumT = bar.findFirst("<li class='platinum'>");
            goldT = bar.findFirst("<li class='gold'>");
            silverT = bar.findFirst("<li class='silver'>");
            bronzeT = bar.findFirst("<li class='bronze'>");
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }

        result.add(Integer.parseInt(psnLevel.getChildText().trim()));
        result.add(Integer.parseInt(totalT.getChildText().trim().replaceAll(",","")));
        result.add(Integer.parseInt(platinumT.getChildText().trim().replaceAll(",","")));
        result.add(Integer.parseInt(goldT.getChildText().trim().replaceAll(",","")));
        result.add(Integer.parseInt(silverT.getChildText().trim().replaceAll(",","")));
        result.add(Integer.parseInt(bronzeT.getChildText().trim().replaceAll(",","")));

        return result;
    }

//    public List<Integer> pullNewgames(String psn, int lastLog) {
//
//    }

    // Testing purposes only
    public static void main(String args[]) {
        // This is to test the actual information pulled from PSNP
        List<Integer> testResult = pullInfo("slammajamma28");
        for (int i : testResult) {
            System.out.println(i);
        }
    }
}
