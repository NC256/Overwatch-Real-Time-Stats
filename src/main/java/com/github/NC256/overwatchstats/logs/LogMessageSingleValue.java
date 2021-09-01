package com.github.NC256.overwatchstats.logs;

import com.github.NC256.overwatchstats.gamedata.GameMatch;

import java.util.Scanner;

public class LogMessageSingleValue extends LogMessage{
    private String player, value;

    protected LogMessageSingleValue(int milliseconds, LogMessageType eventType, Scanner line) {
        super(milliseconds, eventType);
        this.player = line.next();
        this.value = line.next();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void parseStats(GameMatch match) {

    }
}
