package com.github.NC256.overwatchstats.gamedata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class must never be accessed asynchronously
 */
public class GameMatch {

    private static final Logger logger = LogManager.getLogger(GameMatch.class);

    private CanonicalGameData gameData;
    private CanonicalMode gameMode;
    private String mapName;
    private final List<Player> players = new ArrayList<>(12);
    private final List<Player> overflowPlayers = new ArrayList<>();

    public GameMatch(CanonicalGameData gameData) {
        this.gameData = gameData;
    }



    public void parseInitialMessage(List<String> playerNames, String mapName, String gameMode) {
        this.mapName = mapName;
        this.gameMode = gameData.getCanonicalModeByName(gameMode);
        setPlayerNames(playerNames);
    }

    /**
     * Printing the INITIAL message ASAP leaves the player names section blank.
     * Printing it at the "Assembling Heroes" means other messages get printed first
     * which leads to some annoying logic code to identify the team of early-heroes
     *
     * @param playerNames
     */
    private void setPlayerNames(List<String> playerNames) {
        List<Player> generatedPlayers = new ArrayList<>();

        if (gameMode.isTeamMode()) { //Instantiate player objects with their team alignment
            for (int i = 0; i < 6; i++) {
                generatedPlayers.add(new Player(playerNames.get(i), PlayerTeam.TEAM_1, gameData));
            }
            for (int i = 6; i < 12; i++) {
                generatedPlayers.add(new Player(playerNames.get(i), PlayerTeam.TEAM_2, gameData));
            }
        } else {
            for (int i = 0; i < 12; i++) {
                generatedPlayers.add(new Player(playerNames.get(i), PlayerTeam.FFA, gameData));
            }
        }

        // If the game has less than 12 players, trim all "empty" Player objects
        // This is done second because the OW output orders the players 0-12 and they are printed that way
        // No other team-identification is done with the current workshop script than the implicit ordering
        generatedPlayers = generatedPlayers.stream().filter(x -> !x.getName().isEmpty()).collect(Collectors.toList());

        if (overflowPlayers.isEmpty()) { // If no overflow then great, add to the set and bail
            players.addAll(generatedPlayers);
            return;
        } else { // Now we have to update any players that got added early to preserve the data they've already collected

            for (int i = 0; i < overflowPlayers.size(); i++) { //for every player in overflow
                Player match = null;
                for (int j = 0; j < generatedPlayers.size(); j++) { //search for a match
                    if (generatedPlayers.get(j) != null && overflowPlayers.get(i).getName().equals(generatedPlayers.get(j).getName())) {
                        match = generatedPlayers.get(j); //found it
                        overflowPlayers.get(i).setTeam(match.getTeam()); //scrape team enum from the match
                        players.add(overflowPlayers.get(i)); // move to player list
                        overflowPlayers.set(i, null); // remove from overflow
                        generatedPlayers.set(j, null); // remove from incoming
                        break; // move to next overflowed player
                    }
                }
            }
            //Clean up
            overflowPlayers.removeAll(Collections.singleton(null));
            generatedPlayers.removeAll(Collections.singleton(null));

            //Add remaining
            players.addAll(generatedPlayers);
        }
    }

    /**
     * Unfortunately the INITIAL print message that lists all the players cannot be guaranteed to be logged first
     * Thus we must parse messages about players we haven't heard about yet.
     *
     * @param playerName
     * @return
     */
    public Player getPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        for (Player overflowPlayer : overflowPlayers) {
            if (overflowPlayer.getName().equals(playerName)) {
                logger.info("Returning player \"" + overflowPlayer.getName() + "\" from overflow.");
                return overflowPlayer;
            }
        }
        logger.warn("Player " + playerName + " added outside of INITIAL log message.");
        Player p = new Player(playerName, PlayerTeam.INVALID, gameData);
        overflowPlayers.add(p);
        return p;
    }

    public List<Player> getTeam1Players(){
        return players.stream().filter(x -> x.getTeam() == PlayerTeam.TEAM_1).collect(Collectors.toList());
    }

    public List<Player> getTeam2Players(){
        return players.stream().filter(x -> x.getTeam() == PlayerTeam.TEAM_2).collect(Collectors.toList());
    }

    /**
     * Does not return overflow players
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    public String getMapName() {
        return mapName;
    }

    public CanonicalMode getGameMode() {
        return gameMode;
    }
}
