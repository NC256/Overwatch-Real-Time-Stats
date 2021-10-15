package com.github.NC256.overwatchstats.logs.patterns;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.gamedata.OverwatchAbilityEvent;
import com.github.NC256.overwatchstats.logs.LogPatternType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ABILITY_1, ABILITY_2, ULTIMATE
 * PLAYER_NAME,HERO_NAME
 */
public class LogPatternAbilityCounter extends LogPattern {

    private final Logger logger = LogManager.getLogger(this);

    private final String playerName, heroName;
    private final OverwatchAbilityEvent ability;

    public LogPatternAbilityCounter(int milliseconds, LogPatternType type, String line) {
        super(milliseconds, type);
        String[] tokens = line.split(",");
        playerName = tokens[0];
        heroName = tokens[1];
        ability = OverwatchAbilityEvent.fromLogType(type);
    }

    @Override
    public void updateStats(GameMatch match) {
        match.getPlayerByName(playerName).getHeroByName(heroName).incrementAbilityUse(ability);
    }
}
