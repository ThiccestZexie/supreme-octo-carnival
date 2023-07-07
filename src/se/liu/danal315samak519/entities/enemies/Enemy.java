package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.ImageLoader;
import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Person;
import se.liu.danal315samak519.entities.Player;
import se.liu.danal315samak519.entities.Potion;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Superclass to all enemies.
 */
public abstract class Enemy extends Person
{
    private static final int SPRITE_OFFSET = 32;
    protected Player player;

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
	final int spriteHeight = 16;
	final int spriteWidth = 16;
	final int totalFrames = 2;
	ImageLoader imageLoader = new ImageLoader("enemies.png");

	downFrames = new BufferedImage[totalFrames];
	for (int i = 0; i < totalFrames; i++) {
	    downFrames[i] = imageLoader.getSubImage(offsetX, offsetY + spriteWidth * (i * 2), spriteWidth, spriteHeight);
	}


	leftFrames = new BufferedImage[totalFrames];
	upFrames = new BufferedImage[totalFrames];
	rightFrames = new BufferedImage[totalFrames];

	for (int i = 0; i < totalFrames; i++) {
	    leftFrames[i] = imageLoader.getSubImage(offsetX + SPRITE_OFFSET - 2, offsetY, spriteWidth, spriteHeight);
	    upFrames[i] = imageLoader.getSubImage(offsetX + (SPRITE_OFFSET - 2) * 2, offsetY, spriteWidth, spriteHeight);
	    rightFrames[i] =
		    imageLoader.getSubImage(offsetX + (SPRITE_OFFSET - 2) * 3, offsetY + SPRITE_OFFSET * i, spriteWidth, spriteHeight);

	}

	setCurrentFrames();
    }

    @Override public void setIsGarbage() {
	super.setIsGarbage();
	player.incrementKillsInRoom();
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


    private double getDistanceToPlayer() {
	return player.getCoord().distance(this.getCoord());
    }

    public boolean isPlayerInFront(int forwardVision, int sideVision) {
	if (canAttack()) {
	    Rectangle rayCastRectangle = new Rectangle();
	    final float half = 2.0f;
	    switch (getDirection()) {
		case UP:
		    rayCastRectangle.setSize(sideVision, forwardVision);
		    rayCastRectangle.setLocation((int) (this.getX() + (this.getWidth() / half) - rayCastRectangle.getWidth() / half),
						 (int) (this.getY() - rayCastRectangle.getHeight()));
		    break;
		case DOWN:
		    rayCastRectangle.setSize(sideVision, forwardVision);
		    rayCastRectangle.setLocation((int) (this.getX() + (this.getWidth() / half) - rayCastRectangle.getWidth() / half),
						 (int) (this.getY() + (this.getHeight())));
		    break;
		case LEFT:
		    rayCastRectangle.setSize(forwardVision, sideVision);
		    rayCastRectangle.setLocation((int) (this.getX() - rayCastRectangle.getWidth()),
						 (int) ((this.getY() + (this.getHeight() / half)) - (rayCastRectangle.getHeight() / half)));
		    break;
		case RIGHT:
		    rayCastRectangle.setSize(forwardVision, sideVision);
		    rayCastRectangle.setLocation((int) (this.getX() + this.getWidth()),
						 (int) ((this.getY() + (this.getHeight() / half)) - (rayCastRectangle.getHeight() / half)));
		    break;
	    }

	    if (rayCastRectangle.intersects(player.getHitBox())) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Draw a health bar under the enemy.
     *
     * @param g
     */
    @Override public void drawHealth(final Graphics g) {
	// Draw background (BLACK)
	g.setColor(Color.BLACK);
	final int fullBarWidth = this.getMaxHp() * 60;
	final int barHeight = 20;
	final int barX = this.getEntityIntX() - fullBarWidth / 2 + this.getIntWidth() / 2;
	final int barY = this.getEntityIntY() + this.getIntHeight() + 15;
	g.fillRect(barX, barY, fullBarWidth, barHeight);

	// Draw red bar, current health
	g.setColor(Color.RED);
	float percentFilled = (float) getHp() / getMaxHp();
	int redBarWidth = (int) (fullBarWidth * percentFilled);
	g.fillRect(barX, barY, redBarWidth, barHeight);
    }

    public void chasePlayer() {
	Point2D.Float velocity = getVelocityTowardsPlayer();
	setVelocity(velocity.x, velocity.y);
    }

    /**
     * All enemies chase player by default. The sentry overrides this.
     */
    public void tick() {
	super.tick();
	chasePlayer();
    }

    /**
     * Enemies should be pushed around by the player, and obstacles, but not potions or their own weapons.
     * They also should only take damage from other persons weapons, not their own.
     * @param movable
     */
    @Override public void interactWith(final Movable movable) {
	Person person = (Person) movable;
	this.nudgeAwayFrom(person.getHitBox());
	person.takeDamage(this.getDamage());
    }
}
