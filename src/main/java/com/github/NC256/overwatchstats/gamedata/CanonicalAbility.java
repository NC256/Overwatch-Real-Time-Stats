package com.github.NC256.overwatchstats.gamedata;

public class CanonicalAbility {
    final String abilityName;
    final OverwatchEventType eventType;

    public CanonicalAbility(String abilityName, OverwatchEventType eventType) {
        this.abilityName = abilityName;
        this.eventType = eventType;
    }
}
