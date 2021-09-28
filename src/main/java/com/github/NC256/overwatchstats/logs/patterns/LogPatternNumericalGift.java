package com.github.NC256.overwatchstats.logs.patterns;

import com.github.NC256.overwatchstats.gamedata.GameMatch;
import com.github.NC256.overwatchstats.gamedata.OverwatchAbilityEvent;
import com.github.NC256.overwatchstats.logs.LogPatternType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DAMAGE_DEALT, HEALING_DEALT
 * GIFTER,GIFTER_HERO,GIFTER_ABILITY,AMT_GIFTED,RECEIVER,RECEIVER_HERO
 */
public class LogPatternNumericalGift extends LogPattern {

    private final Logger logger = LogManager.getLogger(this);
    private String gifter;
    private String gifterHero;
    private OverwatchAbilityEvent ability;
    private float amountGifted;
    private String receiver;
    private String receiverHero;

    public LogPatternNumericalGift(int millisecondsSinceStart, LogPatternType type, String line) {
        super(millisecondsSinceStart, type);
        String[] tokens = line.split(",");
        gifter = tokens[0];
        gifterHero = tokens[1];
        ability = OverwatchAbilityEvent.stringToEnum(tokens[2]);
        amountGifted = Float.parseFloat(tokens[3]);
        receiver = tokens[4];
        receiverHero = tokens[5];
    }

    @Override
    public void updateStats(GameMatch match) {
        if (super.getMessageType() == LogPatternType.DAMAGE_DEALT){
            match.getPlayerByName(gifter).getHeroByName(gifterHero).incrementDamageDone(amountGifted);
            match.getPlayerByName(receiver).getHeroByName(receiverHero).incrementDamageReceived(amountGifted);
        }
        else if (super.getMessageType() == LogPatternType.HEAL_DEALT){
            match.getPlayerByName(gifter).getHeroByName(gifterHero).incrementHealingDone(amountGifted);
            match.getPlayerByName(receiver).getHeroByName(receiverHero).incrementHealingReceived(amountGifted);
        }
        else{
            logger.warn("Expected Damage or Healing, instantiated with neither.");
        }
    }
}
