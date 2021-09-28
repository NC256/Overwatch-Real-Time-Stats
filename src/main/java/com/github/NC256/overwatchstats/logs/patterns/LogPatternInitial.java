package com.github.NC256.overwatchstats.logs.patterns;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.logs.LogPatternType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * INITIAL
 * MAP_NAME,GAME_MODE,[Team_1_Players],[Team_2_Players]
 */
public class LogPatternInitial extends LogPattern {

    private final Logger logger = LogManager.getLogger(this);

    private final String mapName, modeName;

    private List<String> playerNames = new ArrayList<>();

    public LogPatternInitial(int millisecondsSinceStart, LogPatternType type, String line) {
        super(millisecondsSinceStart, type);
        String[] tokens = line.split(",");
        mapName = tokens[0];
        modeName = tokens[1];

        // As implicitly logged, the first 6 are Team 1, and the next 6 are Team 2
        playerNames.addAll(Arrays.asList(tokens).subList(2, 14));
    }

    @Override
    public void updateStats(GameMatch match) {
        match.parseInitialMessage(playerNames,mapName,modeName);
    }
}
