package se.liu.danal315samak519.Weapons;

import se.liu.danal315samak519.Character;
import se.liu.danal315samak519.Entity;
import se.liu.danal315samak519.Movable;
import se.liu.danal315samak519.Weapons.Weapon;

import java.awt.geom.Point2D;

public class Arrow extends Weapon
{

    public Arrow(final Point2D.Double coord, final Character owner) {
	super(coord, owner);
	this.setLifeSpan(100);
	setSize(5,5);
	switch (owner.getDir()) {
	    case UP:
		setLocation((owner.getX() + (owner.getWidth() / 2.0) - this.getWidth() / 2.0), (owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		setLocation((owner.getX() + (owner.getWidth() / 2.0) - this.getWidth() / 2.0), (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		setLocation((owner.getX() - this.getWidth()), (owner.getY() + (owner.getHeight() / 2.0)) - (this.getHeight() / 2.0));
		break;
	    case RIGHT:
		setLocation((owner.getX() + owner.getWidth()), (owner.getY() + (owner.getHeight() / 2.0)) - (this.getHeight() / 2.0));
		break;
	}
	setDir(owner.getDir());
	switch (this.dir){
	    case UP -> velY = -2;
	    case DOWN -> velY = 2;
	    case LEFT -> velX = -2;
	    case RIGHT -> velX = 2;
	}
	this.setHitBox();

    }

    @Override public void tick() {
	super.tick();
    }
    public boolean hitEntity(Movable target){


	if(this.hitBox.getBounds().intersects(target.getHitBox().getBounds())){
		this.isGarbage = true;
		return true;
	}
	return false;
    }
}
