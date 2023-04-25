package se.liu.danal315samak519.map;

import se.liu.danal315samak519.Entity;

/**
 * Used to block entities on arbitrary areas (not on tiles)
 * Can be moved (used as animating gate)
 */
public class Obstacle extends Entity
{
    protected Obstacle(int id, double x, double y, double width, double height) {
	setLocation(x, y);
	setSize(width, height);
    }
}
