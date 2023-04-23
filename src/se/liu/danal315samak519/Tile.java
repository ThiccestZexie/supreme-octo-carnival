package se.liu.danal315samak519;

import java.awt.*;

public class Tile
{
    private Image image;
    private Point coord;

    public Tile(final Image image, final Point coord) {
	this.image = image;
	this.coord = coord;
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

    public int getX(){
	return coord.x;
    }
    public int getY() {
	return coord.y;
    }
}
