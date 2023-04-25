package se.liu.danal315samak519.map;

import se.liu.danal315samak519.Entity;

/**
 * Used to mark specific points on the map where, upon entry, things will happen.
 * For example, loading the next map if player walks through map exit.
 */
public class Obstacle extends Entity
{
    protected Obstacle(int id, double x, double y, double width, double height) {
	setLocation(x, y);
	setSize(width, height);
    }
}
