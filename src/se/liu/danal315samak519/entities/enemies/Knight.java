package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

/**
 * A knight enemy that chases the player.
 * Is more powerful than the other enemies.
 */
public class Knight extends Enemy
{
    public Knight(final Point2D.Float coord, final Player player) {
	super(coord, player);
	final int speed = 2, health = 5, level = 2, damage = 2;
	final int imageOffsetY = 180, imageOffsetX = 120;
	setMaxSpeed(speed);
	setDamage(damage);
	setStats(health, level);
	storeSpriteFrames(imageOffsetX, imageOffsetY);
    }
}
