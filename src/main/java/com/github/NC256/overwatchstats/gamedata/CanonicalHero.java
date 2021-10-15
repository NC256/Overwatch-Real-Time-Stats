package com.github.NC256.overwatchstats.gamedata;

import java.util.List;

public class CanonicalHero {

    private final String name;
    private final List<CanonicalAbility> abilityNames;

    public CanonicalHero(String name, List<CanonicalAbility> abilityNames) {
        this.name = name;
        this.abilityNames = abilityNames;
    }

    public String getName() {
        return name;
    }

    public String getAbilityName(OverwatchAbilityEvent eventType) {
        for (CanonicalAbility abilityName : abilityNames) {
            if (abilityName.eventType == eventType) {
                return abilityName.abilityName;
            }
        }
        return "UNKNOWN_ABILITY";
    }
}

