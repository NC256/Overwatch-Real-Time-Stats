package com.github.NC256.overwatchstats.gamedata;

import com.github.NC256.overwatchstats.Hero;

import java.util.List;

public class CanonicalHero implements Hero {

    final String name;
    final List<CanonicalAbility> abilityNames;

    public CanonicalHero(String name, List<CanonicalAbility> abilityNames){
        this.name = name;
        this.abilityNames = abilityNames;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getAbilityName (OverwatchEventType eventType){
        for (CanonicalAbility abilityName : abilityNames) {
            if (abilityName.eventType == eventType){
                return abilityName.abilityName;
            }
        }
        return "Didn't find mappable name";
    }
}

