package com.github.NC256.overwatchstats.logs;

import java.util.Scanner;

public final class LogMessageParser {

    public static final String PREFIX = "[INTEL]";

    /**
     * Given a String (presumably from Overwatch's output log file)
     * this method converts it to a LogMessage object
     * Returns null if it isn't prefixed or doesn't match a recognized type
     */
    public static final LogMessage parseLine(String line) {
        if (!line.contains(PREFIX)){
            return null;
        }
        String lineMinusPrefix = line.substring(line.indexOf(PREFIX) + PREFIX.length());
        Scanner lineParser = new Scanner(lineMinusPrefix).useDelimiter(",");

        // 173.29 seconds --> 173290 milliseconds
        int millisecondsSinceStart = Integer.parseInt(lineParser.next().replace(".","")) * 10;
        LogMessageType type = LogMessageType.valueOf(lineParser.next());
        switch (type){
            case DAMAGE_DEALT:
            case HEAL_DEALT:
                return new LogMessageDamageOrHeal(millisecondsSinceStart, type, lineParser);
            case SWITCH_HERO:
                //case SWITCH_TEAM:
                return new LogMessageSwitch(millisecondsSinceStart, type, lineParser);
            case ELIM:
                return new LogMessageElimination(millisecondsSinceStart,type,lineParser);
            case DEATH:
                return new LogMessageDeath(millisecondsSinceStart,type, lineParser);
            case ALIVENESS:
            case FINAL_BLOW:
                return new LogMessageSingleValue(millisecondsSinceStart, type, lineParser);
            case INITIAL:
                return new LogMessageInitial(millisecondsSinceStart, type, lineParser);
            case ABILITY_1:
            case ABILITY_2:
            case ULTIMATE:
                return new LogMessageAbilityUsed(millisecondsSinceStart, type, lineParser);
            default:
                return null;
        }
    }
}
