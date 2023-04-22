package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Character extends Entity
{
    // CONSTANTS FOR ENTITIES
    private static final int INVINCIBILITY_FRAMES = 60; // 1 second @ 60fps

    private final int[] expRequirements = new int[] { 2, 3, 5, 8, 12, 20, 23, 30, 999 }; //from level "0" to level "10"
    protected int exp;
    protected int level;
    protected int hp;
    protected int maxhp;
    private int currentInvFrames;

    protected Character(final Point2D.Double coord) {
	super(coord);
	this.exp = 0;
	this.maxhp = 3;
	this.hp = maxhp;
    }

    public int getHp() {
	return hp;
    }

    public int getMaxHp() {
	return maxhp;
    }

    public int[] getExpRequirements() {
	return expRequirements;
    }

    public int getLevel() {
	return level;
    }

    public int getExp() {
	return exp;
    }

    public boolean isHit(Weapon e)
    {
	Character owner = e.getOwner();
	if (this.hitBox.getBounds().intersects(e.getHitBox())) {
	    if (hp > 1) {
		hp--;
		this.currentInvFrames = INVINCIBILITY_FRAMES;
	    } else {
		owner.incExp();
		owner.levelUp();
		this.isGarbage = true;
	    }
	    return true;

	}
	return false;
    }

    public Weapon getSword() {
	return new Weapon(this.coord, this);
    }

    public void incExp() { //Exp should depend on enemey level
	exp++;
    }

    public void levelUp() {
	while (checkExpReq()) {
	    exp -= expRequirements[level - 1];
	    level++;
	}
    }

    public boolean checkExpReq() { // You start as level 1 so index 0 of exp req and the exp is exp needed for next level...
	if (this.exp >= expRequirements[level - 1]) {
	    return true;
	}
	return false;
    }
}
