package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

/**
 * A red enemy that chases the player.
 * The most basic enemy.
 */
public class Red extends Enemy
{

    public Red(final Point2D.Float coord, final Player player) {
	super(coord, player);
	final int speed = 3, health = 3, level = 1;
	setMaxSpeed(speed);
	setStats(health, level);
	storeSpriteFrames(0, 0);
    }
}
