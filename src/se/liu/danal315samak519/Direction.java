package se.liu.danal315samak519;

import java.awt.geom.Point2D;

/**
 * Enum for directions. Used for movement and animation.
 */
public enum Direction
{
    UP, DOWN, LEFT, RIGHT;


    public static Direction getDirectionBetweenPoints(float startX, float startY, float endX, float endY, float width, float height) {
	float diffX = endX - startX;
	float diffY = endY - startY;
	float distance = (float) Math.sqrt(diffX * diffX + diffY * diffY);
	if (distance == 0) {
	    return null; // Can't calculate Direction
	}
	float radians = (float) Math.atan2(diffY, diffX);
	return roundAngleToDirection(radians, width, height);
    }

    public static Direction getDirectionBetweenPoints(Point2D.Float start, Point2D.Float end) {
	return getDirectionBetweenPoints(start.x, start.y, end.x, end.y, 1, 1);
    }

    public static Direction getDirectionBetweenPoints(final float startX, final float startY, final float endX, final float endY) {
	return getDirectionBetweenPoints(startX, startY, endX, endY, 1, 1);
    }

    /**
     * Calculates what direction would be normal to the surface of rectangle given rectangle shape
     *
     * @param radians
     * @param width
     * @param height
     *
     * @return
     */
    public static Direction roundAngleToDirection(float radians, float width, float height) {
	final float a = width / 2.0f;
	final float b = height / 2.0f;
	float angleLeftRight = (float) Math.atan2(b, a);
	final float angleUpDown = (float) ((Math.PI - 2 * angleLeftRight) / 2.0);

	if (-angleUpDown <= radians && radians <= angleUpDown) {
	    return Direction.RIGHT;
	} else if (-angleUpDown - 2 * angleLeftRight <= radians && radians <= -angleUpDown) {
	    return Direction.UP;
	} else if (angleUpDown <= radians && radians <= angleUpDown + 2 * angleLeftRight) {
	    return Direction.DOWN;
	}
	return Direction.LEFT;
    }

    /**
     * Assumes the shape is square of rectangle
     *
     * @param radians
     *
     * @return
     */

    public static Direction velocityToDirection(float velX, float velY) {
	Point2D.Float start = new Point2D.Float(0, 0);
	Point2D.Float end = new Point2D.Float(velX, velY);
	return getDirectionBetweenPoints(start, end);
    }

    public Direction getOpposite() {
	return switch (this) {
	    case DOWN -> UP;
	    case LEFT -> RIGHT;
	    case UP -> DOWN;
	    case RIGHT -> LEFT;
	};
    }

    public int getX() {
	return switch (this) {
	    case UP, DOWN -> 0;
	    case LEFT -> -1;
	    case RIGHT -> 1;
	};
    }

    public int getY() {
	return switch (this) {
	    case UP -> -1;
	    case DOWN -> 1;
	    case LEFT, RIGHT -> 0;
	};

    }
}
