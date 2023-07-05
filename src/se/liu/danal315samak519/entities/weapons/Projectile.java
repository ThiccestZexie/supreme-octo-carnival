package se.liu.danal315samak519.entities.weapons;

import se.liu.danal315samak519.ImageLoader;
import se.liu.danal315samak519.entities.Person;

import java.awt.*;
import java.io.IOException;

/**
 * A projectile that is fired by a character.
 */
public class Projectile extends Weapon
{

    public static final int PROJECTILE_LIFE_SPAN = 120;

    public Projectile(final Person owner, final int projectileHeight, final int projectileWidth, final int projectileVel) {
	super(owner);
	this.setLifeSpan(PROJECTILE_LIFE_SPAN);
	snapToOwner(projectileWidth, projectileHeight);

	switch (this.direction) {
	    case UP -> setVelY(-projectileVel);
	    case DOWN -> setVelY(projectileVel);
	    case LEFT -> setVelX(-projectileVel);
	    case RIGHT -> setVelX(projectileVel);
	}
	this.setHitBox();
	storeImages();
    }

    /**
     * Moves the projectile to appropiate location next to owner, depending on owner's direction. Also changes size of projectile to fit the
     * direction.
     *
     * @param projectileWidth  width of projectile
     * @param projectileHeight height of projectile
     */
    private void snapToOwner(float projectileWidth, float projectileHeight) {
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
    }


    public void storeImages() {
	try {
	    final int spriteHeight = 16;
	    final int spriteWidth = 16;
	    final int imageOffsetForArrow = 120;
	    ImageLoader enemiesLoader = new ImageLoader("enemies.png");

	    currentSprite = enemiesLoader.getSubImage(imageOffsetForArrow, 0, spriteWidth, spriteHeight);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
