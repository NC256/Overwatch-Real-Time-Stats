package com.github.NC256.overwatchstats.gamedata;

public class CanonicalMode {
    final private String modeName;
    final private boolean teamMode;

    public CanonicalMode(String modeName, boolean teamMode) {
        this.modeName = modeName;
        this.teamMode = teamMode;
    }

    public String getModeName() {
        return modeName;
    }

    public boolean isTeamMode() {
        return teamMode;
    }
}
