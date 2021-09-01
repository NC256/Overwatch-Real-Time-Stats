package com.github.NC256.overwatchstats.gamedata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum OverwatchEventType {
    PRIMARY_FIRE, SECONDARY_FIRE, ABILITY_1, ABILITY_2, ULTIMATE, CROUCH;

    private static final Logger logger = LogManager.getLogger(OverwatchEventType.class);


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
            default:
                logger.error("Unrecognized event type object. Switch needs updating.");
                return null;
        }
    }

    public static OverwatchEventType stringToEnum (String name){
        switch (name.toLowerCase()){
            case "primary fire":
                return OverwatchEventType.PRIMARY_FIRE;
            case "secondary fire":
                return OverwatchEventType.SECONDARY_FIRE;
            case "ability_1":
            case "ability 1":
                return OverwatchEventType.ABILITY_1;
            case "ability_2":
            case "ability 2":
                return OverwatchEventType.ABILITY_2;
            case "ultimate":
                return OverwatchEventType.ULTIMATE;
            case "crouch":
                return OverwatchEventType.CROUCH;
            default:
                logger.warn("Unrecognized event name.");
                return null;
        }
    }
}


