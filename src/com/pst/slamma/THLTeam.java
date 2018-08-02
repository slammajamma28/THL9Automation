package com.pst.slamma;

import java.util.ArrayList;
import java.util.List;

public class THLTeam {

    public String teamName;
    public int team_score;
    public List<PSTMember> teamMembers;

    public THLTeam(String name) {
        setTeamName(name);
        setTeam_score(0);
        setTeamMembers(new ArrayList<>());
//        setTeamMembers(team);
//        setTeam_score(calculateScore());
    }

    public THLTeam(String name, List<PSTMember> team ) {
        setTeamName(name);
        setTeamMembers(team);
        setTeam_score(calculateScore());
    }

    public int calculateScore() {
        int tmpScore = 0;
        for (PSTMember member : getTeamMembers()) {
            tmpScore += member.getCurrent_trophies();
        }
        return tmpScore;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeam_score() {
        return team_score;
    }

    public void setTeam_score(int team_score) {
        this.team_score = team_score;
    }

    public List<PSTMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<PSTMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public void addTeamMember(PSTMember name) {
        List<PSTMember> temp = getTeamMembers();
        temp.add(name);
        setTeamMembers(temp);
    }

    public void getIndividualScores() {
        for (PSTMember person : getTeamMembers()) {
            System.out.println(person.getPst_name() + "(" + person.getPsn_name() + "): " + person.getTotal_trophies());
        }
    }

    // Testing purposes only
    public static void main(String args[]) {
        List<PSTMember> edTeam = new ArrayList<>();
        edTeam.add(new PSTMember("Slamma", "slammajamma28", "Test Team", 0, 0));
        THLTeam slamTeam = new THLTeam("ED Team", edTeam);

        System.out.println("THIS IS TEAM " + slamTeam.getTeamName());
    }
}
