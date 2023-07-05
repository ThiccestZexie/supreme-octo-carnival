package se.liu.danal315samak519.entities.weapons;

import se.liu.danal315samak519.ImageLoader;
import se.liu.danal315samak519.entities.Person;

/**
 * A projectile that is fired by a character.
 */
public class Projectile extends Weapon
{

    public static final int PROJECTILE_LIFE_SPAN = 120;
    private static final float TWO = 2.0f; // Used for halving values

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
		setLocation((owner.getX() + (owner.getWidth() / TWO) - this.getWidth() / TWO), (owner.getY() - this.getHeight()));
		break;
	    case DOWN:
		setSize(projectileWidth, projectileHeight);
		setLocation((owner.getX() + (owner.getWidth() / TWO) - this.getWidth() / TWO), (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		setSize(projectileHeight, projectileWidth);
		setLocation((owner.getX() - this.getWidth()), (owner.getY() + (owner.getHeight() / TWO)) - (this.getHeight() / TWO));
		break;
	    case RIGHT:
		setSize(projectileHeight, projectileWidth);
		setLocation((owner.getX() + owner.getWidth()), (owner.getY() + (owner.getHeight() / TWO)) - (this.getHeight() / TWO));
		break;
	}
	setDirection(owner.getDirection());
    }


    public void storeImages() {
	final int spriteHeight = 16;
	final int spriteWidth = 16;
	final int imageOffsetForArrow = 120;
	ImageLoader enemiesSpriteSheetLoader = new ImageLoader("enemies.png");
	currentSprite = enemiesSpriteSheetLoader.getSubImage(imageOffsetForArrow, 0, spriteWidth, spriteHeight);
    }
}
