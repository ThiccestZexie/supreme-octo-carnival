package se.liu.danal315samak519;

import java.awt.geom.Point2D;

public class DirectionUtils
{
    public static Direction directionBetweenPoints(Point2D from, Point2D to){
	if (from.distance(to) == 0) {
	    return null; // Can't calculate Direction
	}
	Point2D subtracted = new Point2D.Double(from.getX() - to.getX(), from.getY()-to.getY());
	float angle = (float) Math.atan2(subtracted.getY(), subtracted.getX());
	Direction direction = null;

	if (-Math.PI / 4 < angle && angle < Math.PI / 4) {
	    direction = (Direction.RIGHT);
	} else if (-3 * Math.PI / 4 < angle && angle < -Math.PI / 4) {
	    direction = (Direction.UP);
	} else if (Math.PI / 4 < angle && angle < 3 * Math.PI / 4) {
	    direction = (Direction.DOWN);
	} else if (angle < -3 * Math.PI / 4 || angle > 3 * Math.PI / 4) {
	    direction = (Direction.LEFT);
	}

	return direction;
    }
    public static Direction velocityToDirection(Point2D from, double velX, double velY){
	Point2D to = new Point2D.Double(from.getX()+velX, from.getY()+velY);
	return directionBetweenPoints(from, to);
    }
}
