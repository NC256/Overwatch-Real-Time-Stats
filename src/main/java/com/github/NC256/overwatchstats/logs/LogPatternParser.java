package com.github.NC256.overwatchstats.logs;

import com.github.NC256.overwatchstats.logs.patterns.LogPattern;
import com.github.NC256.overwatchstats.logs.patterns.LogPatternAbilityCounter;
import com.github.NC256.overwatchstats.logs.patterns.LogPatternAttackAchievement;
import com.github.NC256.overwatchstats.logs.patterns.LogPatternInitial;
import com.github.NC256.overwatchstats.logs.patterns.LogPatternNumericalGift;
import com.github.NC256.overwatchstats.logs.patterns.LogPatternPersonalAchievement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public final class LogPatternParser {

    private static final Logger logger = LogManager.getLogger(LogPatternParser.class);

    public static final String PREFIX = "[INTEL]";

    /**
     * ---
     * Group 1: Ability triggers
     * ABILITY_1, ABILITY_2, ULTIMATE
     * #EVENT_NAME,PLAYER_CONTEXT,HERO_CONTEXT
     * ---
     * Group 2: Numerical Gifts
     * DAMAGE_DEALT, HEALING_DEALT
     * #EVENT_NAME,GIFTER,GIFTER_HERO,GIFTER_ABILITY,AMT_GIFTED,RECEIVER,RECEIVER_HERO
     * ---
     * Group 3: Attacking Achievements
     * ELIM,FINAL_BLOW
     * #EVENT_NAME,PLAYER,PLAYER_HERO,VICTIM,VICTIM_HERO
     * ---
     * Group 4: Personal Achievements
     * DEATH
     * #EVENT_NAME,PLAYER,PLAYER_HERO
     * ---
     * Group 5: Initial Print
     * INITIAL
     * MAP_NAME,GAME_MODE,[Team_1_Players],[Team_2_Players]
     *
     */
    public static final LogPattern parseLine(String line) {
        if (!line.contains(PREFIX)){
            return null;
        }
        String lineMinusPrefix = line.substring(line.indexOf(PREFIX) + PREFIX.length());
        String[] tokens = lineMinusPrefix.split(",");

        // 173.29 seconds --> 173290 milliseconds
        int millisecondsSinceStart = Integer.parseInt(tokens[0].replace(".","")) * 10;
        LogPatternType type = LogPatternType.valueOf(tokens[1]);

        String justRemainingData = String.join(",", Arrays.asList(tokens).subList(2, tokens.length));

        LogPattern parsedLine;
        try {
            switch (type) {
                case ABILITY_1:
                case ABILITY_2:
                case ULTIMATE:
                    parsedLine = new LogPatternAbilityCounter(millisecondsSinceStart, type, justRemainingData);
                    break;
                case DAMAGE_DEALT:
                case HEAL_DEALT:
                    parsedLine = new LogPatternNumericalGift(millisecondsSinceStart, type, justRemainingData);
                    break;
                case ELIM:
                case FINAL_BLOW:
                    parsedLine = new LogPatternAttackAchievement(millisecondsSinceStart, type, justRemainingData);
                    break;
                case DEATH:
                    parsedLine = new LogPatternPersonalAchievement(millisecondsSinceStart, type, justRemainingData);
                    break;
                case INITIAL:
                    parsedLine = new LogPatternInitial(millisecondsSinceStart, type, justRemainingData);
                    break;
                default:
                    parsedLine = null;
            }
            return parsedLine;
        }
        catch (Exception e){
            logger.warn("Unable to parse the following: " + line);
        }
        return null;
    }
}
