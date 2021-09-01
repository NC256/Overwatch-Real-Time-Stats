package com.github.NC256.overwatchstats.logs;

import com.github.NC256.overwatchstats.gamedata.GameMatch;

import java.util.Scanner;

public class LogMessageDamageOrHeal extends LogMessage {

    private String sourcePlayer, sourceHero, targetPlayer, targetHero;
    private double value;

    LogMessageDamageOrHeal(int milliseconds, LogMessageType eventType, Scanner line){
        super(milliseconds, eventType);
        this.sourcePlayer = line.next();
        this.sourceHero = line.next();
        this.value = line.nextDouble();
        this.targetPlayer = line.next();
        this.targetHero = line.next();
    }

    public String getSourcePlayer() {
        return sourcePlayer;
    }

    public void setSourcePlayer(String sourcePlayer) {
        this.sourcePlayer = sourcePlayer;
    }

    public String getSourceHero() {
        return sourceHero;
    }

    public void setSourceHero(String sourceHero) {
        this.sourceHero = sourceHero;
    }

    public String getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(String targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public String getTargetHero() {
        return targetHero;
    }

    public void setTargetHero(String targetHero) {
        this.targetHero = targetHero;
    }

    @Override
    public void parseStats(GameMatch match) {

    }
}
