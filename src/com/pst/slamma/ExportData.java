package com.pst.slamma;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class will be used to export the information to a CSV file that can be copied into the master spreadsheet
 */
public class ExportData {

    public void exportTeams(List<THLTeam> teams) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
        String filename = ".\\shared\\" + dtf.format(LocalDateTime.now()) + "-team-export.csv";

        try {
            Writer writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(filename), "UTF-8"));
            for (THLTeam team : teams) {
                writer.write(team.getTeamName() + "," + team.getTeam_score() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportIndividuals (List<PSTMember> members) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
        String filename = ".\\shared\\" + dtf.format(LocalDateTime.now()) + "-solo-export.csv";

        try {
            Writer writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(filename), "UTF-8"));
            for (PSTMember member : members) {
                writer.write(member.getPst_name() + "," + member.getPsn_name() + "," + member.getTeam_name() + ","
                        + member.getCurrent_trophies() + "," + member.getStart_trophies() + ","
                        + member.getTotal_trophies() + "," + member.getCurrent_platinums() + ","
                        + member.getStart_platinums() + "," + member.getTotal_platinum() + ","
                        + member.getTotal_gold() + "," + member.getTotal_silver() + ","
                        + member.getTotal_bronze() + "," + member.getPsn_level() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String args[]) {
//        List<THLTeam> thlTeams = new ArrayList<>();
//        List<PSTMember> teamMembers = new ArrayList<>();
//        PSTMember slamz = new PSTMember("Slamma", "slammajamma28", "Test Team", 4332, 50);
//        PSTMember velvz = new PSTMember("Velvet", "Blood_Velvet", "Test Team", 1234, 50);
//        teamMembers.add(slamz);
//        teamMembers.add(velvz);
//        THLTeam testTeam = new THLTeam("Test Team", teamMembers);
//        thlTeams.add(testTeam);
//
//        List<PSTMember> teamMembers2 = new ArrayList<>();
//        PSTMember jay = new PSTMember("JayPBM", "JayPBM", "A Team 2", 4332, 50);
//        PSTMember zet  = new PSTMember("Zetberg", "Zetberg", "A Team 2", 2213, 50);
//        teamMembers2.add(jay);
//        teamMembers2.add(zet);
//        THLTeam testTeam2 = new THLTeam("A Team 2", teamMembers2);
//        thlTeams.add(testTeam2);
//
//        List<PSTMember> teamMembers3 = new ArrayList<>();
//        PSTMember mende = new PSTMember("Mendel", "Mendel_Skimmel", "Another Team 2", 1119, 50);
//        PSTMember bread = new PSTMember("BreadSkin", "BreadSkin", "Another Team 2", 5542, 50);
//        teamMembers3.add(mende);
//        teamMembers3.add(bread);
//        THLTeam testTeam3 = new THLTeam("Another Team 2", teamMembers3);
//        thlTeams.add(testTeam3);
//
//        List<PSTMember> everyone = new ArrayList<>();
//        everyone.add(slamz);
//        everyone.add(velvz);
//        everyone.add(jay);
//        everyone.add(zet);
//        everyone.add(mende);
//        everyone.add(bread);
//
//        System.out.println("Printing initial team order:");
//        for (THLTeam team : thlTeams) {
//            System.out.println("\t" + team.getTeamName());
//        }
//
//        ExportData ed = new ExportData();
//        ed.exportTeams(thlTeams);
//
//        System.out.println("\nPrinting initial member order");
//        for (PSTMember member : everyone) {
//            System.out.println("\t" + member.getPst_name());
//        }
//
//        ed.exportIndividuals(everyone);
//    }
}
