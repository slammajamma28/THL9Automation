package com.pst.slamma;

import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This main class will read in the list of participants and then all the work will continue in other classes.
 */
public class THLAutomation {

    public static void main (String args[]) {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        List<PSTMember> team_members = new ArrayList<>();
        List<THLTeam> thl_teams = new ArrayList<>();
        List<PSTMember> all_members = new ArrayList<>();

        String line;
        String team_name = null;
        try {
            FileReader fr = new FileReader("participant_list");
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if(line.substring(0,1).equals("+")) {
                    if (team_name == null) team_name=line.substring(1);
                    else {
                        thl_teams.add(new THLTeam(team_name, team_members));
                        team_members = new ArrayList<>();
                        team_name = line.substring(1);
                    }
                } else {
                    List<String> id = Arrays.asList(line.split(","));
                    PSTMember newMember = new PSTMember(id.get(0), id.get(1), id.get(2),
                            Integer.parseInt(id.get(3)), Integer.parseInt(id.get(4)));
                    team_members.add(newMember);
                    all_members.add(newMember);
                }
            }
            thl_teams.add(new THLTeam(team_name, team_members));
        } catch (IOException e1) {
            System.out.println("File issue.");
            e1.printStackTrace();
        }

        // Sort teams alphabetically
        Comparator<THLTeam> alphabeticalTeam = new Comparator<THLTeam>() {
            @Override
            public int compare(THLTeam o1, THLTeam o2) {
                return o1.getTeamName().compareToIgnoreCase(o2.getTeamName());
            }
        };
        Collections.sort(thl_teams, alphabeticalTeam);

        // Sort members alphabetically
        Comparator<PSTMember> alphabeticalSolo = new Comparator<PSTMember>() {
            @Override
            public int compare(PSTMember o1, PSTMember o2) {
                return o1.getPst_name().compareToIgnoreCase(o2.getPst_name());
            }
        };
        Collections.sort(all_members, alphabeticalSolo);

        BBCodeLB lb = new BBCodeLB();
        lb.printBBCodeTeamLB(thl_teams);
        lb.printBBCodeIndividualLB(all_members);
        lb.printBBCodePlatCountLB(all_members);
        Collections.sort(thl_teams, alphabeticalTeam);
        lb.printBBCodeInterTeamLB(thl_teams);

        System.out.println("\n ********************* \n");

        System.out.println("Exporting main trophy data to CSV...");

        Collections.sort(thl_teams, alphabeticalTeam);
        Collections.sort(all_members, alphabeticalSolo);

        ExportData ed = new ExportData();
        ed.exportTeams(thl_teams);
        ed.exportIndividuals(all_members);

        System.out.println("\n *********************");
        System.out.println("Begin 90s Trophy Check");
        System.out.println("********************* \n");

        NinetiesTrophies ninetiesTrophies = new NinetiesTrophies();
        ninetiesTrophies.checkNinetiesTrophies();

        System.out.println("\n *********************");
        System.out.println("Begin Unique Games Check");
        System.out.println("********************* \n");

        UniqueGames uniqueGames = new UniqueGames();
        uniqueGames.checkGameCompletions();

        System.out.println("\n ********************* \n");

        stopwatch.stop();
        System.out.println(stopwatch.getTime(TimeUnit.MILLISECONDS) + " ms elapsed");
    }

}