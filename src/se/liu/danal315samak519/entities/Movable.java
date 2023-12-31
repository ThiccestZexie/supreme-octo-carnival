package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Entities that have velocity, and move according to velocity when ticking.
 */
public abstract class Movable extends Entity
{
    protected Direction direction = Direction.DOWN;
    protected float maxSpeed;
    protected float velX;
    protected float velY;
    private boolean canMove = true;

    /**
     * List of movables that THIS movable wants to add. Used for projectiles and loot.
     */
    private Deque<Movable> pending = new LinkedList<>();

    public Direction getDirection() {
	return direction;
    }

    public void setDirection(final Direction direction) {
	this.direction = direction;
    }

    /**
     * Default returns null, which indicates to GameComponent that this is invisible. Should be overriden by entities that actually have
     * sprites.
     *
     * @return
     */
    public BufferedImage getCurrentSprite() {
	return null;
    }

    /**
     * Nudges this movable away from another hitbox
     *
     * @param otherHitBox
     */
    public void nudgeAwayFrom(Rectangle2D otherHitBox) {
	if (!this.getHitBox().intersects(otherHitBox)) {
	    return; // No collision
	}
	// Get intersection
	Rectangle2D intersection = this.getHitBox().createIntersection(otherHitBox);
	// Convert to float
	Rectangle2D.Float intersectionFloat =
		new Rectangle2D.Float((float) intersection.getX(), (float) intersection.getY(), (float) intersection.getWidth(),
				      (float) intersection.getHeight());
	// Determine if horizontal or vertical collision
	boolean isHorizontalCollision = intersectionFloat.getWidth() < intersectionFloat.getHeight();

	// Nudge away from collision
	if (isHorizontalCollision) { // Left-right collision
	    if (intersectionFloat.getCenterX() < this.getHitBox().getCenterX()) { // Left collision
		nudge(intersectionFloat.width, 0);
	    } else { // Right collision
		nudge(-intersectionFloat.width, 0);
	    }
	} else { // Up-down collision
	    if (intersectionFloat.getCenterY() < this.getHitBox().getCenterY()) { // Up collision
		nudge(0, intersectionFloat.height);
	    } else { // Down collision
		nudge(0, -intersectionFloat.height);
	    }
	}
    }

    public void draw(final Graphics g) {
	g.drawImage(this.getCurrentSprite(), this.getEntityIntX(), this.getEntityIntY(), this.getIntWidth(), this.getIntHeight(), null);
    }

    public float getMaxSpeed() {
	return maxSpeed;
    }

    public void setMaxSpeed(final float speed) {
	this.maxSpeed = speed;
    }

    public void nudge(final float dx, final float dy) {
	setLocation(getX() + dx, getY() + dy);
	nudgeHitBox(dx, dy);
    }

    public void setVelocity(final float vx, final float vy) {
	setVelX(vx);
	setVelY(vy);
    }


    /**
     * Sets direction according to velocity.
     */
    private void setCorrectDirection() {
	Direction newDirection = Direction.velocityToDirection(getVelX(), getVelY());
	if (newDirection != null) {
	    setDirection(newDirection);
	}
    }

    /**
     * Pops a movable from internal list of pending movables.
     */
    public Movable popPending() {
	return pending.pop();
    }

    /**
     * Pushes a movable to internal list of pending movables.
     *
     * @param movable
     */
    protected void pushPending(Movable movable) {
	pending.push(movable);
    }

    /**
     * Moves according to velocity.
     */
    public void tick() {
	nudge(velX, velY);
    }

    private void nudgeHitBox(final float dx, final float dy) {
	setLocationOfHitBox(hitBox.x + dx, hitBox.y + dy);
    }

    public float getVelX() {
	return velX;
    }

    public void setVelX(final float vx) {
	if (getCanMove()) {
	    this.velX = vx;
	    setCorrectDirection();
	}
    }

    public float getVelY() {
	return velY;
    }

    public void setVelY(final float vy) {
	if (getCanMove()) {
	    this.velY = vy;
	    setCorrectDirection();
	}
    }

    /**
     * Returns true if there are pending movables.
     *
     * @return
     */
    public boolean hasPending() {
	return !pending.isEmpty();
    }

    /**
     * Interacts with another movable. Override in subclasses. Usually means taking damage or dealing damage. Or in potions case, being
     * picked up.
     *
     * @param movable
     */
    public abstract void interactWith(final Movable movable);

    public boolean intersects(final Movable movable) {
	return this.getHitBox().intersects(movable.getHitBox());
    }

    public boolean equalWith(final Movable movable) {
	return this.equals(movable);
    }

    /**
     * Movables don't deal damage by default, so this returns 0. Override in subclasses.
     *
     * @return 0
     */
    public int getDamage() {
	return 0;
    }

    /**
     * Returns if the movable can move or not.
     *
     * @return
     */
    public boolean getCanMove() {
	return canMove;
    }

    /**
     * Change if the movable can move or not.
     *
     * @param canMove
     */
    protected void setCanMove(boolean canMove) {
	this.canMove = canMove;
    }
}
