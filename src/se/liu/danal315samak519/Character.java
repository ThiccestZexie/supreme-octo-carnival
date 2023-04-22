package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Character extends Entity
{
    // CONSTANTS FOR ENTITIES
    private static final int TICKS_PER_FRAME = 8;

    private final int[] expRequirements = new int[] { 2, 3, 5, 8, 12, 20, 23, 30, 999 }; //from level "0" to level "10"
    protected int exp;
    protected int level;
    protected int hp;
    protected int maxhp;
    protected int currentSpriteFrameIndex = 0;
    protected BufferedImage[] currentFrames;
    protected BufferedImage[] downFrames;
    protected BufferedImage[] leftFrames;
    protected BufferedImage[] upFrames;
    protected BufferedImage[] rightFrames;
    private int ticksCounted;

    protected Character(final Point2D.Double coord) {
	super(coord);
	this.exp = 0;
	this.maxhp = 3;
	this.hp = maxhp;
	this.size = new Dimension(50, 50); // TODO CHARACTERS HARDCODED SIZE
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

    @Override public void tick() {
	super.tick();
	performWalkCycle();
    }

    /**
     * Increments currentSpriteFrameIndex IFF the player is walking. Resets to zero if player stops.
     */
    private void performWalkCycle() {
	if (velX == 0 && velY == 0) {
	    currentSpriteFrameIndex = 0;
	    ticksCounted = 0;
	} else {
	    ticksCounted++;
	    if (ticksCounted > TICKS_PER_FRAME) {
		currentSpriteFrameIndex++;
		currentSpriteFrameIndex %= 2;
		ticksCounted = 0;
	    }
	}
    }


    public BufferedImage getCurrentSprite() {
	return getSpriteFrameAt(currentSpriteFrameIndex);
    }

    private BufferedImage getSpriteFrameAt(int index) {
	return currentFrames[index];
    }

    public void setCurrentFrames(final BufferedImage[] frames) {
	this.currentFrames = frames;
    }
}
