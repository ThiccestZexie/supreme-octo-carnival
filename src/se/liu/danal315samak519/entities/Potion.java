package se.liu.danal315samak519.entities;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Potion extends Movable
{
    private Player user = null;
    private Image sprite;

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

    public void pickUp(Character e) {
	if (this.hitBox.intersects(e.hitBox) && e instanceof Player) {
	    setUser((Player) e);
	    user.heal(2);
	    this.markAsGarbage();
	}
    }

    public void storeFullHeartImage() {
	try {
	    BufferedImage fullHeart = ImageIO.read(new File("resources/images/hearts/heart_full.png"));
	    this.sprite = fullHeart.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
