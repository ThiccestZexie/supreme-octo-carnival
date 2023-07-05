package se.liu.danal315samak519;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

/**
 * Represent a powerup that can be acquired by the player.
 */
public class Decrees
{
    private static final int DECREE_AMOUNT = 5;
    RandomGenerator randomDecreeGenerator = new Random();
    private String type;
    private float increase;
    private String effect;
    private List<String> decreeEffects =
	    Arrays.asList("Movement Increase", "Health Increase", "Arrow Size Increase", "Faster Arrows", "Full Restore");

    public Decrees(final String type) {
	//Spread them out in different levels so its 13 faster then 26, then 40 or something like that
	this.type = type;
	switch (type) {
	    case "Movement Increase": // 50% faster
		this.effect = "Increases Movespeed";
		final float speedIncrease = 1.5f;
		this.increase = speedIncrease;
		break;
	    case "Health Increase": // one extra heart
		this.effect = "Increases Health Points";
		final float healthIncrease = 2.0f;
		this.increase = healthIncrease;
		break;
	    case "Arrow Size Increase":// Larger Arrows
		this.effect = "Increases arrow width";
		final float arrowWidthIncrease = 2.0f;
		this.increase = arrowWidthIncrease;
		break;
	    case "Faster Arows": // Faster arrows
		this.effect = "Faster arrow velocity";
		final float arrowVelocityIncrease = 2.0f;
		this.increase = arrowVelocityIncrease;
		break;
	    default:
		this.effect = "Full Heal!";
		break;
	}
    }


    public String getRandomDecree() {
	return this.decreeEffects.get(randomDecreeGenerator.nextInt(DECREE_AMOUNT));
    }

    public float getIncrease() {
	return increase;
    }

    public String getType() {
	return type;
    }

    public String getEffect() {
	return this.effect;
    }

    public List<String> getDecreeEffects() {
	return decreeEffects;
    }
}
