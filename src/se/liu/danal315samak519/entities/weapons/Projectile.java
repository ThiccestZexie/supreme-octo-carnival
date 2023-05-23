package se.liu.danal315samak519.entities.weapons;

import se.liu.danal315samak519.ImageLoader;
import se.liu.danal315samak519.entities.Character;
import se.liu.danal315samak519.entities.Movable;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * A projectile that is fired by a character.
 */
public class Projectile extends Weapon
{

    public Projectile(final Point2D.Float coord, final Character owner, final int projectileHeight, final int projectileWidth, final int projectileVel) {
	super(coord, owner);
	this.setLifeSpan(120);

	switch (owner.getDirection()) {
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

	setDirection(owner.getDirection());
	switch (this.direction) {
	    case UP -> setVelY(-projectileVel);
	    case DOWN -> setVelY(projectileVel);
	    case LEFT -> setVelX(-projectileVel);
	    case RIGHT -> setVelX(projectileVel);
	}
	this.setHitBox();
	storeImages();

    }

    public boolean hitEntity(Movable target) {

	if (this.hitBox.intersects(target.getHitBox())) {
	    this.isGarbage = true;
	    return true;
	}
	return false;
    }

    public void storeImages(){
	try {
	    int spriteHeight = 16;
	    int spriteWidth = 16;

	    ImageLoader enemiesLoader = new ImageLoader("enemies.png");

	    currentSprite = enemiesLoader.getSubImage( 120,0,spriteWidth, spriteHeight);
	}
	catch (IOException e) {
	    e.printStackTrace();
    }
}
}
