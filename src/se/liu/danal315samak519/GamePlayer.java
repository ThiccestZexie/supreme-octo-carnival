package se.liu.danal315samak519;

import java.awt.*;

public class GamePlayer extends GameEntity
{
    private String name;
    private int level;
    public GamePlayer(String name, final Point coord, final Color color	)
    {
	super(coord, color);
	this.name = name;

    }

    public String getName(){
	 return name;
    }

}
