package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Entities that have velocity, and move according to velocity when ticking.
 */
public abstract class Movable extends Entity
{
    protected Direction direction = Direction.DOWN;
    protected float maxSpeed;
    protected float velX;
    protected float velY;

    public Direction getDirection() {
	return direction;
    }

    public BufferedImage getCurrentSprite() {
	return null;
    }

    public void setDirection(final Direction direction) {
	this.direction = direction;
    }

    public void nudgeAwayFrom(Rectangle2D otherHitBox) {
	if (!this.getHitBox().intersects(otherHitBox)) {
	    return; // No collision
	}

	Rectangle2D.Float intersection = (Rectangle2D.Float) this.getHitBox().createIntersection(otherHitBox);
	boolean isHorizontalCollision = intersection.getWidth() < intersection.getHeight();
	if (isHorizontalCollision) { // Left-right collision
	    if (intersection.getCenterX() < this.getHitBox().getCenterX()) { // Left collision
		nudge(intersection.width, 0);
	    } else { // Right collision
		nudge(-intersection.width, 0);
	    }
	} else { // Up-down collision
	    if (intersection.getCenterY() < this.getHitBox().getCenterY()) { // Up collision
		nudge(0, intersection.height);
	    } else { // Down collision
		nudge(0, -intersection.height);
	    }
	}
    }

    public float getMaxSpeed() {
	return maxSpeed;
    }

    public void setMaxSpeed(final float speed) {
	this.maxSpeed = speed;
    }

    public void nudge(final float dx, final float dy) {
	setLocation(getX() + dx, getY() + dy);
	nudgeHitBox(dx, dy);
    }

    public void setVelocity(final float vx, final float vy) {
	setVelX(vx);
	setVelY(vy);
    }

    private void setAppropiateDir() {
	Direction newDirection = Direction.velocityToDirection(getVelX(), getVelY());
	if (newDirection != null) {
	    setDirection(newDirection);
	}
    }

    public void tick() {
	nudge(velX, velY);
    }

    private void nudgeHitBox(final float dx, final float dy) {
	setLocationOfHitBox(hitBox.x + dx, hitBox.y + dy);
    }

    public float getVelX() {
	return velX;
    }

    public void setVelX(final float vx) {
	this.velX = vx;
	setAppropiateDir();
    }

    public float getVelY() {
	return velY;
    }

    public void setVelY(final float vy) {
	this.velY = vy;
	setAppropiateDir();
    }

}
