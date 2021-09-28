package com.github.NC256.overwatchstats.logs;

// ALIVENESS is supposed to print whenever the player's life state changes
// Sadly, it does not.
// The following is the way it currently works:
// It never prints false. Use DEATH event instead.
// It prints true upon team switch but before the player can choose a hero. It is lying here, do not listen to it.
// It prints true upon game start but before the player can choose a hero. More lies.
// It prints true after the player respawns from dying. This is the truth.
// Verify this truth by walking backwards in time.
// If you find a SWITCH_TEAM event before you find a DEATH event, it is lying, otherwise it is telling the truth.





public enum LogPatternType {
    INITIAL, DAMAGE_DEALT, HEAL_DEALT, FINAL_BLOW, ELIM, DEATH, ABILITY_1, ABILITY_2, ULTIMATE
}
