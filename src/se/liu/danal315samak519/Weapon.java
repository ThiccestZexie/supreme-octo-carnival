package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Weapon extends Entity
{
    private BufferedImage currentSprite;
    private BufferedImage upSprite;
    private BufferedImage leftSprite;
    private BufferedImage downSprite;
    private BufferedImage rightSprite;
    private Character owner;
    private int lifeSpan = 5;

    public Weapon(final Point2D.Double coord, final Character owner) {
	//We have two choices when it comes to spawning the weapon first look at dir then spawn everything according to that...
	super(coord);
	this.owner = owner;

	switch (owner.getDir()) {
	    case UP:
		this.size = new Dimension(10, 60);
		this.coord = new Point2D.Double((owner.getX() + (owner.getWidth() / 2) - this.getWidth() / 2),
						(owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		this.size = new Dimension(10, 60);
		this.coord = new Point2D.Double((owner.getX() + (owner.getWidth() / 2) - this.getWidth() / 2),
						(owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		size = new Dimension(60, 10);
		this.coord = new Point2D.Double((owner.getX() - this.getWidth()),
						(owner.getY() + (owner.getHeight() / 2)) - (this.getHeight() / 2));
		break;
	    case RIGHT:
		size = new Dimension(60, 10);
		this.coord = new Point2D.Double((owner.getX() + owner.getWidth()),
						(owner.getY() + (owner.getHeight() / 2)) - (this.getHeight() / 2));
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
	setCurrentVelocity(owner.getVelX(), owner.getVelY());
    }

    public int getLifeSpan() {
	return lifeSpan;
    }
}
