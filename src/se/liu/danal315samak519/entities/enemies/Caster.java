package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

/**
 * A caster enemy that shoots magic at the player.
 */
public class Caster extends Enemy
{

    private static final int IMAGE_OFFSET_X = 240;
    private static final int IMAGE_OFFSET_Y = 60;
    private static final int PROJECTILE_WIDTH = 50;

    public Caster(final Point2D.Float coord, final Player player) {
	super(coord, player);
	setMaxSpeed(1);
	setStats(2, 2);
	storeSpriteFrames(IMAGE_OFFSET_X, IMAGE_OFFSET_Y);
	setProjectileVelocity(6);
	setProjectileWidth(PROJECTILE_WIDTH);
	setProjectileHeight(5);
    }


    @Override public void tick() {
	super.tick();
	chasePlayer();
    }
}
