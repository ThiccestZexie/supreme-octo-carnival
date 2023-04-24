package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;

public class DirectionUtil
{
    public static Direction getDirectionBetweenPoints(Point2D start, Point2D end) {
	if (start.distance(end) == 0) {
	    return null; // Can't calculate Direction
	}
	Point2D delta = new Point2D.Double(end.getX() - start.getX(), end.getY() - start.getY());
	float radians = (float) Math.atan2(delta.getY(), delta.getX());
	Direction direction = null;

	if (-Math.PI / 4 < radians && radians < Math.PI / 4) {
	    direction = (Direction.RIGHT);
	} else if (-3 * Math.PI / 4 < radians && radians < -Math.PI / 4) {
	    direction = (Direction.UP);
	} else if (Math.PI / 4 < radians && radians < 3 * Math.PI / 4) {
	    direction = (Direction.DOWN);
	} else if (radians < -3 * Math.PI / 4 || radians > 3 * Math.PI / 4) {
	    direction = (Direction.LEFT);
	}

	return direction;
    }

    public static Direction velocityToDirection(double velX, double velY) {
	Point start = new Point(0, 0);
	Point2D end = new Point2D.Double(velX, velY);
	return getDirectionBetweenPoints(start, end);
    }
}
