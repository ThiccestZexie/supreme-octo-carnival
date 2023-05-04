package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.ImageLoader;
import se.liu.danal315samak519.Status;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Character
{
    public int skillPoints = 0;
    public BufferedImage[] levelUpFrames;
    public Image fullHeart = null, halfHeart = null, emptyHeart = null;

    public Player(final Point2D.Double coord)
    {
	super(coord);
	this.color = Color.GREEN; //TODO HARDCODED GREEN PLAYER
	setMaxSpeed(4);
	setStats(6,1);
	storeLevelUpFrames();
	storeSpriteFrames();
	storeHealthBars();
	setDir(Direction.DOWN);
	setFramesBasedOnDirection();
    }

    @Override public void levelUp() {
	super.levelUp();
	skillPoints++;
    }

    private void storeLevelUpFrames() {
	try {
	    // TODO HARDCODED FOR EXACTLY 20 FRAMES
	    levelUpFrames = new BufferedImage[20];
	    for (int tens = 0; tens < 2; tens++)
	    {
		for (int ones = 0; ones < 10; ones++)
		{
		    ImageLoader levelUpFrameLoader = new ImageLoader("/levelUP/level_up" + tens + ones + ".png");
		    levelUpFrames[10 * tens + ones] = levelUpFrameLoader.getImage();
		}
	    }
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

    private void storeSpriteFrames()
    {
	try {
	    int spriteHeight = 16;
	    int spriteWidth = 16;

	    ImageLoader linkImageLoader = new ImageLoader("link.png");

	    downFrames = new BufferedImage[3];
	    downFrames[0] = linkImageLoader.getSubImage(0, 0, spriteWidth, spriteHeight);
	    downFrames[1] = linkImageLoader.getSubImage(0, 32, spriteWidth, spriteHeight);
	    downFrames[2] = linkImageLoader.getSubImage(0, 32 * 2, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[3];
	    leftFrames[0] = linkImageLoader.getSubImage(32, 0, spriteWidth, spriteHeight);
	    leftFrames[1] = linkImageLoader.getSubImage(32, 32, spriteWidth, spriteHeight);
	    leftFrames[2] = linkImageLoader.getSubImage(32, 32 * 2, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[3];
	    upFrames[0] = linkImageLoader.getSubImage(32 * 2, 0, spriteWidth, spriteHeight);
	    upFrames[1] = linkImageLoader.getSubImage(32 * 2, 32, spriteWidth, spriteHeight);
	    upFrames[2] = linkImageLoader.getSubImage(32 * 2, 32 * 2, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[3];
	    rightFrames[0] = linkImageLoader.getSubImage(32 * 3, 32, spriteWidth, spriteHeight);
	    rightFrames[1] = linkImageLoader.getSubImage(32 * 3, 0, spriteWidth, spriteHeight);
	    rightFrames[2] = linkImageLoader.getSubImage(32 * 3, 32 * 2, spriteWidth, spriteHeight);

	} catch (IOException e)
	{
	    throw new RuntimeException(e);
	}
    }

    public void storeHealthBars(){
	try
	{
	    BufferedImage bufferedImage = ImageIO.read(new File("resources/images/hearts/heart_full.png"));
	    fullHeart = bufferedImage.getScaledInstance(50,50,Image.SCALE_DEFAULT);
	    bufferedImage = ImageIO.read(new File("resources/images/hearts/heart_half.png"));
	    halfHeart = bufferedImage.getScaledInstance(50,50,Image.SCALE_DEFAULT);
	    bufferedImage = ImageIO.read(new File("resources/images/hearts/heart_blank.png"));
	    emptyHeart = bufferedImage.getScaledInstance(50,50,Image.SCALE_DEFAULT);
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
    public void takeDamage(){
	if (this.hp > 0 && this.getStatus() != Status.HIT){
	    hp--;
	    this.setStatus(Status.HIT);
	    iFramesTimer.start();
	}
    }
}
