package se.liu.danal315samak519.entities;


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
    private Image sprite = null;
    private static final int HEALAMOUNT = 2;
    public Potion(final Point2D.Float coord) {
	setLocation(coord);
	setSize(25, 25);
	setHitBox();
	setColor(Color.GRAY);
	storeFullHeartImage();
    }

    public Image getSprite() {
	return sprite;
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
	    BufferedImage fullHeart = ImageIO.read(new File("resources/images/hearts/heart_full.png"));
	    int droppedheartWidth = 50, droppheartHeight = 50;
	    this.sprite = fullHeart.getScaledInstance(droppedheartWidth, droppheartHeight, Image.SCALE_DEFAULT);
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
