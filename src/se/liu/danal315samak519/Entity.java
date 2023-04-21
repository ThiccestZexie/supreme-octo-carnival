package se.liu.danal315samak519;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity
{
    // CONSTANTS FOR ENTITIES
    private static final int INVINCIBILITY_FRAMES = 60; // 1 second @ 60fps

    private final int[] expRequirements = new int[] { 2, 3, 5, 8, 12, 20, 23, 30, 999 }; //from level "0" to level "10"
    //...
    protected Dimension size;
    protected Point coord;
    protected Direction dir;
    protected int maxSpeed;
    protected int velX, velY;
    protected Color color;
    protected Rectangle hitBox;
    protected boolean isGarbage = false;
    protected int exp;
    protected int level;

    protected int hp;
    protected int maxhp;
    private int currentInvFrames;

    private BufferedImage[] currentSpriteFrames;

    // Keeps track of what frame to display on the sprite
    protected int currentSpriteFrameIndex = 0;


    public Entity(final Point coord, final Color color) {
	this.maxhp = 3;
	this.hp = maxhp;
	this.size = new Dimension(50, 50); // HARDCODED SIZE OF 50,50 px TODO
	this.coord = coord;
	this.color = color;
	this.exp = 0;
	this.hitBox = new Rectangle(size);
	this.hitBox.setLocation(coord);
	isGarbage = false;
    }

    public Direction getDir() {
	return dir;
    }

    public void setDir(final Direction dir) {
	this.dir = dir;
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

    public BufferedImage getCurrentSprite() {
	return getSpriteFrameAt(currentSpriteFrameIndex);
    }

    private BufferedImage getSpriteFrameAt(int index){
	return currentSpriteFrames[index];
    }

    public void setCurrentSpriteFrames(final BufferedImage[] frames) {
	this.currentSpriteFrames = frames;
    }

    public Rectangle getHitBox() {
	return this.hitBox;
    }

    public Point getCoord() {
	return coord;
    }

    public int getExp() {
	return exp;
    }

    public boolean isHit(WeaponEntity e)
    {
	Entity owner = e.getOwner();
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


    public int getX() {
	return getCoord().x;
    }

    public int getY() {
	return getCoord().y;
    }

    public Color getColor() {
	return color;
    }

    public Dimension getSize() {
	return size;
    }

    public int getWidth() {
	return (int) size.getWidth();
    }

    public int getHeight() {
	return (int) size.getHeight();
    }

    public int getMaxSpeed() {
	return maxSpeed;
    }

    public void setMaxSpeed(final int speed) {
	this.maxSpeed = speed;
    }


    public void nudge(final int dx, final int dy) {
	coord.translate(dx, dy);
    }

    public void setCurrentVelocity(final int vx, final int vy) {
	this.velX = vx;
	this.velY = vy;
    }

    public void tick() {
	// Move along according to current velocity
	nudgeHitBox(velX, velY);
	nudge(velX, velY);
    }

    public void nudgeHitBox(final int dx, final int dy) {
	hitBox.translate(dx, dy);
    }

    public int getVelX() {
	return velX;
    }

    public int getVelY() {
	return velY;
    }

    public int getLifeSpan() {
	// Returns -1 becuase they are immortal!!! as in they dont die of age...
	return -1;
    }

    public boolean getIsGarbage() {
	return isGarbage;
    }

    public WeaponEntity getSword() {
	return new WeaponEntity(this.coord, Color.BLACK, this);
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
