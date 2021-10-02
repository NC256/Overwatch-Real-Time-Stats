package com.github.NC256.overwatchstats.gamedata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ParseModeData {

    private static final Logger logger = LogManager.getLogger(ParseModeData.class);


    public static List<CanonicalMode> parse() {
        //TODO This needs to be done more robustly?
        try {
            File sourceMap = new File("src/main/resources/CanonicalModeData.txt");
            List<String> lines = Files.readAllLines(sourceMap.toPath());

            List<CanonicalMode> canonicalModes = new ArrayList<>();
            for (int i = 0; i < lines.size(); i += 2) { // for lines two at a time
                String modeName = lines.get(i);
                String teamOrFFA = lines.get(i+1);
                boolean teambased;
                if (teamOrFFA.equals("team")){
                    teambased = true;
                }
                else if (teamOrFFA.equals("free-for-all")){
                    teambased = false;
                }
                else{
                    logger.warn("Improper formatting for Canonical Mode Data text file.");
                    continue;
                }
                canonicalModes.add(new CanonicalMode(modeName, teambased));
            }
            return canonicalModes;
        }
        catch (Exception e){
            logger.error("Couldn't parse mode names.");
            logger.error(e);
            return null;
        }
    }
}
