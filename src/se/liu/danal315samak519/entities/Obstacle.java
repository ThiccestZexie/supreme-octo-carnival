package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;

/**
 * Rectangles that block other movables path. (e.g. gates at room entrances)
 */
public class Obstacle extends Movable
{
    private final int id;
    private double openX, openY;
    private double closedX, closedY;
    private double speed = 8; // RANDOM MAGIC CONSTANT

    public Obstacle(double openX, double openY, double closedX, double closedY, double width, double height, int id) {
	setLocation(openX, openY);
	setSize(width, height);
	setHitBox();
	this.openX = openX;
	this.openY = openY;
	this.closedX = closedX;
	this.closedY = closedY;
	this.id = id;
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

    /**
     * Make obstacle glide towards end position
     */
    public void setVelocityTowards(final double targetX, final double targetY) {
	if (getX() == targetX && getY() == targetY) {
	    setVelocity(0, 0); // At end!
	    return;
	}
	Direction towardsTarget = Direction.getDirectionBetweenPoints(getX(), getY(), targetX, targetY);
	setVelocity(this.speed * towardsTarget.getX(), this.speed * towardsTarget.getY());
    }
}
