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
	this.color = Color.GREEN; //TODO HARDCODED GREEN PLAYER
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

}
