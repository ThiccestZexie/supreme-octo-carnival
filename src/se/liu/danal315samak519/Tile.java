package se.liu.danal315samak519;

import java.awt.*;

public class Tile
{
    private Image image;
    private Point coord;

    public Tile(final Image image) {
	this.image = image;
    }

    public Image getImage() {
	return image;
    }

    public int getWidth() {
	if(image == null){
	    return 0;
	}
	return image.getWidth(null);
    }

    public int getHeight() {
	if(image == null){
	    return 0;
	}
	return image.getHeight(null);
    }
}
