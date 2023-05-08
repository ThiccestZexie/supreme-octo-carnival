package se.liu.danal315samak519;

public class Decrees
{
    private int type;
    private int level;
    private double increase;
    private String effect;

    public Decrees(final int type) {
	this.type = type;
	this.level = 1; //temp
	switch(type){
	    case 0:
		effect = "Increases attack speed";
		this.increase = 1.5;
		break;
	}
    }

    public double getIncrease() {
	return increase;
    }
}
