package se.liu.danal315samak519.map;

import java.awt.*;

/**
 * A tile in the map.
 */
public class Tile
{
    private Image image;
    private Point point;
    private Rectangle hitBox;

    /**
     * Creates a tile with an image and a point.
     * @param image The image of the tile.
     * @param point The point of the tile.
     */
    public Tile(final Image image, final Point point) {
	this.image = image;
	this.point = point;
	this.hitBox = new Rectangle(point, getSize());
    }

    private Dimension getSize() {
	return new Dimension(getWidth(), getHeight());
    }

    public Rectangle getHitBox() {
	return hitBox;
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
