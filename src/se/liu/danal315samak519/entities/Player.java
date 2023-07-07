package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.DecreeEnums;
import se.liu.danal315samak519.Decrees;
import se.liu.danal315samak519.ImageLoader;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * The player character. The player can move around and shoot projectiles. The player can also level up and gain stats. The player can also
 * pick up potions to heal. The player can also pick up decrees to gain buffs.
 */
public class Player extends Person
{
    private static final int SUB_IMAGE_SIZE = 32;
    public BufferedImage[] levelUpFrames = null;
    public Image fullHeart = null, halfHeart = null, emptyHeart = null;
    public Collection<Decrees> decrees = new LinkedList<>();

    private int killsInRoom = 0;
    public Player(final Point2D.Float coord)
    {
	super(coord);
	setColor(Color.GREEN);
	final int startingSpeed = 5;
	setMaxSpeed(startingSpeed);
	final int startingHealth = 6;
	final int startingLevel = 1;
	setStats(startingHealth, startingLevel);
	storeLevelUpFrames();
	storeSpriteFrames();
	storeHealthBars();
	setCurrentFrames();
	levelUp();
    }

    public void applyDecrees() {
	for (Decrees decree : decrees) {
	    if (Objects.equals(decree.getType(),(DecreeEnums.MOVEMENT_INCREASE))) {
		setMaxSpeed(this.getMaxSpeed() * decree.getIncrease());
	    } else if (Objects.equals(decree.getType(), (DecreeEnums.HEALTH_INCREASE))) {
		setMaxHP((int) (getMaxHp() + decree.getIncrease()));
		setHp((int) (getHp() + decree.getIncrease()));
	    } else if (Objects.equals(decree.getType(), DecreeEnums.ARROW_SIZE_INCREASE)) {
		setProjectileWidth((int) (getProjectileWidth() * decree.getIncrease()));

	    } else if (Objects.equals(decree.getType(), (DecreeEnums.FASTER_ARROWS))) {
		this.setProjectileVelocity((int) (getProjectileVelocity() * decree.getIncrease()));

	    } else if (Objects.equals(decree.getType(), (DecreeEnums.FULL_HEAL))) {
		this.setHp(getMaxHp());
	    }
	    decrees.remove(decree);
	}

    }

    public void addDecree(Decrees d) {
	this.decrees.add(d);
	applyDecrees();
    }

    @Override protected boolean shouldShowAttackFrame() {
	return this.getTicksAttackCooldown() > TICKS_PER_ATTACK_FRAME;
    }


    @SuppressWarnings("StringConcatenationMissingWhitespace") private void storeLevelUpFrames() {
	// HARDCODED FOR EXACTLY 20 FRAMES
	final int amountOfFrames = 20;
	final int loopsForTen = amountOfFrames / 10;
	final int loopsForOnes = amountOfFrames / 2;
	levelUpFrames = new BufferedImage[amountOfFrames];
	for (int tens = 0; tens < loopsForTen; tens++) {
	    for (int ones = 0; ones < loopsForOnes; ones++) {
		ImageLoader levelUpFrameLoader = new ImageLoader("/levelUP/level_up" + tens + ones + ".png");
		levelUpFrames[10 * tens + ones] = levelUpFrameLoader.getImage();
	    }
	}
    }

    private void storeSpriteFrames()
    {
	final int spriteHeight = 16;
	final int spriteWidth = 16;
	final int totalFrames = 3;

	ImageLoader linkImageLoader = new ImageLoader("link.png");

	downFrames = new BufferedImage[totalFrames];
	leftFrames = new BufferedImage[totalFrames];
	upFrames = new BufferedImage[totalFrames];
	rightFrames = new BufferedImage[totalFrames];

	for (int i = 0; i < totalFrames; i++) {
	    downFrames[i] = linkImageLoader.getSubImage(0, SUB_IMAGE_SIZE * i, spriteWidth, spriteHeight);
	    leftFrames[i] = linkImageLoader.getSubImage(SUB_IMAGE_SIZE, SUB_IMAGE_SIZE * i, spriteWidth, spriteHeight);
	    upFrames[i] = linkImageLoader.getSubImage(SUB_IMAGE_SIZE * 2, SUB_IMAGE_SIZE * i, spriteWidth, spriteHeight);
	    rightFrames[i] = linkImageLoader.getSubImage(SUB_IMAGE_SIZE * 3, SUB_IMAGE_SIZE * i, spriteWidth, spriteHeight);
	}
	setCurrentFrames();
    }

    public void storeHealthBars() {
	final int heartWidth = 50, heartHeight = 50;
	BufferedImage bufferedImage = ImageLoader.loadImage("hearts/heart_full.png");
	this.fullHeart = bufferedImage.getScaledInstance(heartWidth, heartHeight, Image.SCALE_DEFAULT);
	bufferedImage = ImageLoader.loadImage("hearts/heart_half.png");
	this.halfHeart = bufferedImage.getScaledInstance(heartWidth, heartHeight, Image.SCALE_DEFAULT);
	bufferedImage = ImageLoader.loadImage("hearts/heart_empty.png");
	this.emptyHeart = bufferedImage.getScaledInstance(heartWidth, heartHeight, Image.SCALE_DEFAULT);
    }

    /**
     * Display player's health, by drawing hearts in the top left corner of the screen.
     *
     * @param g
     */
    @Override public void drawHealth(final Graphics g) {
	int x = 0;
	int y = 0;
	int currentHeart = 0;
	final int pixelsBetweenHearts = 60;
	final int totalHearts = getMaxHp() / 2;
	// Draw all empty hearts
	while (currentHeart < totalHearts) {
	    g.drawImage(emptyHeart, x, y, null);
	    x += pixelsBetweenHearts;
	    currentHeart++;
	}
	x = 0;
	currentHeart = 0;
	while (currentHeart < getHp()) {
	    g.drawImage(halfHeart, x, y, null);
	    currentHeart++;
	    if (currentHeart < getHp()) {
		g.drawImage(fullHeart, x, y, null);
	    }
	    currentHeart++;
	    x += pixelsBetweenHearts;
	}
    }


    public int getKillsInRoom() {
	return killsInRoom;
    }

    public void incrementKillsInRoom() {
	this.killsInRoom++;
    }

    public void setKillsInRoom(final int killsInRoom) {
	this.killsInRoom = killsInRoom;
    }

    @Override public void interactWith(final Movable movable) {
    }
}
