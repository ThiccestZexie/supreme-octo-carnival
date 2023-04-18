package se.liu.danal315samak519;

import java.awt.*;

public class Entity
{
    // CONSTANTS FOR ENTITIES
    private static final int INVINCIBILITY_FRAMES = 60; // 1 second @ 60fps
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

    // TODO REMOVE HARDCODED 3
    protected int hp;
    protected int maxhp ;
    private int currentInvFrames;


    public Entity(final Point coord, final Color color) {
	this.size = new Dimension(50, 50); // HARDCODED SIZE OF 50,50 px TODO
	this.coord = coord;
	this.color = color;
	this.hitBox = new Rectangle(size);
	this.hitBox.setLocation(coord);
	dir = Direction.DOWN;
	isGarbage = false;
    }

    public Direction getDir() {
	return dir;
    }
    public int getHp(){
		return hp;
    }
    public int getMaxHp(){
	 return maxhp;
    }


    public void setDir(final Direction dir) {
	this.dir = dir;
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
	    if (hp > 0) {
		hp--;
		this.currentInvFrames = INVINCIBILITY_FRAMES;
	    } else {
		owner.incExp();
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

    public void moveTo(final int x, final int y)
    {
	coord.move(x, y);
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
	while (canLevelUp()) {
	    exp = 0;
	    level++;
	}
    }

    public boolean canLevelUp() { // need to figure out exp requirements
	if (this.exp >= 211) {
	    return true;
	}
	return false;
    }
}
