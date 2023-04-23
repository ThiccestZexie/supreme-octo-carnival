package se.liu.danal315samak519;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy extends Character
{
    private Player player;

    public Enemy(final Point2D.Double coord, final Player player)
    {
	super(coord);
	this.player = player;
	storeDirectionalFrames();
	setDir(Direction.DOWN);
	// TODO REMOVE SLEEPING STATUS FROM ENEMY
	setStatus(Status.SLEEPING);
    }

    private void storeDirectionalFrames() {
	try {
	    int spriteHeight = 16;
	    int spriteWidth = 16;

	    ImageLoader imageLoader = new ImageLoader("enemies.png");

	    downFrames = new BufferedImage[2];
	    downFrames[0] = imageLoader.getSubImage(0, 0, spriteWidth, spriteHeight);
	    downFrames[1] = imageLoader.getSubImage(0, spriteWidth * 2 - 2, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[2];
	    leftFrames[0] = imageLoader.getSubImage(32 - 2, 0, spriteWidth, spriteHeight);
	    leftFrames[1] = imageLoader.getSubImage(32 - 2, 32, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[2];
	    upFrames[0] = imageLoader.getSubImage((32 - 2) * 2, 0, spriteWidth, spriteHeight);
	    upFrames[1] = imageLoader.getSubImage((32 - 2) * 2, 32, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[2];
	    rightFrames[0] = imageLoader.getSubImage((32 - 2) * 3, 32, spriteWidth, spriteHeight);
	    rightFrames[1] = imageLoader.getSubImage((32 - 2) * 3, 0, spriteWidth, spriteHeight);

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public Point2D.Double getVelocityTowardsPlayer() {
	double x = player.getX() - this.getX();
	double y = player.getY() - this.getY();
	double distance = distanceToPlayer();
	Point2D.Double velocity = new Point2D.Double(maxSpeed * x / distance, maxSpeed * y / distance);
	return velocity;
    }

    private double distanceToPlayer() {
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
	if (distanceToPlayer() < 60) {
	    return true;
	}
	return false;
    }

    @Override public void tick() {
	super.tick();
	if (canSeePlayer()) { // CHASe PLAYER!!
	    Point2D velocity = getVelocityTowardsPlayer();
	    setCurrentVelocity((int) velocity.getX(), (int) velocity.getY());
	} else { // Chill...
	    setCurrentVelocity(0, 0);
	}
    }
}
