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
    private String name;
    public List<BufferedImage> frames = new ArrayList();
    private static final String LEVEL_FOLDER = "resources/data/LevelUP/";


    public Player(String name, final Point coord, final Color color)
    {
	super(coord, color);
	this.name = name;
	this.level = 1;
	getLevelUpImages();
    }

    public String getName() {
	return name;
    }

    public void getLevelUpImages(){
	try{
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up00.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up01.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up02.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up03.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up04.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up05.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up06.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up07.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up08.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up09.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up10.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up11.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up12.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up13.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up14.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up15.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up16.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up17.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up18.png")));
	frames.add(ImageIO.read(new File(LEVEL_FOLDER + "level_up19.png")));

	}
	catch (IOException e){
	    e.printStackTrace();
	}
    }


}
