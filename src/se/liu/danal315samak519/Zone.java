package se.liu.danal315samak519;

import java.awt.geom.Point2D;

/**
 * Used to mark specific points on the map where, upon entry, things will happen.
 * For example, loading the next map if player walks through map exit.
 */
public class Zone extends Entity
{
    protected Zone(final Point2D.Double coord, double width, double height) {
	super(coord);
	setSize(width, height);
    }

    protected Zone(final Point2D.Double start, final Point2D.Double end) {
	super(start);
	if(start.getX() > end.getX() || start.getY() > end.getY()){
	    throw new IllegalArgumentException("Invalid point coordinates: start must be lower-left corner and end must be upper-right corner");
	}
	double width = end.getX() - start.getX();
	double height = end.getY() - start.getY();
	setSize(width, height);
    }
}
