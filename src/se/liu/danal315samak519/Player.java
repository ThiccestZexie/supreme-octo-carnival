package se.liu.danal315samak519;

import java.awt.*;

public class Player extends Entity
{
    private String name;

    public Player(String name, final Point coord, final Color color)
    {
	super(coord, color);
	this.name = name;
    }

    public String getName() {
	return name;
    }
}
