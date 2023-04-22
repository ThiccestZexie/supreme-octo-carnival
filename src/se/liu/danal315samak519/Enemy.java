package se.liu.danal315samak519;

import java.awt.*;

public class Enemy extends Character
{

    public Enemy(final Point coord)
    {
		super(coord, Color.red);

    }

    public void isKilled(final Weapon weapon){


    }

    @Override public boolean isHit(final Weapon weapon) {

	return super.isHit(weapon);

    }
}
