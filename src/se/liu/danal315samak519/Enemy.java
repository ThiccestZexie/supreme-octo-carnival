package se.liu.danal315samak519;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy extends Character
{

    public Enemy(final Point2D.Double coord)
    {
	super(coord);
	storeDirectionalFrames();
	setDir(Direction.DOWN);
    }

    private void storeDirectionalFrames() {
	try {
	    int spriteHeight = 16;
	    int spriteWidth = 16;

	    ImageLoader imageLoader = new ImageLoader("enemies.png");

	    downFrames = new BufferedImage[2];
	    downFrames[0] = imageLoader.getSubImage(0, 0, spriteWidth, spriteHeight);
	    downFrames[1] = imageLoader.getSubImage(0, spriteWidth*2-2, spriteWidth, spriteHeight);

	    leftFrames = new BufferedImage[2];
	    leftFrames[0] = imageLoader.getSubImage(32, 0, spriteWidth, spriteHeight);
	    leftFrames[1] = imageLoader.getSubImage(32, 32, spriteWidth, spriteHeight);

	    upFrames = new BufferedImage[2];
	    upFrames[0] = imageLoader.getSubImage(32 * 2, 0, spriteWidth, spriteHeight);
	    upFrames[1] = imageLoader.getSubImage(32 * 2, 32, spriteWidth, spriteHeight);

	    rightFrames = new BufferedImage[2];
	    rightFrames[0] = imageLoader.getSubImage(32 * 3, 32, spriteWidth, spriteHeight);
	    rightFrames[1] = imageLoader.getSubImage(32 * 3, 0, spriteWidth, spriteHeight);

	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
}
