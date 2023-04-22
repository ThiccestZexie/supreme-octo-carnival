package se.liu.danal315samak519;

import java.awt.*;

public class Weapon extends Entity
{
    private final int[] expRequirements = new int[] { 2, 3, 5, 8, 12, 20, 23, 30, 999 }; //from level "0" to level "10"
    protected int exp;
    protected int level;
    protected int hp;
    protected int maxhp;
    private Character owner;
    private int lifeSpan;//get a life
    private int currentInvFrames;

    public Weapon(final Point coord, final Color color, final Character owner) {
	//We have two choices when it comes to spawning the weapon first look at dir then spawn everything according to that...
	super(coord, color);
	this.owner = owner;
	this.coord = coord;
	//this.hp = 9999; //temp bug fix to make it so swords dont kill themselves for exp...
	switch (owner.getDir()) {
	    case UP:
		this.size = new Dimension(10, 60);
		this.coord = new Point((owner.getX() + (owner.getWidth() / 2) - this.getWidth() / 2), (owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		this.size = new Dimension(10, 60);
		this.coord = new Point((owner.getX() + (owner.getWidth() / 2) - this.getWidth() / 2), (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		size = new Dimension(60, 10);
		this.coord = new Point((owner.getX() - this.getWidth()), (owner.getY() + (owner.getHeight() / 2)) - (this.getHeight() / 2));
		break;
	    case RIGHT:
		size = new Dimension(60, 10);
		this.coord =
			new Point((owner.getX() + owner.getWidth()), (owner.getY() + (owner.getHeight() / 2)) - (this.getHeight() / 2));
		break;

	}
	this.hitBox = new Rectangle(this.coord, size);
	lifeSpan = 5;
    }

    public Character getOwner() {
	return owner;
    }

    @Override public void tick() {
	super.tick();
	lifeSpan--;
	followOwner();
	if (lifeSpan <= 0) {
	    isGarbage = true;
	}
    }

    public void followOwner() {
	this.velX = owner.getVelX();
	this.velY = owner.getVelY();
    }

    @Override public int getLifeSpan() {
	return lifeSpan;
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

//    public boolean isHit(Weapon e)
//    {
//	Character owner = e.getOwner();
//	if (this.hitBox.getBounds().intersects(e.getHitBox())) {
//	    if (hp > 1) {
//		hp--;
//		this.currentInvFrames = INVINCIBILITY_FRAMES;
//	    } else {
//		owner.incExp();
//		owner.levelUp();
//		this.isGarbage = true;
//	    }
//	    return true;
//
//	}
//	return false;
//    }

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
