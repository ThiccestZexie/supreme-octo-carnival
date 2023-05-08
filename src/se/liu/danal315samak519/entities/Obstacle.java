package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;

public class Obstacle extends Movable
{
    private final int id;
    private double endX, endY;
    private double speed = 8; // RANDOM MAGIC CONSTANT

    public Obstacle(double x, double y, double endX, double endY, double width, double height, int id) {
	setLocation(x, y);
	setSize(width, height);
	setHitBox();
	this.endX = endX;
	this.endY = endY;
	this.id = id;
    }

    public void activate() {
	setVelocityTowardsEnd();
    }

    public void deactivate() {
	setVelocity(0, 0);
    }

    /**
     * Make obstacle glide towards end position
     */
    public void setVelocityTowardsEnd() {
	if (getX() == endX && getY() == endY) {
	    setVelocity(0,0); // At end!
	    return;
	}
	Direction towardsEnd = Direction.getDirectionBetweenPoints(getX(), getY(), endX, endY);
	setVelocity(speed * towardsEnd.getX(), speed * towardsEnd.getY());
    }
}
