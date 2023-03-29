package se.liu.danal315samak519;

import java.awt.*;

public class GameEntity
{
    //...
    protected Dimension size;
    protected Point coord;
    private int velX;
    private int velY;
    private Color color;

    public GameEntity(final Point coord, final Color color) {
	this.size = new Dimension(50,50);
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

    public void moveTo(final int x, final int y)
    {
		coord.move(x,y);
    }

    public void nudge(final int dx, final int dy) {
		coord.translate(dx, dy);
    }

    public void setVelocity(final int vx, final int vy) {
	this.velX = vx;
	this.velY = vy;
    }

    public void tick() {
	nudge(velX, velY);
    }

    public int getVelocityX() {
	return velX;
    }

    public int getVelocityY() {
	return velY;
    }
}
