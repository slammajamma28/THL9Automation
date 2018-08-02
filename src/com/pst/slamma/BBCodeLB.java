package com.pst.slamma;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class prints out the stuff to post on PST
 */
public class BBCodeLB {

    /**
     * This c will take the list of THL teams, sort and print the leaderboard in BBCode
     * @param teams - the list of THLTeams
     */
    public void printBBCodeTeamLB(List<THLTeam> teams) {

        // Sort teams
        Comparator<THLTeam> thlTeamComparator = new Comparator<THLTeam>() {
            @Override
            public int compare(THLTeam o1, THLTeam o2) {
                if (o1.getTeam_score() == o2.getTeam_score()) return 0;
                else if (o1.getTeam_score() > o2.getTeam_score()) return -1;
                else return 1;
            }
        };

        Collections.sort(teams, thlTeamComparator);

        writeToLBFile("*********************");
        writeToLBFile("[center][url=https://images.gr-assets.com/hostedimages/1513814742ra/24726205.gif][img]https://i.imgur.com/IarPbGn.png[/img][/url][/center]\n");
        writeToLBFile("[list=1]");
        for (THLTeam team : teams) {
            writeToLBFile("[*][B]" + team.getTeamName() + "[/B] - " + team.getTeam_score());
        }
        writeToLBFile("[/list]");
    }

    /**
     * This method will take the list of participants and print the trophy leaderboard in BBCode
     * @param members - the list of PSTMembers
     */
    public void printBBCodeIndividualLB(List<PSTMember> members) {

        // Sort members
        Comparator<PSTMember> pstMemberComparator = new Comparator<PSTMember>() {
            @Override
            public int compare(PSTMember o1, PSTMember o2) {
                if (o1.getCurrent_trophies() == o2.getCurrent_trophies()) return 0;
                else if (o1.getCurrent_trophies() > o2.getCurrent_trophies()) return -1;
                else return 1;
            }
        };

        Collections.sort(members, pstMemberComparator);

        writeToLBFile("*********************");
        writeToLBFile("\n[center][img]https://i.imgur.com/bsCX05d.png[/img]");
        writeToLBFile("* - [url=https://media1.tenor.com/images/dc792a96b19306578954913440d73496/tenor.gif?itemid=3441119]Exploding wallet[/url] [Met Sub Goal][/center]\n");
        int header_tmp = 1;
        writeToLBFile("[indent][size=4][url=https://i.imgur.com/LpyuQVV.gif][color=blue][b]Master of your Domain[/b][/size][/color][/url][/indent]");
        writeToLBFile("[list=1]");
        for (PSTMember member : members) {
            if (header_tmp == 22) {
                writeToLBFile("\n[size=4][url=https://i.imgur.com/K4Bcu1N.mp4][color=blue][b]And You Want to Be my Latex Salesman[/color][/url][/b][/size]\n");
            }
            if (header_tmp == 43) {
                writeToLBFile("\n[size=4][url=https://media1.tenor.com/images/d498fa7cb4d2cc068f42aad205d990ac/tenor.gif?itemid=5796128][color=blue][b]We're living[/color][/url] [url=https://thumbs.gfycat.com/SimilarCraftyCusimanse-size_restricted.gif][color=blue]in a society![/b][/color][/url][/size]\n");
            }
            if (header_tmp == 64) {
                writeToLBFile("\n[size=4][url=https://media.giphy.com/media/kgQz2qE3i0T1C/giphy.gif][color=blue][b]Can't Find My Baby[/b][/color][/url][/size]\n");
            }
            if (header_tmp == 85) {
                writeToLBFile("\n[size=4][url=http://i.imgur.com/AH39pOA.gif][color=blue][b]Very Bad[/b][/color][/url][/size]\n");
            }
            if (header_tmp == 106) {
                writeToLBFile("\n[size=4][url=https://giant.gfycat.com/SentimentalShadyBluejay.webm][color=blue][b]I Was In the Pool![/b][/color][/url][/size]\n");
            }
            if (member.getCurrent_trophies() > 496) {
                if (member.getPst_name().equals(member.getPsn_name())) {
                    writeToLBFile("[*][B]" + member.getPst_name() + "[/B] - " + member.getCurrent_trophies() + "*");
                } else {
                    writeToLBFile("[*][B]" + member.getPst_name() + "[/B] (" + member.getPsn_name() + ") - " + member.getCurrent_trophies() + "*");
                }
            }
            else {
                if (member.getPst_name().equals(member.getPsn_name())) {
                    writeToLBFile("[*][B]" + member.getPst_name() + "[/B] - " + member.getCurrent_trophies());
                } else {
                    writeToLBFile("[*][B]" + member.getPst_name() + "[/B] (" + member.getPsn_name() + ") - " + member.getCurrent_trophies());
                }
            }
            header_tmp++;
        }
        writeToLBFile("[/list]");
    }

