package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;

import java.awt.geom.Rectangle2D;

/**
 *
 */
public class Movable extends Entity
{
    protected Direction dir;
    protected double maxSpeed;
    protected double velX;
    protected double velY;

    public Direction getDir() {
	return dir;
    }

    public void setDir(final Direction dir) {
	this.dir = dir;
    }

    public void nudgeAwayFrom(Rectangle2D otherHitBox) {
	if (!this.getHitBox().intersects(otherHitBox)) {
	    return; // not intersecting!
	}

	Rectangle2D intersection = this.getHitBox().createIntersection(otherHitBox);
	boolean isHorizontalCollision = intersection.getWidth() < intersection.getHeight();
	if (isHorizontalCollision) { // Left-right collision
	    if (intersection.getCenterX() < this.getHitBox().getCenterX()) { // Left collision
		nudge(intersection.getWidth(), 0);
	    } else { // Right collision
		nudge(-intersection.getWidth(), 0);
	    }
	} else { // Up-down collision
	    if (intersection.getCenterY() < this.getHitBox().getCenterY()) { // Up collision
		nudge(0, intersection.getHeight());
	    } else { // Down collision
		nudge(0, -intersection.getHeight());
	    }
	}
    }

    public double getMaxSpeed() {
	return maxSpeed;
    }

    public void setMaxSpeed(final int speed) {
	this.maxSpeed = speed;
    }

    public void nudge(final double dx, final double dy) {
	setLocation(getX() + dx, getY() + dy);
	nudgeHitBox(dx, dy);
    }

    public void setVelocity(final double vx, final double vy) {
	setVelX(vx);
	setVelY(vy);
    }

    private void setAppropiateDir() {
	Direction newDirection = Direction.velocityToDirection(getVelX(), getVelY());
	if (newDirection != null) {
	    setDir(newDirection);
	}
    }

    public void tick() {
	nudge(velX, velY);
    }

    private void nudgeHitBox(final double dx, final double dy) {
	setLocationOfHitBox(hitBox.getX() + dx, hitBox.getY() + dy);
    }

    public double getVelX() {
	return velX;
    }

    public void setVelX(final double vx) {
	this.velX = vx;
	setAppropiateDir();
    }

    public double getVelY() {
	return velY;
    }

    public void setVelY(final double vy) {
	this.velY = vy;
	setAppropiateDir();
    }

    public boolean getIsGarbage() {
	return isGarbage;
    }
}
