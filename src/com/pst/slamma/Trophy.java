package com.pst.slamma;

import java.time.LocalDateTime;

public class Trophy {

    public int game_id;
    public String trophy_title;
    public String trophy_description;
    public int log_num;
    public LocalDateTime timestamp;
    public int achievers;
    public int owners;
    public String rarity_type;
    public double rarity_percentage;
    public String trophy_value;
    public LocalDateTime audit_timestamp;
    public String game_url;

    public Trophy() {
        this.audit_timestamp = LocalDateTime.now();
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getTrophy_title() {
        return trophy_title;
    }

    public void setTrophy_title(String trophy_title) {
        this.trophy_title = trophy_title;
    }

    public String getTrophy_description() {
        return trophy_description;
    }

    public void setTrophy_description(String trophy_description) {
        this.trophy_description = trophy_description;
    }

    public int getLog_num() {
        return log_num;
    }

    public void setLog_num(int log_num) {
        this.log_num = log_num;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getAchievers() {
        return achievers;
    }

    public void setAchievers(int achievers) {
        this.achievers = achievers;
    }

    public int getOwners() {
        return owners;
    }

    public void setOwners(int owners) {
        this.owners = owners;
    }

    public String getRarity_type() {
        return rarity_type;
    }

    public void setRarity_type(String rarity_type) {
        this.rarity_type = rarity_type;
    }

    public String getTrophy_value() {
        return trophy_value;
    }

    public void setTrophy_value(String trophy_value) {
        this.trophy_value = trophy_value;
    }

    public LocalDateTime getAudit_timestamp() {
        return audit_timestamp;
    }

    public void setAudit_timestamp(LocalDateTime audit_timestamp) {
        this.audit_timestamp = audit_timestamp;
    }

    public double getRarity_percentage() { return rarity_percentage; }

    public void setRarity_percentage(double rarity_percentage) { this.rarity_percentage = rarity_percentage; }

    public String getGame_url() { return game_url; }

    public void setGame_url(String game_url) { this.game_url = game_url; }
}
