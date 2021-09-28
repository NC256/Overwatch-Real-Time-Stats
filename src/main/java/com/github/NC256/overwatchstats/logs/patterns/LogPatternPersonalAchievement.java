package com.github.NC256.overwatchstats.logs.patterns;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.logs.LogPatternType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DEATH
 * PLAYER,PLAYER_HERO
 */
public class LogPatternPersonalAchievement extends LogPattern {

    private final Logger logger = LogManager.getLogger(this);
    private final String playerName, heroName;

    public LogPatternPersonalAchievement(int millisecondsSinceStart, LogPatternType type, String line) {
        super(millisecondsSinceStart, type);
        String[] tokens = line.split(",");
        playerName = tokens[0];
        heroName = tokens[1];
    }

    @Override
    public void updateStats(GameMatch match) {
        match.getPlayerByName(playerName).getHeroByName(heroName).incrementDeaths();
    }
}
