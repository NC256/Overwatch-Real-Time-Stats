package com.github.NC256.overwatchstats.logs;

/**
 * Any log line printed from the associated workshop code should start with one of these words
 */
public enum LogPatternType {
    INITIAL, DAMAGE_DEALT, HEAL_DEALT, FINAL_BLOW, ELIM, DEATH, ABILITY_1, ABILITY_2, ULTIMATE
}
