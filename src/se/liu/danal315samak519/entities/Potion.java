package se.liu.danal315samak519.entities;


import se.liu.danal315samak519.ImageLoader;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * A potion that heals the player. Immovable and non-attacking.
 */
public class Potion extends Movable
{
    /**
     * Heals the player by 2 everytime.
     */
    private static final int HEALAMOUNT = 2;
    private BufferedImage currentSprite = null;

    public Potion(final Point2D.Float coord) {
	final Dimension potionSize = new Dimension(25, 25);
	setLocation(coord);
	setSize(potionSize.width, potionSize.height);
	setHitBox();
	setColor(Color.GRAY);
	storeCurrentSprite();
	setCanMove(false);
    }


    @Override public BufferedImage getCurrentSprite() {
	return currentSprite;
    }

    /**
     * Tries to be picked up by the player. If the player intersects with the potion, the player heals and the potion is marked as garbage.
     *
     * @param player
     */
    public void tryToBePickedUpBy(Player player) {
	if (this.getHitBox().intersects(player.getHitBox())) {
	    player.heal(HEALAMOUNT);
	    this.setIsGarbage();
	}
    }

    /**
     * If the movable is a player, should be picked up by them.
     *
     * @param movable
     */
    @Override public void interactWith(final Movable movable) {
	if(movable instanceof Player){
	    this.tryToBePickedUpBy((Player) movable);
	}

    }

    public void storeCurrentSprite() {
	ImageLoader imageLoader = new ImageLoader("hearts/heart_full.png");
	this.currentSprite = imageLoader.getImage();
    }

}
