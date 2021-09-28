package com.github.NC256.overwatchstats.gamedata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private static final Logger logger = LogManager.getLogger(Player.class);
    private final CanonicalGameData gameData;
    private final String name;
    private PlayerTeam team;

    private final List<PlayerHero> playedHeroes = new ArrayList<>();


    public Player(String name, PlayerTeam team, CanonicalGameData gameData) {
        this.name = name;
        this.team = team;
        this.gameData = gameData;
    }

    public PlayerTeam getTeam() {
        return team;
    }

    public void setTeam (PlayerTeam team){
        this.team = team;
    }

    public String getName() {
        return name;
    }

    private void addHero(CanonicalHero hero){
        playedHeroes.add(new PlayerHero(hero));
    }

    public List<PlayerHero> getPlayedHeroes() {
        return playedHeroes;
    }

    public PlayerHero getHeroByName(String name){
        for (PlayerHero h : playedHeroes) {
            if (h.getHero().getName().equals(name)){
                return h;
            }
        }
        // Hero not found, time to add to the player's ongoing list
        CanonicalHero newHero = gameData.getCanonicalHeroByName(name);
        if (newHero == null){
            logger.error("Nonexistent hero name " + name + " tried to be added.");
            return null;
        }
        else{
            PlayerHero current = new PlayerHero(newHero);
            playedHeroes.add(current);
            return current;
        }
    }

    public double getTotalDamageDone() {
        return playedHeroes.stream().mapToDouble(PlayerHero::getDamageDone).sum();

    }

    public double getTotalHealingDone() {
        return playedHeroes.stream().mapToDouble(PlayerHero::getHealingDone).sum();
    }

    public int getTotalKills(){
        return playedHeroes.stream().mapToInt(PlayerHero::getElims).sum();
    }

    public int getTotalDeaths(){
        return playedHeroes.stream().mapToInt(PlayerHero::getDeaths).sum();
    }

    public int getTotalFinalBlows(){
        return playedHeroes.stream().mapToInt(PlayerHero::getFinalBlows).sum();
    }
}
