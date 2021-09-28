package com.github.NC256.overwatchstats.gamedata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ParseHeroData {
    private static final Logger logger = LogManager.getLogger(ParseHeroData.class);

    public static List<CanonicalHero> parse() {
        //TODO This needs to be done more robustly?
        try {
            File sourceMap = new File("src/main/resources/HeroDamageSourceMap.txt");
            List<String> lines = Files.readAllLines(sourceMap.toPath());

            List<CanonicalHero> heroes = new ArrayList<>();
            for (int i = 0; i < lines.size(); i += 2) { // for lines two at a time
                String heroName = lines.get(i); // name comes first
                String[] abilityPairs = lines.get(i + 1).split(","); // big list of mapped pairs comes next
                List<CanonicalAbility> canonicalNamePairs = new ArrayList<>();
                for (String abilityPair : abilityPairs) { // Each pair is "Name:Ability_Type", needs splitting
                    String[] pair = abilityPair.split(";");
                    canonicalNamePairs.add(new CanonicalAbility(pair[0].trim(), OverwatchAbilityEvent.stringToEnum(pair[1].trim())));
                }
                heroes.add(new CanonicalHero(heroName, canonicalNamePairs));
            }
            return heroes;
        }
        catch (Exception e){
            logger.error("Couldn't parse hero ability names.");
            logger.error(e);
            return null;
        }
    }
}

