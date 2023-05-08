package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;

public enum Direction
{
    UP, DOWN, LEFT, RIGHT;


    public static Direction getDirectionBetweenPoints(double startX, double startY, double endX, double endY, double width, double height) {
	double diffX = endX - startX;
	double diffY = endY - startY;
	double distance = Math.sqrt(diffX * diffX + diffY * diffY);
	if (distance == 0) {
	    return null; // Can't calculate Direction
	}
	float radians = (float) Math.atan2(diffY, diffX);
        return roundAngleToDirection(radians, width, height);
    }

    public Direction getOpposite(){
	return switch(this){
	    case DOWN -> UP;
	    case LEFT -> RIGHT;
	    case UP -> DOWN;
	    case RIGHT -> LEFT;
	};
    }

    public static Direction getDirectionBetweenPoints(Point2D start, Point2D end, double width, double height) {
	return getDirectionBetweenPoints(start.getX(), start.getY(), end.getX(), end.getY(), width, height);
    }

    public static Direction getDirectionBetweenPoints(Point2D start, Point2D end) {
	return getDirectionBetweenPoints(start.getX(), start.getY(), end.getX(), end.getY(), 1, 1);
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
    public static Direction roundAngleToDirection(double radians, double width, double height) {
	double a = width / 2.0;
	double b = height / 2.0;
	double angleLeftRight = Math.atan2(b, a);
	double angleUpDown = (Math.PI - 2 * angleLeftRight) / 2.0;

        if(-angleUpDown <= radians && radians <= angleUpDown){
            return RIGHT;
        } else if (-angleUpDown-2*angleLeftRight <= radians && radians <= -angleUpDown) {
            return UP;
//        } else if (Math.PI - angleUpDown <= radians && radians <= Math.PI + angleUpDown) {
//           return LEFT;
        } else if (angleUpDown <= radians && radians <= angleUpDown + 2*angleLeftRight) {
            return DOWN;
        }
        return LEFT; // Idk why the angle sometimes isn't caught
    }

    public static Direction getDirectionBetweenPoints(final double x, final double y, final double endX, final double endY) {
	return getDirectionBetweenPoints(x, y, endX, endY, 1, 1);
    }

    /**
     * Assumes the shape is square of rectangle
     *
     * @param radians
     *
     * @return
     */
    public static Direction roundAngleToDirection(double radians) {
	return roundAngleToDirection(radians, 1, 1);
    }

    public static Direction velocityToDirection(double velX, double velY) {
	Point start = new Point(0, 0);
	Point2D end = new Point2D.Double(velX, velY);
	return getDirectionBetweenPoints(start, end);
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
