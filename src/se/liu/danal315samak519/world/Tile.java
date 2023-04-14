package se.liu.danal315samak519.world;

import java.awt.*;

public class Tile
{
    private Image image;
    private Point position;

    public Tile(final Image image, final Point position) {
	this.image = image;
	this.position = position;
    }

    public Image getImage() {
	return image;
    }

    public Point getPosition() {
	return position;
    }

    public int getWidth() {
	return image.getWidth(null);
    }

    public int getHeight() {
	return image.getHeight(null);
    }
}
