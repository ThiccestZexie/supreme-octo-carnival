package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;

import java.awt.geom.Rectangle2D;

/**
 * Rectangles that block other movables path. (e.g. gates at room entrances) Can be opened and closed.
 */
public class Obstacle extends Movable
{
    private float openX, openY;
    private float closedX, closedY;
    private boolean roomComplete;

    public Obstacle(float openX, float openY, float closedX, float closedY, float width, float height) {
	this.openX = openX;
	this.openY = openY;
	this.closedX = closedX;
	this.closedY = closedY;
	setLocation(openX, openY);
	setSize(width, height);
	setHitBox();
    }

    /**
     * Used to point roomComplete to a new object.
     *
     * @param roomComplete
     */
    public void setRoomComplete(boolean roomComplete) {
	this.roomComplete = roomComplete;
    }

    public void open() {
	setVelocityTowards(openX, openY);
    }

    public void close() {
	setVelocityTowards(closedX, closedY);
    }

    @Override public void tick() {
	super.tick();
	if (roomComplete) {
	    open();
	} else {
	    close();
	}
    }


    /**
     * Obstacles cannot be nudged away from other hitboxes.
     *
     * @param otherHitBox
     */
    @Override public void nudgeAwayFrom(final Rectangle2D otherHitBox) {
    }

    @Override public void interactWith(final Movable movable) {
	Person person = (Person) movable;
	person.nudgeAwayFrom(this.getHitBox());
    }

    /**
     * Make obstacle glide towards end position
     */
    public void setVelocityTowards(final float targetX, final float targetY) {
	if (getCoord().distance(targetX, targetY) == 0) {
	    setVelocity(0, 0);
	    return;
	}
	Direction towardsTarget = Direction.getDirectionBetweenPoints(getX(), getY(), targetX, targetY);
	final float speed = 8;
	setVelocity(speed * towardsTarget.getX(), speed * towardsTarget.getY());
    }
}
