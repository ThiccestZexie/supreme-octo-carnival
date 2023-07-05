package se.liu.danal315samak519.entities;


import se.liu.danal315samak519.ImageLoader;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * A potion that heals the player.
 */
public class Potion extends Movable
{
    private static final int HEALAMOUNT = 2;
    private Player user = null;
    private BufferedImage currentSprite = null;

    public Potion(final Point2D.Float coord) {
	final Dimension potionSize = new Dimension(25, 25);
	setLocation(coord);
	setSize(potionSize.width, potionSize.height);
	setHitBox();
	setColor(Color.GRAY);
	storeFullHeartImage();
    }


    @Override public BufferedImage getCurrentSprite() {
	return currentSprite;
    }

    @Override public void handleCollision(Movable movable) {

	pickUp((Person) movable);
    }

    private void setUser(Player user) {
	this.user = user;
    }

    public void pickUp(Person player) {
	if (this.hitBox.intersects(player.hitBox) && player instanceof Player) {
	    setUser((Player) player);
	    user.heal(HEALAMOUNT);
	    this.markAsGarbage();
	}
    }

    public void storeFullHeartImage() {
	ImageLoader imageLoader = new ImageLoader("hearts/heart_full.png");
	this.currentSprite = imageLoader.getImage();
    }

}
