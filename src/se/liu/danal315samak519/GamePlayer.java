package se.liu.danal315samak519;

import java.awt.*;

public class GamePlayer extends GameEntity
{
    private String Name;
    private int level;
    private JComponent awooga;
    //...
    private Dimension size;
    private Point coord;
    private int velX, velY;

    public GamePlayer(String name, final Point coord)
    {
	this.coord = coord;
	this.size = new Dimension(50,50);
	this.Name = name;
	this.awooga = new JLabel("Penis head");
    }

    public Point getCoord() {
	return coord;
    }

    public Dimension getSize() {
	return size;
    }
    public String getName(){
		 return name;
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
