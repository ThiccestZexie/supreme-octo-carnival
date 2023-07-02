package se.liu.danal315samak519;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Represent a powerup that can be acquired by the player.
 */
public class Decrees
{
    private String type;
    private float increase;
    private String effect = null;
    private List<String> effectList = Arrays.asList("Movement Increase","Health Increase","Arrow Size Increase","Faster Arrows",
						    "Full Restore");

    private static final int DECREE_AMMOUNT = 4;

    public Decrees(final String type) {
	//Spread them out in different levels so its 13 faster then 26, then 40 or something like that
	this.type = type;
	switch(type){
	    case "Movement Increase": // 50% faster
		effect = "Increases Movespeed";
		this.increase = 1.5f;
		break;
	    case "Health Increase": // one extra heart
		effect = "Increases Health Points";
		this.increase = 2.0f;
		break;
	    case "Arrow Size Increase":// Larger Arrows
		effect = "Increases arrow width";
		this.increase = 2.0f;
		break;
	    case "Faster Arows": // Faster arrows
		effect = "Faster arrow velocity";
		this.increase = 2.0f;
		break;
	    case "Full Restore":
		effect = "Full Heal!";
		break;
	}
    }



    public String getRandomDecree(){
	Random rng = new Random();
	return this.effectList.get(rng.nextInt(DECREE_AMMOUNT));
    }

    public float getIncrease() {
	return increase;
    }

    public String getType() {
	return type;
    }

    public String getEffect() {
	return effect;
    }

    public List<String> getEffectList() {
	return effectList;
    }
}
