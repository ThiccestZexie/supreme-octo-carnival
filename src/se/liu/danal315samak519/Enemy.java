package se.liu.danal315samak519;

import java.awt.geom.Point2D;

public class Enemy extends Character
{

    public Enemy(final Point2D.Double coord)
    {
	super(coord);

    }

    public void isKilled(final Weapon weapon) {


    }

    @Override public boolean isHit(final Weapon weapon) {

	return super.isHit(weapon);

    }
}
