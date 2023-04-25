package se.liu.danal315samak519;

import java.awt.geom.Point2D;

/**
 * Used to mark specific points on the map where, upon entry, things will happen.
 * For example, loading the next map if player walks through map exit.
 */
public class Zone extends Entity
{
    protected Zone(int id, double x, double y, double width, double height) {
	setLocation(x, y);
	setSize(width, height);
    }
}
