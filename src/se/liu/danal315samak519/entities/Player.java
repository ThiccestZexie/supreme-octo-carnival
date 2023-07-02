package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Decrees;
import se.liu.danal315samak519.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

/**
 * The player character. The player can move around and shoot projectiles. The player can also level up and gain stats. The player can also
 * pick up potions to heal. The player can also pick up decrees to gain buffs.
 */
public class Player extends Character
{
    public BufferedImage[] levelUpFrames = null;
    public Image fullHeart = null, halfHeart = null, emptyHeart = null;

    public Deque<Decrees> decrees = new LinkedList<>();

    public Player(final Point2D.Float coord)
    {
	super(coord);
	setColor(Color.GREEN);
	setMaxSpeed(5);
	setStats(6, 1);
	storeLevelUpFrames();
	storeSpriteFrames();
	storeHealthBars();
	setCurrentFrames();
	levelUp();
    }

    public void applyDecrees() { //Have to manually know effect and apply effect
	for (Decrees decree : decrees) {
	    if (decree.getType() == 0) {
		setMaxSpeed(this.getMaxSpeed() * decree.getIncrease());
	    } else if (decree.getType() == 1) {
		setMaxHP((int) (getMaxHp() + decree.getIncrease()));
		setHp((int) (getHp() + decree.getIncrease()));
	    } else if (decree.getType() == 2) {
		setProjectileWidth((int) (getProjectileWidth() * decree.getIncrease()));

	    } else if (decree.getType() == 3) {
		this.setProjectileVelocity((int) (getProjectileVelocity() * decree.getIncrease()));

	    } else if (decree.getType() == 4) {
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
	return this.ticksAttackCooldown > TICKS_PER_ATTACKFRAME;
    }


    private void storeLevelUpFrames() {
	try {
	    // HARDCODED FOR EXACTLY 20 FRAMES
	    levelUpFrames = new BufferedImage[20];
	    for (int tens = 0; tens < 2; tens++) {
		for (int ones = 0; ones < 10; ones++) {
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
	    int spriteHeight = 16;
	    int spriteWidth = 16;
	    int totalFrames = 3;
	    ImageLoader linkImageLoader = new ImageLoader("link.png");

	    downFrames = new BufferedImage[totalFrames];
	    downFrames[0] = linkImageLoader.getSubImage(0, 0, spriteWidth, spriteHeight);
	    downFrames[1] = linkImageLoader.getSubImage(0, 32, spriteWidth, spriteHeight);
	    downFrames[2] = linkImageLoader.getSubImage(0, 32 * 2, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[totalFrames];
	    leftFrames[0] = linkImageLoader.getSubImage(32, 0, spriteWidth, spriteHeight);
	    leftFrames[1] = linkImageLoader.getSubImage(32, 32, spriteWidth, spriteHeight);
	    leftFrames[2] = linkImageLoader.getSubImage(32, 32 * 2, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[totalFrames];
	    upFrames[0] = linkImageLoader.getSubImage(32 * 2, 0, spriteWidth, spriteHeight);
	    upFrames[1] = linkImageLoader.getSubImage(32 * 2, 32, spriteWidth, spriteHeight);
	    upFrames[2] = linkImageLoader.getSubImage(32 * 2, 32 * 2, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[totalFrames];
	    rightFrames[0] = linkImageLoader.getSubImage(32 * 3, 32, spriteWidth, spriteHeight);
	    rightFrames[1] = linkImageLoader.getSubImage(32 * 3, 0, spriteWidth, spriteHeight);
	    rightFrames[2] = linkImageLoader.getSubImage(32 * 3, 32 * 2, spriteWidth, spriteHeight);

	    setCurrentFrames();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void storeHealthBars() {
	try {
	    int heartWidth = 50, heartHeight = 50;

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

}
