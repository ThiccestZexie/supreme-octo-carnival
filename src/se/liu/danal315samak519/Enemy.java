package se.liu.danal315samak519;

import java.awt.*;

public class Enemy extends Entity
{

    public Enemy(final Point coord)
    {
		super(coord, Color.red);

    }

    public void isDead(WeaponEntity weapon)
    {
	if(isHit(weapon)){
	   Entity player = weapon.getOwner();
	   player.getExp();
	}
    }

}
