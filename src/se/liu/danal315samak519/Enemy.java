package se.liu.danal315samak519;

import java.awt.*;

public class Enemy extends Entity
{

    public Enemy(final Point coord)
    {
		super(coord, Color.red);

    }

    public void isKilled(final WeaponEntity weaponEntity){


    }

    @Override public boolean isHit(final WeaponEntity weaponEntity) {

	return super.isHit(weaponEntity);

    }
}
