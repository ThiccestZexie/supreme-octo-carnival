package se.liu.danal315samak519;

public class Decrees
{
    private int type;
    private int level;
    private float increase;
    private String effect;
    private static final int DECREEAMMOUNT = 4;

    public Decrees(final int type) {
	//Spread them out in different levels so its 13 faster then 26, then 40 or something like that
	this.type = type;
	this.level = 1; //temp
	switch(type){
	    case 0: // 50% faster
		effect = "Increases Movespeed";
		this.increase = 1.5f;
		break;
	    case 1: // one extra heart
		effect = "Increases Health Points";
		this.increase = 2f;
		break;
	    case 2:// Larger Arrows
		effect = "Increases arrow width";
		this.increase = 2f;
		break;
	    case 3: // Faster arrows
		effect = "Faster arrow velocity";
		this.increase = 2f;
		break;
	    case 4:
		effect = "Full Heal!";
		break;
	}
    }

    public static int getDecreeAmount() {
	return DECREEAMMOUNT;
    }

    public float getIncrease() {
	return increase;
    }

    public int getType() {
	return type;
    }

    public String getEffect() {
	return effect;
    }
}