    /**
     * This method will take the list of participants and print the plat leaderboard in BBCode
     * @param members - the list of PSTMembers
     */
    public void printBBCodePlatCountLB(List<PSTMember> members) {

        // Sort members
        Comparator<PSTMember> pstMemberComparator = new Comparator<PSTMember>() {
            @Override
            public int compare(PSTMember o1, PSTMember o2) {
                if (o1.getCurrent_platinums() == o2.getCurrent_platinums()) return 0;
                else if (o1.getCurrent_platinums() > o2.getCurrent_platinums()) return -1;
                else return 1;
            }
        };
        Collections.sort(members, pstMemberComparator);

        writeToLBFile("*********************");
        writeToLBFile("\n[center][img]https://i.imgur.com/wlkGvME.png[/img][/center]\n");
        int header_tmp = 1;
        writeToLBFile("[indent][size=4][b][url=https://media.giphy.com/media/C767ptzM7gdUc/giphy.gif][color=blue]These[/color][/url] [url=https://media1.tenor.com/images/eac7bad50e78dd4379e328c68120596e/tenor.gif][color=blue]trophies[/color][/url] [url=https://cdn-images-1.medium.com/max/1600/1*ZTBtKosGpleB6sf_VVpvxA.gif][color=blue]are[/color][/url] [url=https://media3.giphy.com/media/geg3Ltfrc6FZC/giphy.gif][color=blue]making[/color][/url] [url=https://zippy.gfycat.com/GenerousContentAsiansmallclawedotter.webm][color=blue]me[/color][/url] [url=https://www.myjewishlearning.com/wp-content/uploads/2017/09/giphy-68.gif][color=blue]thirsty[/url]![/color][/b][/size][/indent]");
        writeToLBFile("[list=1]");
        for (PSTMember member : members) {
            if (header_tmp == 22) {
                writeToLBFile("\n[size=4][url=http://gifimage.net/wp-content/uploads/2017/08/serenity-now-gif-13.gif][color=blue][b]SERENITY NOW[/b][/color][/url][/size]\n");
            }
            if (header_tmp == 43) {
                writeToLBFile("\n[size=4][url=http://i.imgur.com/7g9LBaz.gifv][color=blue][b]I Don't Want to be a Pirate![/b][/color][/url][/size]\n");
            }
            if (header_tmp == 64) {
                writeToLBFile("\n[size=4][url=https://giant.gfycat.com/BlackCluelessBergerpicard.webm][color=blue][b]Little[/color][/url] [url=http://i.imgur.com/NaH9PD5.gif][color=blue]Kicks[/color][/url][/b][/size]\n");
            }
            if (header_tmp == 85) {
                writeToLBFile("\n[size=4][url=http://i.imgur.com/u9G3isF.gifv][color=blue][b]Anti-Platite[/b][/color][/url][/size]\n");
            }
            if (header_tmp == 106) {
                writeToLBFile("\n[size=4][url=https://img.buzzfeed.com/buzzfeed-static/static/2014-06/5/12/enhanced/webdr04/enhanced-24620-1401984756-25.jpg?downsize=715:*&output-format=auto&output-quality=auto][color=blue][b]I'm miserable, so I might as well be comfortable.[/b][/color][/url][/size]\n");
            }
            if (member.getPst_name().equals(member.getPsn_name())) {
                writeToLBFile("[*][B]" + member.getPst_name() + "[/B] - " + member.getCurrent_platinums());
            } else {
                writeToLBFile("[*][B]" + member.getPst_name() + "[/B] (" + member.getPsn_name() + ") - " + member.getCurrent_platinums());
            }

            header_tmp++;
        }
        writeToLBFile("[/list]");
    }

    public void printBBCodeInterTeamLB(List<THLTeam> teams) {
        writeToLBFile("*********************");
        writeToLBFile("\n[center][url=https://giant.gfycat.com/MildZealousFlyinglemur.webm][img]https://i.imgur.com/TddD0lf.png[/img][/url][/center]\n");

        // Sort members by trophy score
        Comparator<PSTMember> memberTrophyComparator = new Comparator<PSTMember>() {
            @Override
            public int compare(PSTMember o1, PSTMember o2) {
                if (o1.getCurrent_trophies() == o2.getCurrent_trophies()) return 0;
                else if (o1.getCurrent_trophies() > o2.getCurrent_trophies()) return -1;
                else return 1;
            }
        };

        for (THLTeam team : teams) {
            writeToLBFile("\n[B]" + team.getTeamName() + "[/B]");
            writeToLBFile("[list]");
            List<PSTMember> currentTeam = team.getTeamMembers();
            Collections.sort(currentTeam, memberTrophyComparator);

            for (PSTMember member : currentTeam) {
                if (member.getPst_name().equals(member.getPsn_name())) {
                    writeToLBFile("[*][B]" + member.getPst_name() + "[/B] - " + member.getCurrent_trophies());
                } else {
                    writeToLBFile("[*][B]" + member.getPst_name() + "[/B] (" + member.getPsn_name() + ") - " + member.getCurrent_trophies());
                }
            }
            writeToLBFile("[/list]\n");
        }
    }

    private void writeToLBFile(String line) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
        String filename = ".\\shared\\" + dtf.format(LocalDateTime.now()) + "-lb-bbcode.txt";
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
        List<THLTeam> thlTeams = new ArrayList<>();
        List<PSTMember> teamMembers = new ArrayList<>();
        PSTMember slamz = new PSTMember("Slamma", "slammajamma28", "Test Team", 4332, 50);
        PSTMember velvz = new PSTMember("Velvet", "Blood_Velvet", "Test Team", 1234, 50);
        teamMembers.add(slamz);
        teamMembers.add(velvz);
        THLTeam testTeam = new THLTeam("Test Team", teamMembers);
        thlTeams.add(testTeam);
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
        List<PSTMember> everyone = new ArrayList<>();
        everyone.add(slamz);
        everyone.add(velvz);
//        everyone.add(jay);
//        everyone.add(zet);
//        everyone.add(mende);
//        everyone.add(bread);
//
        BBCodeLB bbclb = new BBCodeLB();
//        bbclb.printBBCodeTeamLB(thlTeams);
        bbclb.printBBCodeIndividualLB(everyone);
//        bbclb.printBBCodePlatCountLB(everyone);
//        bbclb.printBBCodeInterTeamLB(thlTeams);
    }
}