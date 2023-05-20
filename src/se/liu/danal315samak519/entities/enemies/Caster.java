package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

public class Caster extends Enemy
{

    public Caster(final Point2D.Float coord, final Player player) {
	super(coord, player);
	setMaxSpeed(1);
	setStats(2, 2);
	storeSpriteFrames(240, 60);
	setProjectileVelocity(6);
	setProjectileWidth(50);
	setProjectileHeight(5);
    }


    @Override public void tick() {
	super.tick();
	chasePlayer();
    }
}
