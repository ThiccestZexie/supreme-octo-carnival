package se.liu.danal315samak519;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Loads images from the resources/images folder. Allows for easy access manipulation the loaded image.
 */
public class ImageLoader
{
    private final int width, height;
    private BufferedImage image;

    /**
     * Loads an image from the resources/images folder.
     *
     * @param imageName The name of the image to load.
     *
     * @throws IOException If the image could not be loaded.
     */
    public ImageLoader(final String imageName) {
	this.image = loadImage(imageName);
	this.width = image.getWidth();
	this.height = image.getHeight();
    }

    /**
     * Loads an image from the resources/images folder.
     *
     * @param imageName The name of the image to load. Must be in the resources/images folder.
     *
     * @return The loaded image. Null if the image could not be loaded.
     */
    public static BufferedImage loadImage(final String imageName) {
	BufferedImage image = null;
	String imagePath = "resources/images/" + imageName;
	try {
	    image = ImageIO.read(new File(imagePath));
	} catch (IOException ignored) {
	    System.err.println("Failed to locate image with path: " + imagePath);
	}
	return image;
    }
    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public BufferedImage getSubImage(int x, int y, int width, int height) {
	return image.getSubimage(x, y, width, height);
    }

    public BufferedImage getImage() {
	return image;
    }
}
