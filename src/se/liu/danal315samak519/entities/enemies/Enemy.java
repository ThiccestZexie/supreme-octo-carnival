package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.ImageLoader;
import se.liu.danal315samak519.Status;
import se.liu.danal315samak519.entities.Character;
import se.liu.danal315samak519.entities.Entity;
import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Player;
import se.liu.danal315samak519.entities.Potion;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Enemy extends Character
{
    protected Player player;

    protected Enemy(final Point2D.Double coord, final Player player)
    {
	super(coord);
	this.level = 1;
	this.player = player;
	setMaxSpeed(2);
    }

    protected void storeDirectionalFrames(int offsetX, int offsetY) {
	try {
	    int spriteHeight = 16;
	    int spriteWidth = 16;

	    ImageLoader imageLoader = new ImageLoader("enemies.png");

	    downFrames = new BufferedImage[2];
	    downFrames[0] = imageLoader.getSubImage(offsetX, offsetY, spriteWidth, spriteHeight);
	    downFrames[1] = imageLoader.getSubImage(offsetX, offsetY + spriteWidth * 2 - 2, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[2];
	    leftFrames[0] = imageLoader.getSubImage(offsetX + 32 - 2, offsetY, spriteWidth, spriteHeight);
	    leftFrames[1] = imageLoader.getSubImage(offsetX + 32 - 2, offsetY + 32, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[2];
	    upFrames[0] = imageLoader.getSubImage(offsetX+(32 - 2) * 2, offsetY, spriteWidth, spriteHeight);
	    upFrames[1] = imageLoader.getSubImage(offsetX+(32 - 2) * 2, offsetY+32, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[2];
	    rightFrames[0] = imageLoader.getSubImage(offsetX +(32 - 2) * 3, offsetY +32, spriteWidth, spriteHeight);
	    rightFrames[1] = imageLoader.getSubImage(offsetX +(32 - 2) * 3, offsetY, spriteWidth, spriteHeight);

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
    public Movable dropItem(){
	return new Potion(this.coord);
    }
    public Point2D.Double getVelocityTowardsPlayer() {
	double x = player.getX() - this.getX();
	double y = player.getY() - this.getY();
	double distance = getDistanceToPlayer();
	Point2D.Double velocity = new Point2D.Double(maxSpeed * x / distance, maxSpeed * y / distance);
	return velocity;
    }

    private double getDistanceToPlayer() {
	return player.getCoord().distance(this.getCoord());
    }

    public boolean canSeePlayer() {
	return true;
//	if (distanceToPlayer() < 500) {
//	    return true;
//	}
//	return false;
    }

    public boolean wantToAttack()
    {
	if (getDistanceToPlayer() < 60) {
	    return true;
	}
	return false;
    }

    public boolean checkIfPlayerIsInFront(int length, int width) {
	if (this.getStatus() != Status.ATTACKING) {
	    Rectangle arrowLength = new Rectangle();
	    switch (getDir()) {
		case UP:
		    arrowLength.setSize(width, length);
		    arrowLength.setLocation((int) (this.getX() + (this.getWidth() / 2.0) - arrowLength.getWidth() / 2.0),
					    (int) (this.getY() - arrowLength.getHeight()));
		    break;
		case DOWN:
		    arrowLength.setSize(width, length);
		    arrowLength.setLocation((int) (this.getX() + (this.getWidth() / 2.0) - arrowLength.getWidth() / 2.0),
					    (int) (this.getY() + (this.getHeight())));
		    break;
		case LEFT:
		    arrowLength.setSize(length, width);
		    arrowLength.setLocation((int) (this.getX() - arrowLength.getWidth()),
					    (int) ((this.getY() + (this.getHeight() / 2.0)) - (arrowLength.getHeight() / 2.0)));
		    break;
		case RIGHT:
		    arrowLength.setSize(length, width);
		    arrowLength.setLocation((int) (this.getX() + this.getWidth()),
					    (int) ((this.getY() + (this.getHeight() / 2.0)) - (arrowLength.getHeight() / 2.0)));
		    break;
	    }

	    if (arrowLength.intersects(player.getHitBox())) {
		return true;
	    }
	}
	return false;
    }
    public void chasePlayer(){
	if (canSeePlayer()) { // CHASe PLAYER!!
	    Point2D velocity = getVelocityTowardsPlayer();
	    setVelocity((int) velocity.getX(), (int) velocity.getY());
	} else { // Chill...
	    setVelocity(0, 0);
	}
    }
}
