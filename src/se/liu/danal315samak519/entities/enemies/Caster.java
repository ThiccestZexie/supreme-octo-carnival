package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

/**
 * A caster enemy that shoots magic at the player.
 */
public class Caster extends Enemy
{


    public Caster(final Point2D.Float coord, final Player player) {
	super(coord, player);
	final int speed = 1, health = 2, level = 2;
	final int projectileWidth = 50, projectileHeight = 5, projectileVelocity = 6;
	final int imageOffsetY = 60, imageOffsetX = 240;

	setMaxSpeed(speed);
	setStats(health, level);

	storeSpriteFrames(imageOffsetX, imageOffsetY);
	setProjectileVelocity(projectileVelocity);
	setProjectileWidth(projectileWidth);
	setProjectileHeight(projectileHeight);
    }


    @Override public void tick() {
	super.tick();
	chasePlayer();
    }
}
