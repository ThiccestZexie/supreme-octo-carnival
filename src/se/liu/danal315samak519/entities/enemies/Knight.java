package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

/**
 * A knight enemy that chases the player.
 * Is more powerful than the other enemies.
 */
public class Knight extends Enemy
{

    private static final int IMAGE_OFFSET_X = 120;
    private static final int IMAGE_OFFSET_Y = 180;

    public Knight(final Point2D.Float coord, final Player player) {
	super(coord, player);
	setMaxSpeed(2);
	setDamage(2);
	setStats(5, 2);
	storeSpriteFrames(IMAGE_OFFSET_X, IMAGE_OFFSET_Y);
    }


    @Override public void tick() {
	super.tick();
	chasePlayer();
    }


}
