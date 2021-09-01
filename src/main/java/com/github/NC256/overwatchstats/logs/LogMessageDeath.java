package com.github.NC256.overwatchstats.logs;

import com.github.NC256.overwatchstats.gamedata.GameMatch;

import java.util.Scanner;

public class LogMessageDeath extends LogMessage{
    String player;

    protected LogMessageDeath(int millisecondsSinceMatchStart, LogMessageType eventType, Scanner remainingLine) {
        super(millisecondsSinceMatchStart, eventType);
        player = remainingLine.next();
    }

    @Override
    public void parseStats(GameMatch match) {
        match.getPlayer(player).incrementDeaths();
    }
}
