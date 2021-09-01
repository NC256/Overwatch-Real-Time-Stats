package com.github.NC256.overwatchstats.logs;

import com.github.NC256.overwatchstats.gamedata.GameMatch;

import java.util.Scanner;

public class LogMessageAbilityUsed extends LogMessage{
    private String player, hero;

    protected LogMessageAbilityUsed(int milliseconds, LogMessageType eventType, Scanner line) {
        super(milliseconds, eventType);
        this.player = line.next();
        this.hero = line.next();
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public void parseStats(GameMatch match) {

    }
}
