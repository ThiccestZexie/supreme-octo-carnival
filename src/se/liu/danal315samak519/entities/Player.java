package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Decrees;
import se.liu.danal315samak519.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * The player character. The player can move around and shoot projectiles. The player can also level up and gain stats. The player can also
 * pick up potions to heal. The player can also pick up decrees to gain buffs.
 */
public class Player extends Person
{
    private static final int SUB_IMAGE_SIZE = 32;
    public BufferedImage[] levelUpFrames = null;
    public Image fullHeart = null, halfHeart = null, emptyHeart = null;
    public Queue<Decrees> decrees = new LinkedList<>();

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
	    if (Objects.equals(decree.getType(), decree.getDecreeEffects().get(0))) {
		setMaxSpeed(this.getMaxSpeed() * decree.getIncrease());
	    } else if (Objects.equals(decree.getType(), decree.getDecreeEffects().get(1))) {
		setMaxHP((int) (getMaxHp() + decree.getIncrease()));
		setHp((int) (getHp() + decree.getIncrease()));
	    } else if (Objects.equals(decree.getType(), decree.getDecreeEffects().get(2))) {
		setProjectileWidth((int) (getProjectileWidth() * decree.getIncrease()));

	    } else if (Objects.equals(decree.getType(), decree.getDecreeEffects().get(3))) {
		this.setProjectileVelocity((int) (getProjectileVelocity() * decree.getIncrease()));

	    } else if (Objects.equals(decree.getType(), decree.getDecreeEffects().get(4))) {
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
	try {
	    // HARDCODED FOR EXACTLY 20 FRAMES
	    final int amountOfFrames = 20;
	    final int loopsForTen = amountOfFrames / 10;
	    final int loopsforones = amountOfFrames / 2;
	    levelUpFrames = new BufferedImage[amountOfFrames];
	    for (int tens = 0; tens < loopsForTen; tens++) {
		for (int ones = 0; ones < loopsforones; ones++) {
		    ImageLoader levelUpFrameLoader = new ImageLoader("/levelUP/level_up" + tens + ones + ".png");
		    levelUpFrames[10 * tens + ones] = levelUpFrameLoader.getImage();
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void storeSpriteFrames()
    {
	try {
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

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void storeHealthBars() {
	try {
	    final int heartWidth = 50, heartHeight = 50;
	    BufferedImage bufferedImage = ImageIO.read(new File("resources/images/hearts/heart_full.png"));
	    fullHeart = bufferedImage.getScaledInstance(heartWidth, heartHeight, Image.SCALE_DEFAULT);
	    bufferedImage = ImageIO.read(new File("resources/images/hearts/heart_half.png"));
	    halfHeart = bufferedImage.getScaledInstance(heartWidth, heartHeight, Image.SCALE_DEFAULT);
	    bufferedImage = ImageIO.read(new File("resources/images/hearts/heart_blank.png"));
	    emptyHeart = bufferedImage.getScaledInstance(heartWidth, heartHeight, Image.SCALE_DEFAULT);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Display player's health, by drawing hearts in the top left corner of the screen.
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
}
