package se.liu.danal315samak519.weapons;

import se.liu.danal315samak519.entities.Character;
import se.liu.danal315samak519.entities.Movable;

import java.awt.geom.Point2D;

public class Projectile extends Weapon
{
    public Projectile(final Point2D.Float coord, final Character owner, final int projectileHeight, final int projectileWidth, final int projectileVel) {
	super(coord, owner);
	this.setLifeSpan(120);

	switch (owner.getDir()) {
	    case UP:
		setSize(projectileWidth, projectileHeight);
		setLocation((owner.getX() + (owner.getWidth() / 2.0f) - this.getWidth() / 2.0f), (owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		setSize(projectileWidth, projectileHeight);
		setLocation((owner.getX() + (owner.getWidth() / 2.0f) - this.getWidth() / 2.0f), (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		setSize(projectileHeight, projectileWidth);
		setLocation((owner.getX() - this.getWidth()), (owner.getY() + (owner.getHeight() / 2.0f)) - (this.getHeight() / 2.0f));
		break;
	    case RIGHT:
		setSize(projectileHeight, projectileWidth);
		setLocation((owner.getX() + owner.getWidth()), (owner.getY() + (owner.getHeight() / 2.0f)) - (this.getHeight() / 2.0f));
		break;
	}

	setDir(owner.getDir());
	switch (this.dir) {
	    case UP -> setVelY(-projectileVel);
	    case DOWN -> setVelY(projectileVel);
	    case LEFT -> setVelX(-projectileVel);
	    case RIGHT -> setVelX(projectileVel);
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
