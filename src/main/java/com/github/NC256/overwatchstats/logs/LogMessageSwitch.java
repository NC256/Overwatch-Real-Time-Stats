package com.github.NC256.overwatchstats.logs;

import com.github.NC256.overwatchstats.gamedata.GameMatch;

import java.util.Scanner;

public class LogMessageSwitch extends LogMessage{

    private String player, target;

    protected LogMessageSwitch(int milliseconds, LogMessageType eventType, Scanner line) {
        super(milliseconds, eventType);
        this.player = line.next();
        this.target = line.next();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public void parseStats(GameMatch match) {

    }
}
