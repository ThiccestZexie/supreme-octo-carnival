package se.liu.danal315samak519.world;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader
{

    private BufferedImage tileset;
    private int tileWidth, tileHeight;

    /**
     * Constructs an ImageLoader object that can be used to extract individual tiles from a larger image file.
     *
     * @param filename   the name or path of the image file to load
     * @param tileWidth  the width of each tile in the image file
     * @param tileHeight the height of each tile in the image file
     *
     * @throws IOException if there is an error reading the image file
     */
    public ImageLoader(String filename, int tileWidth, int tileHeight) throws IOException {
	tileset = ImageIO.read(new File(filename));
	this.tileWidth = tileWidth;
	this.tileHeight = tileHeight;
    }
    public static void main(String[] args) throws IOException {
	int testTileWidth = 32;
	int testTileHeight = 32;
	ImageLoader imageLoader = new ImageLoader("E:\\OneDrive - Linköpings universitet\\Kurser\\TDDE30\\Projekt\\tileset\\TX Tileset Grass.png", testTileWidth, testTileHeight);

	// Test getting the tile image at (0, 0)
	BufferedImage tileImage = imageLoader.getTileAsBufferedImage(0, 0);
	assert tileImage.getWidth() == testTileWidth && tileImage.getHeight() == testTileHeight;

	// Test getting the tile image at (1, 2)
	tileImage = imageLoader.getTileAsBufferedImage(1, 2);
	assert tileImage.getWidth() == testTileWidth && tileImage.getHeight() == testTileHeight;

	// Test getting the tile image at (7, 3)
	tileImage = imageLoader.getTileAsBufferedImage(7, 3);
	assert tileImage.getWidth() == testTileWidth && tileImage.getHeight() == testTileHeight;

	// Test getting the tile image at (9, 6)
	tileImage = imageLoader.getTileAsBufferedImage(9, 6);
	assert tileImage.getWidth() == testTileWidth && tileImage.getHeight() == testTileHeight;

	// Test getting the tile width and height
	assert imageLoader.getTileWidth() == testTileWidth;
	assert imageLoader.getTileHeight() == testTileHeight;

	System.out.println("All tests passed!");
    }


    /**
     * Loads an image from a file path.
     *
     * @param imagePath The file path of the image to load.
     *
     * @return The loaded image.
     * @throws IOException If there is an error loading the image.
     */
    private BufferedImage loadImage(String imagePath) throws IOException {
	return ImageIO.read(getClass().getResource(imagePath));
    }

    public BufferedImage getTileAsBufferedImage(int x, int y) {
	int tileX = x * tileWidth;
	int tileY = y * tileHeight;
	return tileset.getSubimage(tileX, tileY, tileWidth, tileHeight);
    }

    public Image getScaledTile(int x, int y) {
	return getTileAsBufferedImage(x, y).getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH);
    }

    public int getTileWidth() {
	return tileWidth;
    }

    public int getTileHeight() {
	return tileHeight;
    }

}
