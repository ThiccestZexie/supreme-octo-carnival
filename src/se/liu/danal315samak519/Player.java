package se.liu.danal315samak519;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up00.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up01.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up02.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up03.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up04.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up05.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up06.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up07.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up08.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up09.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up10.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up11.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up12.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up13.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up14.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up15.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up16.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up17.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up18.png")));
	    levelUpFrames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up19.png")));

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }


}
