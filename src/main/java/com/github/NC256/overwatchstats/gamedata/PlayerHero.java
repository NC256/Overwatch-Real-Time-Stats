package com.github.NC256.overwatchstats.gamedata;


public class PlayerHero {
    CanonicalHero hero;
    private int timePlayed = 0, objectiveTime = 0; //in seconds
    private int elims = 0, finalBlows = 0, deaths = 0;
    private double damageDone = 0.0, healingDone = 0.0, damageReceived = 0.0, healingReceived = 0.0;
    private int ability1Uses = 0, ability2Uses = 0, ultimateUses = 0, crouchUses = 0;
    // barrier damage, solo kills, objective kills, environmental kills, weapon accuracy?

    public PlayerHero(CanonicalHero hero){
        this.hero = hero;
    }

    public CanonicalHero getHero() {
        return hero;
    }

    public void setName(CanonicalHero hero) {
        this.hero = hero;
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(int timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getObjectiveTime() {
        return objectiveTime;
    }

    public void setObjectiveTime(int objectiveTime) {
        this.objectiveTime = objectiveTime;
    }

    public int getElims() {
        return elims;
    }

    public void setElims(int elims) {
        this.elims = elims;
    }

    public int getFinalBlows() {
        return finalBlows;
    }

    public void setFinalBlows(int finalBlows) {
        this.finalBlows = finalBlows;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public double getDamageDone() {
        return damageDone;
    }

    public void setDamageDone(double damageDone) {
        this.damageDone = damageDone;
    }

    public double getHealingDone() {
        return healingDone;
    }

    public void setHealingDone(double healingDone) {
        this.healingDone = healingDone;
    }

    public double getDamageReceived() {
        return damageReceived;
    }

    public void setDamageReceived(double damageReceived) {
        this.damageReceived = damageReceived;
    }

    public double getHealingReceived() {
        return healingReceived;
    }

    public void setHealingReceived(double healingReceived) {
        this.healingReceived = healingReceived;
    }

    public int getAbility1Uses() {
        return ability1Uses;
    }

    public void setAbility1Uses(int ability1Uses) {
        this.ability1Uses = ability1Uses;
    }

    public int getAbility2Uses() {
        return ability2Uses;
    }

    public void setAbility2Uses(int ability2Uses) {
        this.ability2Uses = ability2Uses;
    }

    public int getUltimateUses() {
        return ultimateUses;
    }

    public void setUltimateUses(int ultimateUses) {
        this.ultimateUses = ultimateUses;
    }

    public int getCrouchUses() {
        return crouchUses;
    }

    public void setCrouchUses(int crouchUses) {
        this.crouchUses = crouchUses;
    }

    public void incrementAbility1Uses(){
        ability1Uses++;
    }

    public void incrementAbility2Uses(){
        ability2Uses++;
    }

    public void incrementUltimateUses(){
        ultimateUses++;
    }

    public void incrementCrouchUses(){
        crouchUses++;
    }

    public void incrementAbilityUse(OverwatchAbilityEvent event) {
        switch (event){
            case PRIMARY_FIRE:
                break;
            case SECONDARY_FIRE:
                break;
            case ABILITY_1:
                this.incrementAbility1Uses();
                break;
            case ABILITY_2:
                this.incrementAbility2Uses();
                break;
            case ULTIMATE:
                this.incrementUltimateUses();
                break;
            case CROUCH:
                this.incrementCrouchUses();
                break;
        }
    }

    public void incrementDamageDone(double value){
        damageDone+=value;
    }

    public void incrementDamageReceived(double value){
        damageReceived+=value;
    }

    public void incrementHealingDone(double value){
        healingDone+=value;
    }

    public void incrementHealingReceived(double value){
        healingReceived+=value;
    }

    public void incrementElimnations() {
        elims++;
    }

    public void incrementFinalBlows() {
        finalBlows++;
    }

    public void incrementDeaths() {
        deaths++;
    }
}
