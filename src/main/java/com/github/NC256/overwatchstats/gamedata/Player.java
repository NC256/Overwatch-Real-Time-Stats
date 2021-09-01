package com.github.NC256.overwatchstats.gamedata;

import com.github.NC256.overwatchstats.Hero;

import java.util.ArrayList;

public class Player {
    String name;
    Hero currentHero;
    int totalKills = 0;
    int totalDeaths = 0;

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public void incrementDeaths(){
        totalDeaths++;
    }

    public void incrementEliminations(){
        totalKills++;
    }



    // Stats per hero
    ArrayList<Hero> heroes = new ArrayList<>();

    public Hero getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(Hero currentHero) {
        this.currentHero = currentHero;
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public Hero getHero (String name){
        return heroes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }
}
