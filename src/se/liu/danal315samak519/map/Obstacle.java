package se.liu.danal315samak519.map;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.Movable;

/**
 * Used to block entities on arbitrary areas (not on tiles) Can be moved (used as animating gate)
 */
public class Obstacle extends Movable
{
    private double endX, endY;

    protected Obstacle(double startX, double startY, double width, double height, double endX, double endY) {
	setLocation(startX, startY);
	setSize(width, height);
	setHitBox();
	this.endX = endX;
	this.endY = endY;
    }

    public double getEndX() {
	return endX;
    }

    public double getEndY() {
	return endY;
    }

    /**
     * Moves toward end coordinates. Returns true if possible, false if current == end.
     *
     * @return whether moved successfully
     */
    private boolean tryMoveTowardsEnd() {
	if (getX() == getEndX() && getY() == getEndY()) {
	    return false;
	}
	Direction direction = Direction.getDirectionBetweenPoints(getX(), getY(), getEndX(), getEndY());
	setVelX(direction.getX());
	setVelY(direction.getY());
	return true;
    }
}
