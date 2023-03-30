package se.liu.danal315samak519;

import java.awt.*;

public class Entity
{
    //...
    private Dimension size;
    private Point coord;
    private int speed;
    private int velX, velY;
    private Color color;

    public Entity(final Point coord, final Color color) {
	this.size = new Dimension(50, 50); // HARDCODED SIZE OF 50,50 px TODO
	this.coord = coord;
	this.color = color;
    }

    public Point getCoord() {
	return coord;
    }

    public Color getColor() {
	return color;
    }

    public Dimension getSize() {
	return size;
    }

    public int getSpeed() {
	return speed;
    }

    public void setSpeed(final int speed) {
	this.speed = speed;
    }

    public void moveTo(final int x, final int y)
    {
	coord.move(x, y);
    }

    public void nudge(final int dx, final int dy) {
	coord.translate(dx, dy);
    }

    public void setCurrentVelocity(final int vx, final int vy) {
	this.velX = vx;
	this.velY = vy;
    }

    public void tick() {
	// Move along according to current velocity
	nudge(velX, velY);
    }

    public int getVelX() {
	return velX;
    }

    public int getVelY() {
	return velY;
    }
}
