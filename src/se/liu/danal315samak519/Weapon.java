package se.liu.danal315samak519;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Weapon extends Movable
{
    private BufferedImage currentSprite;
    private BufferedImage upSprite;
    private BufferedImage leftSprite;
    private BufferedImage downSprite;
    private BufferedImage rightSprite;
    private Character owner;
    private int lifeSpan;

    public Weapon(final Point2D.Double coord, final Character owner) {
	//We have two choices when it comes to spawning the weapon first look at dir then spawn everything according to that...
	this.owner = owner;
	lifeSpan = 5;
	switch (owner.getDir()) {
	    case UP:
		setSize(10, 60);
		setLocation((owner.getX() + (owner.getWidth() / 2.0) - this.getWidth() / 2.0), (owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		setSize(10, 60);
		setLocation((owner.getX() + (owner.getWidth() / 2.0) - this.getWidth() / 2.0), (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		setSize(60, 10);
		setLocation((owner.getX() - this.getWidth()), (owner.getY() + (owner.getHeight() / 2.0)) - (this.getHeight() / 2.0));
		break;
	    case RIGHT:
		setSize(60, 10);
		setLocation((owner.getX() + owner.getWidth()), (owner.getY() + (owner.getHeight() / 2.0)) - (this.getHeight() / 2.0));
		break;
	}
	setHitBox();
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
	followOwner();
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
