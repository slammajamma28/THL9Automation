package com.pst.slamma;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Game {

    private DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy h:mm:ss a");

    private int game_id;
    private List<String> platforms;
    private boolean ps3_game;
    private boolean vita_game;
    private boolean ps4_game;
    private String game_name;
    private int game_owners;
    private boolean plat_game;
    private String game_dev;
    private String game_publisher;
    private List<String> genre;
    private List<String> modes;
    private String url;
    private List<String> themes;
    private LocalDateTime start_timestamp;
    private LocalDateTime end_thl_timestamp;
    private int total_trophies;
    private boolean completed_during_thl;

    public Game(int a, String b) {
        this.setGame_id(a);
        this.setUrl(b);
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public int getGame_owners() {
        return game_owners;
    }

    public void setGame_owners(int game_owners) {
        this.game_owners = game_owners;
    }

    public boolean isPlat_game() {
        return plat_game;
    }

    public void setPlat_game(boolean plat_game) {
        this.plat_game = plat_game;
    }

    public String getGame_dev() {
        return game_dev;
    }

    public void setGame_dev(String game_dev) {
        this.game_dev = game_dev;
    }

    public String getGame_publisher() {
        return game_publisher;
    }

    public void setGame_publisher(String game_publisher) {
        this.game_publisher = game_publisher;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
        for (String platform : platforms) {
            if (platform.equals("PS3")) this.setPs3_game(true);
            if (platform.equals("Vita")) this.setVita_game(true);
            if (platform.equals("PS4")) this.setPs4_game(true);
        }
    }

    public List<String> getThemes() { return themes; }

    public void setThemes(List<String> themes) { this.themes = themes; }

    public LocalDateTime getStart_timestamp() { return start_timestamp; }

    public void setStart_timestamp(String start_time) {
        this.start_timestamp = LocalDateTime.parse(start_time, DATE_FORMAT);
    }

    public LocalDateTime getEnd_thl_timestamp() { return end_thl_timestamp; }

    public void setEnd_thl_timestamp(String end_time) {
        this.end_thl_timestamp = LocalDateTime.parse(end_time, DATE_FORMAT);;
    }

    public int getTotal_trophies() { return total_trophies; }

    public void setTotal_trophies(int total_trophies) { this.total_trophies = total_trophies; }

    public boolean isPs3_game() { return ps3_game; }

    public void setPs3_game(boolean ps3_game) { this.ps3_game = ps3_game; }

    public boolean isVita_game() { return vita_game; }

    public void setVita_game(boolean vita_game) { this.vita_game = vita_game; }

    public boolean isPs4_game() { return ps4_game; }

    public void setPs4_game(boolean ps4_game) { this.ps4_game = ps4_game; }

    public boolean isCompleted_during_thl() { return completed_during_thl; }

    public void setCompleted_during_thl(boolean completed_during_thl) { this.completed_during_thl = completed_during_thl; }
}
