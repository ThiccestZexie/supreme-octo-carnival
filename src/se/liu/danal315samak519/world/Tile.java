package se.liu.danal315samak519.world;

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

    public Point getCoord() {
	return coord;
    }

    public int getWidth() {
	return image.getWidth(null);
    }

    public int getHeight() {
	return image.getHeight(null);
    }
}
