package com.github.NC256.overwatchstats.gamedata;

import java.util.List;

/**
 * This class represents a canonical reference of all official game data loaded from configuration files.
 * Hero names, ability names, map mode types, etc, anything that needs reference should require this class.
 */
public class CanonicalGameData { // Represents all of the official reference data in the game, characters, maps,
    // abilities, names etc
    private final List<CanonicalHero> canonicalHeroes;
    private final List<CanonicalMode> canonicalModes;

    public CanonicalGameData(List<CanonicalHero> canonicalHeroes,
                             List<CanonicalMode> canonicalModes) {
        this.canonicalHeroes = canonicalHeroes;
        this.canonicalModes = canonicalModes;
    }

    public List<CanonicalHero> getCanonicalHeroes() {
        return canonicalHeroes;
    }

    public List<CanonicalMode> getCanonicalModes() {
        return canonicalModes;
    }

    public CanonicalHero getCanonicalHeroByName(String name) {
        return canonicalHeroes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    public CanonicalMode getCanonicalModeByName(String name) {
        return canonicalModes.stream().filter(x -> x.getModeName().equals(name)).findFirst().orElse(null);
    }
}
