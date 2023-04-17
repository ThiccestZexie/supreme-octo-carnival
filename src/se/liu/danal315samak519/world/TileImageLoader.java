package se.liu.danal315samak519.world;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileImageLoader
{

    private BufferedImage allTilesImage;
    private int tileWidth, tileHeight;
    private int columns, rows;

    /**
     * Constructs an ImageLoader object that can be used to extract individual tiles from a larger image file.
     *
     * @param fileName   the name of the image file to load (from resources/images/.)
     * @param tileWidth  the width of each tile in the image file
     * @param tileHeight the height of each tile in the image file
     *
     * @throws IOException if there is an error reading the image file
     */
    public TileImageLoader(String fileName, int tileWidth, int tileHeight) throws IOException {
	this.tileWidth = tileWidth;
	this.tileHeight = tileHeight;
	allTilesImage = ImageIO.read(new File("resources/images/" + fileName));

	rows = allTilesImage.getHeight() / getTileHeight();
	columns = allTilesImage.getWidth() / getTileWidth();
    }

    public BufferedImage getTileImage(int x, int y) {
	int tileX = x * tileWidth;
	int tileY = y * tileHeight;
	return allTilesImage.getSubimage(tileX, tileY, tileWidth, tileHeight);
    }

    public BufferedImage getTileImage(int id) {
	int x = id % columns;
	int y = id / rows;

	return getTileImage(x, y);
    }

    public int getColumns() {
	return columns;
    }

    public int getRows() {
	return rows;
    }

    public int getTileWidth() {
	return tileWidth;
    }

    public int getTileHeight() {
	return tileHeight;
    }

}
