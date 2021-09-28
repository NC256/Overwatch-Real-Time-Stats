package com.github.NC256.overwatchstats.gamedata;

public class CanonicalAbility {
    final String abilityName;
    final OverwatchAbilityEvent eventType;

    public CanonicalAbility(String abilityName, OverwatchAbilityEvent eventType) {
        this.abilityName = abilityName;
        this.eventType = eventType;
    }
}
