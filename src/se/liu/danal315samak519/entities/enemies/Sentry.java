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
    private static final int PROJECTILE_WIDTH = 20;
    private static final int IMAGE_OFFSET_X = 120;

    public Sentry(final Point2D.Float coord, final Player player) {
	super(coord, player);
	final int health = 2, level = 2;
	final int projectileWidth = 50;
	final int imageOffsetX = 120;
	setStats(health, level);
	storeSpriteFrames(imageOffsetX, 0);
	setProjectileWidth(projectileWidth);
    }

    @Override public void tick() {
	super.tick();
	setDirection(getDirectionTowardsPlayer());
	final int fowardVision = 500, sideVision = 100;
	if(checkIfPlayerIsInFront(fowardVision, sideVision) && canAttack()){
	    this.becomeAttacking();
	    shoot();
	}
    }

    /**
     * Pushes a projectile to internal list of pending projectiles.
     */
    private void shoot() {
	pushPending(getProjectile());
    }

    /**
     * Returns the direction towards the player.
     * @return
     */
    private Direction getDirectionTowardsPlayer() {
	return Direction.getDirectionBetweenPoints(this.getCoord(), player.getCoord());
    }
}