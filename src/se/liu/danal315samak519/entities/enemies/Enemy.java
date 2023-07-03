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

/**
 * Superclass to all enemies.
 */
public abstract class Enemy extends Character
{
    private static final int SPRITE_OFFSET = 32;
    protected Player player;
    protected int damage = 1;

    protected Enemy(final Point2D.Float coord, final Player player)
    {
	super(coord);
	this.player = player;
	setMaxSpeed(2);
    }

    /**
     * Takes in an offset and uses that to index the spritesheet from enemies.png. Stores the frames in the Enemy's frames variables.
     *
     * @param offsetX
     * @param offsetY
     */
    protected void storeSpriteFrames(int offsetX, int offsetY) {
	try {
	    final int spriteHeight = 16;
	    final int spriteWidth = 16;

	    ImageLoader imageLoader = new ImageLoader("enemies.png");

	    downFrames = new BufferedImage[3];
	    downFrames[0] = imageLoader.getSubImage(offsetX, offsetY, spriteWidth, spriteHeight);
	    downFrames[1] = imageLoader.getSubImage(offsetX, offsetY + spriteWidth * 2 - 2, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[3];
	    leftFrames[0] = imageLoader.getSubImage(offsetX + SPRITE_OFFSET - 2, offsetY, spriteWidth, spriteHeight);
	    leftFrames[1] = imageLoader.getSubImage(offsetX + SPRITE_OFFSET - 2, offsetY + SPRITE_OFFSET, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[3];
	    upFrames[0] = imageLoader.getSubImage(offsetX + (SPRITE_OFFSET - 2) * 2, offsetY, spriteWidth, spriteHeight);
	    upFrames[1] = imageLoader.getSubImage(offsetX + (SPRITE_OFFSET - 2) * 2, offsetY + SPRITE_OFFSET, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[3];
	    rightFrames[0] = imageLoader.getSubImage(offsetX + (SPRITE_OFFSET - 2) * 3, offsetY + SPRITE_OFFSET, spriteWidth, spriteHeight);
	    rightFrames[1] = imageLoader.getSubImage(offsetX + (SPRITE_OFFSET - 2) * 3, offsetY, spriteWidth, spriteHeight);

	    setCurrentFrames();

	} catch (IOException e) {
	   e.printStackTrace();
	}
    }

    @Override public void markAsGarbage() {
	super.markAsGarbage();
	Movable dropped = getDropped();
	if (dropped != null) {
	    pushPending(dropped);
	}
    }

    /**
     * Drop a potion 50% of the time
     *
     * @return a potion or null
     */
    private Movable getDropped() {
	boolean shouldDrop = getRandom().nextBoolean();
	if (shouldDrop) {
	    return new Potion(this.coord);
	}
	return null;
    }

    public Point2D.Float getVelocityTowardsPlayer() {
	float x = player.getX() - this.getX();
	float y = player.getY() - this.getY();
	float distance = (float) getDistanceToPlayer();
	Point2D.Float velocity = new Point2D.Float(maxSpeed * x / distance, maxSpeed * y / distance);
	return velocity;
    }

    public int getDamage() {
	return damage;
    }

    protected void setDamage(int damage) {
	this.damage = damage;
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


    public boolean checkIfPlayerIsInFront(int fowardVision, int sideVision) {
	if (canAttack()) {
	    Rectangle raycastRectangle = new Rectangle();
	    final int half = 2;
	    switch (getDirection()) {
		case UP:
		    raycastRectangle.setSize(sideVision, fowardVision);
		    raycastRectangle.setLocation((int) (this.getX() + (this.getWidth() / half) - raycastRectangle.getWidth() / half),
						 (int) (this.getY() - raycastRectangle.getHeight()));
		    break;
		case DOWN:
		    raycastRectangle.setSize(sideVision, fowardVision);
		    raycastRectangle.setLocation((int) (this.getX() + (this.getWidth() / half) - raycastRectangle.getWidth() / half),
						 (int) (this.getY() + (this.getHeight())));
		    break;
		case LEFT:
		    raycastRectangle.setSize(fowardVision,  sideVision );
		    raycastRectangle.setLocation((int) (this.getX() - raycastRectangle.getWidth()),
						 (int) ((this.getY() + (this.getHeight() / half)) - (raycastRectangle.getHeight() / half)));
		    break;
		case RIGHT:
		    raycastRectangle.setSize(fowardVision, sideVision);
		    raycastRectangle.setLocation((int) (this.getX() + this.getWidth()),
						 (int) ((this.getY() + (this.getHeight() / half)) - (raycastRectangle.getHeight() / half)));
		    break;
	    }

	    if (raycastRectangle.intersects(player.getHitBox())) {
		return true;
	    }
	}
	return false;
    }

    @Override public void drawHealth(final Graphics g) {
	super.drawHealth(g);
	g.setColor(Color.BLACK);
	final int blackWidth = this.getMaxHp() * 60;
	final int blackHeight = 20;
	final int blackX = this.getEntityIntX() - blackWidth / 2 + this.getIntWidth() / 2;
	final int blackY = this.getEntityIntY() + this.getIntHeight() + 15;
	g.fillRect(blackX, blackY, blackWidth, blackHeight); // Should be getInt maxHp

	//Current health (RED)
	final int redWidth = this.getHp() * 60;
	final int redHeight = 20;
	final int yCoord = this.getEntityIntY() + this.getIntHeight() + 15;
	final int xCoord = (this.getEntityIntX() - blackWidth / 2) + (this.getIntWidth() / 2);
	g.setColor(Color.RED);
	g.fillRect(xCoord, yCoord,
		   redWidth, redHeight);
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
