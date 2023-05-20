package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

/**
 * A sentry enemy that shoots at the player.
 * Can not move, instead it only rotates to face the player.
 */
public class Sentry extends Enemy
{
    public Sentry(final Point2D.Float coord, final Player player) {
	super(coord, player);
	setStats(2, 1);
	storeSpriteFrames(120, 0);
	setProjectileWidth(20);
    }

    @Override public void tick() {
	super.tick();
	setDirection(facePlayer());
    }

    private Direction facePlayer() {
	return Direction.getDirectionBetweenPoints(this.getCoord(), player.getCoord());
    }
}