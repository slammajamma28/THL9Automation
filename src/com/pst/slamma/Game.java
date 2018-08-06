package com.pst.slamma;

import java.util.List;

public class Game {

    private int game_id;
    private List<String> platforms;
    private String game_name;
    private int game_owners;
    private boolean plat_game;
    private String game_dev;
    private String game_publisher;
    private List<String> genre;
    private List<String> modes;
    private String url;


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
    }
}
