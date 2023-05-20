package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;

import java.awt.geom.Rectangle2D;

/**
 * Rectangles that block other movables path. (e.g. gates at room entrances)
 */
public class Obstacle extends Movable
{
    private final int id;
    private float openX, openY;
    private float closedX, closedY;
    private float speed = 8; // RANDOM MAGIC CONSTANT

    public Obstacle(float openX, float openY, float closedX, float closedY, float width, float height, int id) {
	this.openX = openX;
	this.openY = openY;
	this.closedX = closedX;
	this.closedY = closedY;
	this.id = id;
	setLocation(openX, openY);
	setSize(width, height);
	setHitBox();
    }

    public void open() {
	setVelocityTowards(openX, openY);
    }

    public void close() {
	setVelocityTowards(closedX, closedY);
    }

    public void stopMoving() {
	setVelocity(0, 0);
    }

    @Override public void nudgeAwayFrom(final Rectangle2D otherHitBox) {
	return; // Obstacles don't move away from other entities
    }

    /**
     * Make obstacle glide towards end position
     */
    public void setVelocityTowards(final float targetX, final float targetY) {
	if (getCoord().distance(targetX, targetY) == 0){
	    setVelocity(0, 0);
	    return;
	}
	Direction towardsTarget = Direction.getDirectionBetweenPoints(getX(), getY(), targetX, targetY);
	setVelocity(this.speed * towardsTarget.getX(), this.speed * towardsTarget.getY());
    }
}
