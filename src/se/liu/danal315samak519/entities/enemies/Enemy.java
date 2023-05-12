package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.ImageLoader;
import se.liu.danal315samak519.entities.Character;
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

    protected int collisionDamage;

    protected Enemy(final Point2D.Float coord, final Player player)
    {
	super(coord);
	this.level = 1;
	this.collisionDamage = 1;
	this.player = player;
	setMaxSpeed(2);
    }

    /**
     * Takes in an offset and uses that to index the spritesheet from enemies.png.
     * Stores the frames in the Enemy's frames variables.
     * @param offsetX
     * @param offsetY
     */
    protected void storeSpriteFrames(int offsetX, int offsetY) {
	try {
	    int spriteHeight = 16;
	    int spriteWidth = 16;

	    ImageLoader imageLoader = new ImageLoader("enemies.png");

	    downFrames = new BufferedImage[3];
	    downFrames[0] = imageLoader.getSubImage(offsetX, offsetY, spriteWidth, spriteHeight);
	    downFrames[1] = imageLoader.getSubImage(offsetX, offsetY + spriteWidth * 2 - 2, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[3];
	    leftFrames[0] = imageLoader.getSubImage(offsetX + 32 - 2, offsetY, spriteWidth, spriteHeight);
	    leftFrames[1] = imageLoader.getSubImage(offsetX + 32 - 2, offsetY + 32, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[3];
	    upFrames[0] = imageLoader.getSubImage(offsetX + (32 - 2) * 2, offsetY, spriteWidth, spriteHeight);
	    upFrames[1] = imageLoader.getSubImage(offsetX + (32 - 2) * 2, offsetY + 32, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[3];
	    rightFrames[0] = imageLoader.getSubImage(offsetX + (32 - 2) * 3, offsetY + 32, spriteWidth, spriteHeight);
	    rightFrames[1] = imageLoader.getSubImage(offsetX + (32 - 2) * 3, offsetY, spriteWidth, spriteHeight);

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    //TODO make drops random and not guaranteed...
    public Movable dropItem() {
	return new Potion(this.coord);
    }

    public Point2D.Float getVelocityTowardsPlayer() {
	float x = player.getX() - this.getX();
	float y = player.getY() - this.getY();
	float distance = (float) getDistanceToPlayer();
	Point2D.Float velocity = new Point2D.Float(maxSpeed * x / distance, maxSpeed * y / distance);
	return velocity;
    }

    public int getCollisionDamage() {
	return collisionDamage;
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
	if (canAttack()) {
	    Rectangle raycastRectangle = new Rectangle();
	    switch (getDir()) {
		case UP:
		    raycastRectangle.setSize(width, length);
		    raycastRectangle.setLocation((int) (this.getX() + (this.getWidth() / 2.0) - raycastRectangle.getWidth() / 2.0),
						 (int) (this.getY() - raycastRectangle.getHeight()));
		    break;
		case DOWN:
		    raycastRectangle.setSize(width, length);
		    raycastRectangle.setLocation((int) (this.getX() + (this.getWidth() / 2.0) - raycastRectangle.getWidth() / 2.0),
						 (int) (this.getY() + (this.getHeight())));
		    break;
		case LEFT:
		    raycastRectangle.setSize(length, width);
		    raycastRectangle.setLocation((int) (this.getX() - raycastRectangle.getWidth()),
						 (int) ((this.getY() + (this.getHeight() / 2.0)) - (raycastRectangle.getHeight() / 2.0)));
		    break;
		case RIGHT:
		    raycastRectangle.setSize(length, width);
		    raycastRectangle.setLocation((int) (this.getX() + this.getWidth()),
						 (int) ((this.getY() + (this.getHeight() / 2.0)) - (raycastRectangle.getHeight() / 2.0)));
		    break;
	    }

	    if (raycastRectangle.intersects(player.getHitBox())) {
		return true;
	    }
	}
	return false;
    }

    public void chasePlayer() {
	if (canSeePlayer()) { // CHASe PLAYER!!
	    Point2D.Float velocity = getVelocityTowardsPlayer();
	    setVelocity(velocity.x, velocity.y);
	} else { // Chill...
	    setVelocity(0, 0);
	}
    }
}
