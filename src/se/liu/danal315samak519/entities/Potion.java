package se.liu.danal315samak519.entities;


import se.liu.danal315samak519.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A potion that heals the player.
 */
public class Potion extends Movable
{
    private Player user = null;
    private BufferedImage currentSprite = null;
    private static final int HEALAMOUNT = 2;
    public Potion(final Point2D.Float coord) {
	setLocation(coord);
	setSize(25, 25);
	setHitBox();
	setColor(Color.GRAY);
	storeFullHeartImage();
    }


    @Override public BufferedImage getCurrentSprite() {
	return currentSprite;
    }

    private void setUser(Player user) {
	this.user = user;
    }

    public void pickUp(Player e) {
	if (this.hitBox.intersects(e.hitBox)) {
	    setUser(e);
	    user.heal(HEALAMOUNT);
	    this.markAsGarbage();
	}
    }

    public void storeFullHeartImage() {
	try {
	    ImageLoader imageLoader = new ImageLoader("hearts/heart_full.png");
	    int droppedheartWidth = 50, droppheartHeight = 50;
	    this.currentSprite = imageLoader.getImage();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
