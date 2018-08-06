package com.pst.slamma;

import java.util.List;

public class GameAndTrophy {

    public List<Game> game;
    public List<Trophy> trophies;

    public GameAndTrophy(List<Game> g, List<Trophy> t) {
        this.setGame(g);
        this.setTrophies(t);
    }

    public List<Game> getGame() {
        return game;
    }

    public void setGame(List<Game> game) {
        this.game = game;
    }

    public List<Trophy> getTrophies() {
        return trophies;
    }

    public void setTrophies(List<Trophy> trophies) {
        this.trophies = trophies;
    }
}
