package se.liu.danal315samak519;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity
{
    private static final String LEVEL_FOLDER = "resources/data/LevelUP/";
    private String name;

    public BufferedImage[] levelUpFrames;

    private BufferedImage[] upFrames;
    private BufferedImage[] downFrames;
    private BufferedImage[] leftFrames;
    private BufferedImage[] rightFrames;

    // Keeps track of what frame to display on the sprite
    private int currentDirectionalFrameIndex = 1;


    public Player(String name, final Point coord, final Color color)
    {
	super(coord, color);
	this.name = name;
	this.level = 1;
	storeLevelUpFrames();
	storeDirectionalFrames();
    }

    public String getName() {
	return name;
    }

    private void storeLevelUpFrames() {
	try {
	    // HARDCODED FOR EXACTLY 20 FRAMES
	    levelUpFrames = new BufferedImage[20];
	    for (int tens = 0; tens < 2; tens++) {
		for (int ones = 0; ones < 10; ones++) {
		    ImageLoader levelUpFrameLoader = new ImageLoader("/levelUP/level_up" + tens + ones + ".png");
		    levelUpFrames[10*tens + ones] = levelUpFrameLoader.getImage();
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void storeDirectionalFrames() {
	try {
	    int spriteHeight = 16;
	    int spriteWidth = 16;

	    ImageLoader linkImageLoader = new ImageLoader("link.png");

	    downFrames = new BufferedImage[2];
	    downFrames[0] = linkImageLoader.getSubImage(0,0, spriteWidth, spriteHeight);
	    downFrames[1] = linkImageLoader.getSubImage(0,32, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[2];
	    leftFrames[0] = linkImageLoader.getSubImage(32,0, spriteWidth, spriteHeight);
	    leftFrames[1] = linkImageLoader.getSubImage(32,32, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[2];
	    upFrames[0] = linkImageLoader.getSubImage(32*2,0, spriteWidth, spriteHeight);
	    upFrames[1] = linkImageLoader.getSubImage(32*2,32, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[2];
	    rightFrames[0] = linkImageLoader.getSubImage(32*3,0, spriteWidth, spriteHeight);
	    rightFrames[1] = linkImageLoader.getSubImage(32*3,32, spriteWidth, spriteHeight);

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

}
