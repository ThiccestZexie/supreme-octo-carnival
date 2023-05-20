package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

public class Knight extends Enemy
{

    public Knight(final Point2D.Float coord, final Player player) {
	super(coord, player);
	setMaxSpeed(2);
	setDamage(2);
	setStats(5, 2);
	storeSpriteFrames(120, 180);
    }


    @Override public void tick() {
	super.tick();
	chasePlayer();
    }


}
