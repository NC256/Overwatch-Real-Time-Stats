package com.github.NC256.overwatchstats;

import com.github.NC256.overwatchstats.gamedata.CanonicalAbility;
import com.github.NC256.overwatchstats.gamedata.CanonicalHero;
import com.github.NC256.overwatchstats.gamedata.OverwatchEventType;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ParseDamageSourceNames {
    public static List<CanonicalHero> parse() {
        try {
            File sourceMap = new File("HeroDamageSourceMap.txt");
            List<String> lines = Files.readAllLines(sourceMap.toPath());

            List<CanonicalHero> heroes = new ArrayList<>();
            for (int i = 0; i < lines.size(); i += 2) {
                String heroName = lines.get(i);
                String[] abilityPairs = lines.get(i + 1).split(",");
                List<CanonicalAbility> canonicalNamePairs = new ArrayList<>();
                for (String abilityPair : abilityPairs) {
                    String[] pair = abilityPair.split(":");
                    //System.out.println(Arrays.toString(pair));
                    canonicalNamePairs.add(new CanonicalAbility(pair[0].trim(), OverwatchEventType.stringToEnum(pair[1].trim())));
                }
                heroes.add(new CanonicalHero(heroName, canonicalNamePairs));
            }
            return heroes;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

