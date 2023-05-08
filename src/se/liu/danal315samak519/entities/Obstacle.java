package se.liu.danal315samak519.entities;

import java.awt.geom.Point2D;

public class Obstacle extends Movable
{
    public Obstacle(final Point2D.Double coord, double width, double height){
	setLocation(coord);
	setSize(width, height);
	setHitBox();
    }
}
