package se.liu.danal315samak519;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Character extends Movable
{
    // CONSTANTS FOR CHARACTERS
    private static final int TICKS_PER_FRAME = 8;
    private static final int[] EXP_REQUIREMENTS = new int[] { 2, 3, 5, 8, 12, 20, 23, 30, 999 }; //from level "0" to level "10"
    private static final int MAXHP = 3;

    private static final int iFrames = 90; //about 1.5 seconds of iFrames

    protected int exp = 0;

    protected int currentIFramees = 0;
    protected int level;
    protected int hp = MAXHP;
    protected int currentFrameIndex = 0;
    // Sprite
    protected BufferedImage[] currentFrames;
    protected BufferedImage[] downFrames;
    protected BufferedImage[] leftFrames;
    protected BufferedImage[] upFrames;
    protected BufferedImage[] rightFrames;
    protected BufferedImage attackFrame;
    private Status status = Status.NORMAL;
    private int ticksCounted;


    protected Character(final Point2D.Double coord) {
	setLocation(coord);
	setSize(50, 50);
	setMaxSpeed(5);
	setHitBox();
    }

    @Override public void setDir(final Direction dir) {
	super.setDir(dir);
	this.currentFrames = switch (dir) {
	    case UP -> upFrames;
	    case DOWN -> downFrames;
	    case LEFT -> leftFrames;
	    case RIGHT -> rightFrames;
	};
    }

    public int getHp() {
	return hp;
    }

    public int getMaxHp() {
	return MAXHP;
    }

    public int[] getExpRequirements() {
	return EXP_REQUIREMENTS;
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
	    if (hp > 1 && this.getStatus() != Status.HIT) {
		hp--;
		this.setStatus(Status.HIT);
	    } else if (this.getStatus() != Status.HIT) {
		owner.incExp();
		owner.levelUp();
		this.isGarbage = true;
	    }
	    return true;

	}
	return false;
    }

    public Arrow shootProjectile(){
	return new Arrow(this.coord, this);
    }

    public Weapon getSword() {
	return new Weapon(this.coord, this);
    }

    public void incExp() { //Exp should depend on enemey level
	exp++;
    }

    public void levelUp() {
	while (checkExpReq()) {
	    exp -= EXP_REQUIREMENTS[level - 1];
	    level++;
	}
    }

    public boolean checkExpReq() { // You start as level 1 so index 0 of exp req and the exp is exp needed for next level...
	if (this.exp >= EXP_REQUIREMENTS[level - 1]) {
	    return true;
	}
	return false;
    }

    @Override public void tick() {
	if (getStatus() == Status.SLEEPING) {
	    return; // Do nothing just sleep zZzZ
	}
	if (getStatus() == Status.HIT){
	    currentIFramees++;
	}
	if (currentIFramees == iFrames){
	    setStatus(Status.NORMAL);
	    currentIFramees = 0;
	}
	super.tick();
	performWalkCycle();
    }

    /**
     * Increments currentSpriteFrameIndex IFF the player is walking. Resets to zero if player stops.
     */
    private void performWalkCycle() {
	if (velX == 0 && velY == 0) {
	    currentFrameIndex = 0;
	    ticksCounted = 0;
	} else {
	    ticksCounted++;
	    if (ticksCounted > TICKS_PER_FRAME) {
		currentFrameIndex++;
		currentFrameIndex %= 2;
		ticksCounted = 0;
	    }
	}
    }

    public BufferedImage getCurrentSprite() {
	return getSpriteFrameAt(currentFrameIndex);
    }

    private BufferedImage getSpriteFrameAt(int index) {
	return currentFrames[index];
    }

    public void setCurrentFrames(final BufferedImage[] frames) {
	this.currentFrames = frames;
    }

    public void becomeAttacking() {
	this.currentFrameIndex = 2;
	this.setStatus(Status.ATTACKING);
    }

    protected Status getStatus() {
	return this.status;
    }

    protected void setStatus(final Status status) {
	this.status = status;
    }
}
