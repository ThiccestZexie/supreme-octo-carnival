package se.liu.danal315samak519;

import java.awt.geom.Point2D;

public class Sword extends Weapon
{
    public Sword(final Point2D.Double coord, final Character owner) {
	super(coord, owner);

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
	this.setLifeSpan(5);
	setHitBox();

    }

    @Override public void tick() {
	super.tick();
	followOwner();
    }
}
