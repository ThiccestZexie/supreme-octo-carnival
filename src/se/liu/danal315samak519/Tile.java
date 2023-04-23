package se.liu.danal315samak519;

import java.awt.*;

public class Tile
{
    private Image image;
    private Point point;

    public Tile(final Image image, final Point point) {
	this.image = image;
	this.point = point;
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
	return point.x;
    }
    public int getY(){
	return point.y;
    }
}
