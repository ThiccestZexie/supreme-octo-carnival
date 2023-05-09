package se.liu.danal315samak519.weapons;

import se.liu.danal315samak519.entities.Character;
import se.liu.danal315samak519.entities.Movable;

import java.awt.geom.Point2D;

public class Projectile extends Weapon
{
    public Projectile(final Point2D.Double coord, final Character owner, final int projectileHeight, final int projectileWidth ) {
	super(coord, owner);
	this.setLifeSpan(120);

	switch (owner.getDir()) {
	    case UP:
		setSize(projectileWidth, projectileHeight);
		setLocation((owner.getX() + (owner.getWidth() / 2.0) - this.getWidth() / 2.0), (owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		setSize(projectileWidth, projectileHeight);
		setLocation((owner.getX() + (owner.getWidth() / 2.0) - this.getWidth() / 2.0), (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		setSize(projectileHeight, projectileWidth);
		setLocation((owner.getX() - this.getWidth()), (owner.getY() + (owner.getHeight() / 2.0)) - (this.getHeight() / 2.0));
		break;
	    case RIGHT:
		setSize(projectileHeight, projectileWidth);
		setLocation((owner.getX() + owner.getWidth()), (owner.getY() + (owner.getHeight() / 2.0)) - (this.getHeight() / 2.0));
		break;
	}

	setDir(owner.getDir());
	switch (this.dir) {
	    case UP -> setVelY(-3);
	    case DOWN -> setVelY(3);
	    case LEFT -> setVelX(-3);
	    case RIGHT -> setVelX(3);
	}
	this.setHitBox();

    }

    public boolean hitEntity(Movable target) {

	if (this.hitBox.intersects(target.getHitBox())) {
	    this.isGarbage = true;
	    return true;
	}
	return false;
    }
}
