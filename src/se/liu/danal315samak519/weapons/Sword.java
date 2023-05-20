package se.liu.danal315samak519.weapons;

import se.liu.danal315samak519.entities.Character;

import java.awt.geom.Point2D;

/**
 * A sword that is swung by a character.
 */
public class Sword extends Weapon
{
    public Sword(final Point2D.Float coord, final Character owner) {
	super(coord, owner);
	switch (owner.getDirection()) {
	    case UP:
		setSize(10f, 60f);
		setLocation((owner.getX() + (owner.getWidth() / 2.0f) - this.getWidth() / 2.0f), (owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		setSize(10f, 60f);
		setLocation((owner.getX() + (owner.getWidth() / 2.0f) - this.getWidth() / 2.0f), (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		setSize(60f, 10f);
		setLocation((owner.getX() - this.getWidth()), (owner.getY() + (owner.getHeight() / 2.0f)) - (this.getHeight() / 2.0f));
		break;
	    case RIGHT:
		setSize(60f, 10f);
		setLocation((owner.getX() + owner.getWidth()), (owner.getY() + (owner.getHeight() / 2.0f)) - (this.getHeight() / 2.0f));
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
