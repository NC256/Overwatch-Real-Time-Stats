package com.github.NC256.overwatchstats.gamedata;

import java.util.ArrayList;
import java.util.List;

public class GameMatch {
    String gameMode; // assault/control/escort/hybrid/etc
    String mapName;
    int round; // only applicable on best-of-3 control modes

    List<Player> players = new ArrayList<>();
    boolean inProgress = true;

    public GameMatch(){

    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String playerName) {
        return players.stream().filter(x -> x.getName().equals(playerName)).findFirst().orElse(null);
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public String getMapName() {
        return mapName;
    }

    public String getGameMode() {
        return gameMode;
    }

    public int getRound() {
        return round;
    }

    public boolean isInProgress() {
        return inProgress;
    }
}
