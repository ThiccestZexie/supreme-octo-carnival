package se.liu.danal315samak519.entities;

public class Obstacle extends Movable
{
    private final int id;
    private double endX, endY;

    public Obstacle(double x, double y, double endX, double endY, double width, double height, int id) {
	setLocation(x, y);
	setSize(width, height);
	setHitBox();
	this.id = id;
    }


}
