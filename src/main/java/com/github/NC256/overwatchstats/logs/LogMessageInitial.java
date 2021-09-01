package com.github.NC256.overwatchstats.logs;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.gamedata.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogMessageInitial extends LogMessage{
    String mapName, gamemode;
    List<Player> players = new ArrayList<>();

    protected LogMessageInitial(int milliseconds, LogMessageType eventType, Scanner line) {
        super(milliseconds, eventType);
        this.mapName = line.next();
        this.gamemode = line.next();
        for (int i = 0; i < 12; i++) {
            players.add(new Player(line.next()));
        }
    }

    @Override
    public void parseStats(GameMatch match) {
        match.setMapName(mapName);
        match.setGameMode(gamemode);
        match.setPlayers(players);
    }
}
