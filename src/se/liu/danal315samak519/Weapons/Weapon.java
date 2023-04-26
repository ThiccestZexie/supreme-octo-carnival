package se.liu.danal315samak519.Weapons;

import se.liu.danal315samak519.Character;
import se.liu.danal315samak519.Movable;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Weapon extends Movable
{
    protected BufferedImage currentSprite;
    protected BufferedImage upSprite;
    protected BufferedImage leftSprite;
    protected BufferedImage downSprite;
    protected BufferedImage rightSprite;
    protected Character owner;
    protected int lifeSpan;

    protected Weapon(final Point2D.Double coord, final Character owner) {
	//We have two choices when it comes to spawning the weapon first look at dir then spawn everything according to that...
	this.owner = owner;
	this.coord = coord;
	this.lifeSpan = 5;
    }

    public Character getOwner() {
	return owner;
    }

    public BufferedImage getCurrentSprite() {
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

    public int getLifeSpan() {
	return lifeSpan;
    }

    public void setLifeSpan(final int lifeSpan) {
	this.lifeSpan = lifeSpan;
    }
}
