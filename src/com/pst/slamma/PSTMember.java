package com.pst.slamma;

import java.util.List;

/**
 * This class will be the object that stores all the information about a user
 */
public class PSTMember {

    private String pst_name;
    private String psn_name;
    private String team_name;
    private int total_trophies;
    private int total_platinum;
    private int total_gold;
    private int total_silver;
    private int total_bronze;
    private int psn_level;
    private int start_trophies;
    private int current_trophies;
    private int start_platinums;
    private int current_platinums;
    private int last_log_processed;
    private int nineties_count;

    /**
     * This is the constructor for the PST Member object
     * The PST and PSN name will be passed in and then the constructor will continue
     * by calling the pullInfo to grab necessary information.
     * @param pst
     * @param psn
     * @param team
     */
    public PSTMember(String pst, String psn, String team, int startTrophies, int startPlatinums) {
        setPst_name(pst);
        setPsn_name(psn);
        setTeam_name(team);
        setStart_trophies(startTrophies);
        setStart_platinums(startPlatinums);
        System.out.println("Pulling info for " + pst + " (" + psn + ")...");
        pullInfo();
//        printItAll();
    }

    private void pullInfo() {
        List<Integer> userInfo = ScrapePSNP.pullInfo(getPsn_name());

        setPsn_level(userInfo.get(0));
        setTotal_trophies((userInfo.get(1)));
        setTotal_platinum((userInfo.get(2)));
        setTotal_gold(userInfo.get(3));
        setTotal_silver(userInfo.get(4));
        setTotal_bronze(userInfo.get(5));
        setCurrent_trophies();
        setCurrent_platinums();
    }

    private void findNewGameCompletions() {

    }

    private void printItAll() {
        System.out.println("PST Name:\t" + getPst_name());
        System.out.println("PSN Name:\t" + getPsn_name());
        System.out.println("Team Name:\t" + getTeam_name());
        System.out.println("Level:\t" + getPsn_level());
        System.out.println("Total:\t" + getTotal_trophies());
        System.out.println("Platinums:\t" + getTotal_platinum());
        System.out.println("Golds:\t" + getTotal_gold());
        System.out.println("Silvers:\t" + getTotal_silver());
        System.out.println("Bronzes:\t" + getTotal_bronze());
        System.out.println("Current Trophs:\t\t" + getCurrent_trophies());
        System.out.println("Current Plats:\t\t" + getCurrent_platinums());
    }

    public String getPst_name() { return pst_name; }

    public void setPst_name(String pst_name) { this.pst_name = pst_name; }

    public String getPsn_name() { return psn_name; }

    public void setPsn_name(String psn_name) { this.psn_name = psn_name; }

    public int getTotal_trophies() { return total_trophies; }

    public void setTotal_trophies(int total_trophies) { this.total_trophies = total_trophies; }

    public int getTotal_platinum() {
        return total_platinum;
    }

    public void setTotal_platinum(int total_platinum) {
        this.total_platinum = total_platinum;
    }

    public int getTotal_gold() {
        return total_gold;
    }

    public void setTotal_gold(int total_gold) {
        this.total_gold = total_gold;
    }

    public int getTotal_silver() {
        return total_silver;
    }

    public void setTotal_silver(int total_silver) {
        this.total_silver = total_silver;
    }

    public int getTotal_bronze() {
        return total_bronze;
    }

    public void setTotal_bronze(int total_bronze) {
        this.total_bronze = total_bronze;
    }

    public int getPsn_level() {
        return psn_level;
    }

    public void setPsn_level(int psn_level) {
        this.psn_level = psn_level;
    }

    public String getTeam_name() { return team_name; }

    public void setTeam_name(String team_name) { this.team_name = team_name; }

    public int getStart_trophies() { return start_trophies; }

    public void setStart_trophies(int start_trophies) { this.start_trophies = start_trophies; }

    public int getCurrent_trophies() { return current_trophies; }

    public void setCurrent_trophies() { this.current_trophies = getTotal_trophies() - getStart_trophies(); }

    public int getStart_platinums() { return start_platinums; }

    public void setStart_platinums(int start_platinums) { this.start_platinums = start_platinums; }

    public int getCurrent_platinums() { return current_platinums; }

    public void setCurrent_platinums() { this.current_platinums = getTotal_platinum() - getStart_platinums(); }

    public int getLast_log_processed() { return last_log_processed; }

    public void setLast_log_processed(int last_log_processed) { this.last_log_processed = last_log_processed; }

    public int getNineties_count() { return nineties_count; }

    public void setNineties_count(int nineties_count) { this.nineties_count = nineties_count; }

    // Testing purposes only
    public static void main(String args[]) {
        // This is to test the actual information pulled from PSNP
//        PSTMember slamz = new PSTMember("Slamma", "slammajamma28", "Test Team", 4332, 67, 7555, 3);
        PSTMember slamz = new PSTMember("Slamma", "slammajamma28", "Test Team", 4332, 67);
//        List<Integer> testResult = pullInfo("slammajamma28");
//        for (int i : testResult) {
//            System.out.println(i);
//        }
    }
}
