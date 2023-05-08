package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;

public class Obstacle extends Movable
{
    private final int id;
    private double endX, endY;

    public Obstacle(double x, double y, double endX, double endY, double width, double height, int id) {
	setLocation(x, y);
	setSize(width, height);
	setHitBox();
	this.endX = endX;
	this.endY = endY;
	this.id = id;
    }

    @Override public void tick() {
	super.tick();
	nudgeTowardsEnd();
    }

    /**
     * Make obstacle glide towards end position
     */
    public void nudgeTowardsEnd(){
	if(getX() == endX && getY() == endY){
	    return; // Already at end location
	}
	Direction towardsEnd = Direction.getDirectionBetweenPoints(getX(), getY(), endX, endY);
	double speed = 8;
	this.nudge(speed*towardsEnd.getX(), speed*towardsEnd.getY());
    }
}
