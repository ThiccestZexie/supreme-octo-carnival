package se.liu.danal315samak519;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity
{
    private static final String LEVEL_FOLDER = "resources/data/LevelUP/";
    public List<BufferedImage> levelUpFrames = new ArrayList<>();
    private String name;


    public Player(String name, final Point coord, final Color color)
    {
	super(coord, color);
	this.name = name;
	this.level = 1;
	getLevelUpFrames();
    }

    public String getName() {
	return name;
    }

    public void getLevelUpFrames() {
	try {
	    // HARDCODED FOR EXACTLY 20 FRAMES
	    for (int tens = 0; tens < 2; tens++) {
		for (int ones = 0; ones < 10; ones++) {
		    ImageLoader levelUpFrameLoader = new ImageLoader("/levelUP/level_up" + tens + ones + ".png");
		    levelUpFrames.add(levelUpFrameLoader.getImage());
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }


}
