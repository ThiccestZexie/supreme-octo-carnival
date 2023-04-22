package se.liu.danal315samak519;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity
{
    //...
    protected Dimension size;
    protected Point coord;
    protected Direction dir;
    protected int maxSpeed;
    protected int velX;
    protected int velY;
    protected Color color;
    protected Rectangle hitBox;
    protected boolean isGarbage = false;
    // Keeps track of what frame to display on the sprite
    protected int currentSpriteFrameIndex = 0;
    private BufferedImage[] currentSpriteFrames;

    public Entity(final Point coord, final Color color) {
	this.size = new Dimension(50, 50); // HARDCODED SIZE OF 50,50 px TODO
	this.coord = coord;
	this.color = color;
	isGarbage = false;
	this.hitBox = new Rectangle(size);
    }

    public Direction getDir() {
	return dir;
    }

    public void setDir(final Direction dir) {
	this.dir = dir;
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

    public Rectangle getHitBox() {
	return this.hitBox;
    }

    public Point getCoord() {
	return coord;
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

}
