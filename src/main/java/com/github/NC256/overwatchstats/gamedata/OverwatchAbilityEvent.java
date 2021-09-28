package com.github.NC256.overwatchstats.gamedata;

import com.github.NC256.overwatchstats.logs.LogPatternType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum OverwatchAbilityEvent {
    PRIMARY_FIRE, SECONDARY_FIRE, ABILITY_1, ABILITY_2, ULTIMATE, CROUCH, MELEE;

    private static final Logger logger = LogManager.getLogger(OverwatchAbilityEvent.class);


    @Override
    public String toString() {
        switch (this){
            case PRIMARY_FIRE:
                return "Primary Fire";
            case SECONDARY_FIRE:
                return "Secondary Fire";
            case ABILITY_1:
                return "Ability 1";
            case ABILITY_2:
                return "Ability 2";
            case ULTIMATE:
                return "Ultimate";
            case CROUCH:
                return "Crouch";
            case MELEE:
                return "Melee";
            default:
                logger.warn("Unrecognized event type object. EventType enum needs updating.");
                return "UNKNOWN_EVENT";
        }
    }

    public static OverwatchAbilityEvent stringToEnum (String name){
        switch (name.toLowerCase()){
            case "primary fire":
                return OverwatchAbilityEvent.PRIMARY_FIRE;
            case "secondary fire":
                return OverwatchAbilityEvent.SECONDARY_FIRE;
            case "ability_1":
            case "ability 1":
                return OverwatchAbilityEvent.ABILITY_1;
            case "ability_2":
            case "ability 2":
                return OverwatchAbilityEvent.ABILITY_2;
            case "ultimate":
                return OverwatchAbilityEvent.ULTIMATE;
            case "crouch":
                return OverwatchAbilityEvent.CROUCH;
            case "melee":
                return OverwatchAbilityEvent.MELEE;
            default:
                logger.warn("Unrecognized event name: " + name);
                return null;
        }
    }

    public static OverwatchAbilityEvent fromLogType (LogPatternType type){
        switch (type){
            case ABILITY_1:
                return OverwatchAbilityEvent.ABILITY_1;
            case ABILITY_2:
                return OverwatchAbilityEvent.ABILITY_2;
            case ULTIMATE:
                return OverwatchAbilityEvent.ULTIMATE;
            default:
                logger.warn("Unable to convert Log Event Type to Ability Event Type.");
                return null;
        }
    }
}


