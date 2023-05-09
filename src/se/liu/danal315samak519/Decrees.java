package se.liu.danal315samak519;

public class Decrees
{
    private int type;
    private int level;
    private double increase;
    private String effect;

    public Decrees(final int type) {
	//Spread them out in different levels so its 13 faster then 26, then 40 or something like that
	this.type = type;
	this.level = 1; //temp
	switch(type){
	    case 0: // 50% faster
		effect = "Increases attack speed";
		this.increase = 1.5;
		break;
	    case 1: // one extra heart
		effect = "Increases Health Points";
		this.increase = 2;
		break;
	    case 2:// Larger Arrows
		effect = "Increases arrow width";
		this.increase = 2;
		break;
	    case 3: // Faster arrows
		effect = "Faster arrow velocity";
		this.increase = 2;
		break;
	}
    }

    public double getIncrease() {
	return increase;
    }

    public int getType() {
	return type;
    }
}
