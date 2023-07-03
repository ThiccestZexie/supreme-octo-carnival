package se.liu.danal315samak519.entities.weapons;

import se.liu.danal315samak519.entities.Character;
import se.liu.danal315samak519.entities.Movable;
import java.awt.image.BufferedImage;

/**
 * Superclass to all weapons.
 */
public abstract class Weapon extends Movable
{
    protected BufferedImage currentSprite = null;
    protected Character owner;
    protected int lifeSpan;

    protected Weapon(final Character owner) {
	this.owner = owner;
	this.lifeSpan = 5;
    }

    public Character getOwner() {
	return owner;
    }

    @Override public BufferedImage getCurrentSprite() {
	return currentSprite;
    }
    @Override public void tick() {
	super.tick();
	lifeSpan--;
	if (lifeSpan <= 0) {
	    isGarbage = true;
	}
    }

    public void followOwner() {
	setVelocity(owner.getVelX(), owner.getVelY());
    }

    public void setLifeSpan(final int lifeSpan) {
	this.lifeSpan = lifeSpan;
    }
}
	