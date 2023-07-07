package se.liu.danal315samak519;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.random.RandomGenerator;

/**
 * Represent a powerup that can be acquired by the player.
 */
public class Decrees
{
    private final RandomGenerator randomGenerator = new Random();
    private DecreeEnums type;
    private float increase;
    private String effect = null;

    private Map<DecreeEnums, Float> decreeEffects = new HashMap<>();

    public Decrees(final DecreeEnums type) {
	this.type = type;
	this.setDecreeEffects();
	this.effect = String.valueOf((type));
	this.increase =decreeEffects.get(type);
    }
    private void setDecreeEffects(){
	final float valueOfDecree = 2.0f;
	decreeEffects.put(DecreeEnums.FULL_HEAL, 1.0f);
	decreeEffects.put(DecreeEnums.MOVEMENT_INCREASE, valueOfDecree);
	decreeEffects.put(DecreeEnums.ARROW_SIZE_INCREASE, valueOfDecree);
	decreeEffects.put(DecreeEnums.FASTER_ARROWS, valueOfDecree);
	decreeEffects.put(DecreeEnums.HEALTH_INCREASE, valueOfDecree);


    }
    public DecreeEnums getRandomDecree() {
	DecreeEnums[] decrees = DecreeEnums.values();
	return decrees[this.randomGenerator.nextInt(decrees.length)];
    }

    public float getIncrease() {
	return increase;
    }

    public DecreeEnums getType() {
	return type;
    }

    public String getEffect() {
	return this.effect;
    }

    public Map<DecreeEnums, Float> getDecreeEffects() {
	return decreeEffects;
    }
}
