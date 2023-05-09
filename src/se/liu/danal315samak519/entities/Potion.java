package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Potion extends Movable
{
    private Player user = null;
    private Image fullHeart;

    public Potion(final Point2D.Float coord) {
	setLocation(coord);
	setSize(25, 25);
	setHitBox();
	setColor(Color.GRAY);
	this.setDir(Direction.DOWN);
	this.storeImage();
    }

    public Image getFullHeart() {
	return fullHeart;
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

    public void storeImage() {
	try {
	    BufferedImage bufferedImage = ImageIO.read(new File("resources/images/hearts/heart_full.png"));
	    fullHeart = bufferedImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
