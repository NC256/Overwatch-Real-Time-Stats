package com.github.NC256.overwatchstats.logs.patterns;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.logs.LogPatternType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ELIM,FINAL_BLOW
 * PLAYER,PLAYER_HERO,VICTIM,VICTIM_HERO
 */
public class LogPatternAttackAchievement extends LogPattern {

    private final Logger logger = LogManager.getLogger(this);

    private final String attackerName, attackerHero, victimName, victimHero;

    public LogPatternAttackAchievement(int millisecondsSinceStart, LogPatternType type, String line) {
        super(millisecondsSinceStart, type);
        String[] tokens = line.split(",");
        attackerName = tokens[0];
        attackerHero = tokens[1];
        victimName = tokens[2];
        victimHero = tokens[3];
    }

    @Override
    public void updateStats(GameMatch match) {
        // Deaths are counted by the DEATH event, don't double-count here!
        if (super.getMessageType() == LogPatternType.ELIM) {
            match.getPlayerByName(attackerName).getHeroByName(attackerHero).incrementElimnations();
        } else if (super.getMessageType() == LogPatternType.FINAL_BLOW) {
            match.getPlayerByName(attackerName).getHeroByName(attackerHero).incrementFinalBlows();
        } else {
            logger.warn("Unexpected logPattern type.");
        }
    }
}
