package com.github.NC256.overwatchstats.gamedata;

import com.github.NC256.overwatchstats.logs.LogMessageType;

import java.util.ArrayList;

class PlayerHero {
    String name;
    ArrayList<LogMessageType> events = new ArrayList<>();
    int timePlayed; //in seconds
    int deaths, healingDone, healingReceived, objectiveTime;
    int allDamageDone, heroDamageDone, barrierDamageDone;
    int eliminations, finalBlows, soloKills, objectiveKills, environmentalKills;
    int weaponAccuracy; //as percent
}
