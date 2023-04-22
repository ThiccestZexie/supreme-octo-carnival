package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Character
{
    private static final String LEVEL_FOLDER = "resources/data/LevelUP/";
    public BufferedImage[] levelUpFrames;
    private String name;


    public Player(final Point2D.Double coord)
    {
	super(coord);
	this.name = name;
	this.color = Color.GREEN;
	this.level = 1;
	storeLevelUpFrames();
	storeDirectionalFrames();
	setDir(Direction.DOWN);
    }

    public String getName() {
	return name;
    }

    private void storeLevelUpFrames() {
	try {
	    // TODO HARDCODED FOR EXACTLY 20 FRAMES
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

    @Override public void tick() {
	super.tick();
	if(velX == 0 && velY == 0){
	    currentSpriteFrameIndex = 0;
	    ticksCounted = 0;
	}
	else{
	    ticksCounted++;
	    if(ticksCounted > TICKS_PER_FRAME){
		currentSpriteFrameIndex++;
		currentSpriteFrameIndex %= 2;
		ticksCounted = 0;
	    }
	}
    }

    private void storeDirectionalFrames() {
	try {
	    int spriteHeight = 16;
	    int spriteWidth = 16;

	    ImageLoader linkImageLoader = new ImageLoader("link.png");

	    downFrames = new BufferedImage[2];
	    downFrames[0] = linkImageLoader.getSubImage(0, 0, spriteWidth, spriteHeight);
	    downFrames[1] = linkImageLoader.getSubImage(0, 32, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[2];
	    leftFrames[0] = linkImageLoader.getSubImage(32, 0, spriteWidth, spriteHeight);
	    leftFrames[1] = linkImageLoader.getSubImage(32, 32, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[2];
	    upFrames[0] = linkImageLoader.getSubImage(32 * 2, 0, spriteWidth, spriteHeight);
	    upFrames[1] = linkImageLoader.getSubImage(32 * 2, 32, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[2];
	    rightFrames[0] = linkImageLoader.getSubImage(32 * 3, 32, spriteWidth, spriteHeight);
	    rightFrames[1] = linkImageLoader.getSubImage(32 * 3, 0, spriteWidth, spriteHeight);

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

}
