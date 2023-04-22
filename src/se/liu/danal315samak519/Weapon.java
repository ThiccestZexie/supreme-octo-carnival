package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;

public class Weapon extends Entity
{
    private final int[] expRequirements = new int[] { 2, 3, 5, 8, 12, 20, 23, 30, 999 }; //from level "0" to level "10"
    private Character owner;
    private int lifeSpan = 5;
    private int currentInvFrames;

    public Weapon(final Point2D.Double coord, final Character owner) {
	//We have two choices when it comes to spawning the weapon first look at dir then spawn everything according to that...
	super(coord);
	this.coord = coord;
	this.owner = owner;

	switch (owner.getDir()) {
	    case UP:
		this.size = new Dimension(10, 60);
		this.coord = new Point2D.Double((owner.getX() + (owner.getWidth() / 2) - this.getWidth() / 2), (owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		this.size = new Dimension(10, 60);
		this.coord = new Point2D.Double((owner.getX() + (owner.getWidth() / 2) - this.getWidth() / 2), (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		size = new Dimension(60, 10);
		this.coord = new Point2D.Double((owner.getX() - this.getWidth()), (owner.getY() + (owner.getHeight() / 2)) - (this.getHeight() / 2));
		break;
	    case RIGHT:
		size = new Dimension(60, 10);
		this.coord =
			new Point2D.Double((owner.getX() + owner.getWidth()), (owner.getY() + (owner.getHeight() / 2)) - (this.getHeight() / 2));
		break;
	}

	setHitBox();
    }

    public Character getOwner() {
	return owner;
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
	this.velX = owner.getVelX();
	this.velY = owner.getVelY();
    }

    public int getLifeSpan() {
	return lifeSpan;
    }

    public int[] getExpRequirements() {
	return expRequirements;
    }

}
