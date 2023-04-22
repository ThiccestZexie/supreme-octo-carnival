package se.liu.danal315samak519;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    protected BufferedImage[] currentSpriteFrames;
    private BufferedImage[] downFrames;
    private BufferedImage[] leftFrames;
    private BufferedImage[] upFrames;
    private BufferedImage[] rightFrames;
    private int ticksCounted;

    protected Character(final Point2D.Double coord) {
	super(coord);
	this.exp = 0;
	this.maxhp = 3;
	this.hp = maxhp;
    }

    @Override public void setDir(final Direction dir) {
	super.setDir(dir);
	this.currentSpriteFrames = switch (dir) {
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

    protected void storeDirectionalFrames() {
	try {
	    int spriteHeight = 16;
	    int spriteWidth = 16;

	    ImageLoader linkImageLoader = new ImageLoader("link.png");

	    downFrames = new BufferedImage[2];
	    downFrames[0] = linkImageLoader.getSubImage(0, 0, spriteWidth, spriteHeight);
	    downFrames[1] = linkImageLoader.getSubImage(0, 32, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[2];
	    leftFrames[0] = linkImageLoader.getSubImage(32, 0, spriteWidth, spriteHeight);
	    leftFrames[1] = linkImageLoader.getSubImage(32, 32, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[2];
	    upFrames[0] = linkImageLoader.getSubImage(32 * 2, 0, spriteWidth, spriteHeight);
	    upFrames[1] = linkImageLoader.getSubImage(32 * 2, 32, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[2];
	    rightFrames[0] = linkImageLoader.getSubImage(32 * 3, 32, spriteWidth, spriteHeight);
	    rightFrames[1] = linkImageLoader.getSubImage(32 * 3, 0, spriteWidth, spriteHeight);

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public BufferedImage getCurrentSprite() {
	return getSpriteFrameAt(currentSpriteFrameIndex);
    }

    private BufferedImage getSpriteFrameAt(int index) {
	return currentSpriteFrames[index];
    }

    public void setCurrentSpriteFrames(final BufferedImage[] frames) {
	this.currentSpriteFrames = frames;
    }
}
