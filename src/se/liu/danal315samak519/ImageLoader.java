package se.liu.danal315samak519;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader
{
    private BufferedImage image;
    private int width, height;

    public ImageLoader(final String imageName) throws IOException {
	String imagePath = "resources/images/" + imageName;
	this.image = ImageIO.read(new File(imagePath));
	this.width = image.getWidth();
	this.height = image.getHeight();
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

    public BufferedImage getImage(){
	return image;
    }

    public static BufferedImage readImageIO(final String imageName) throws IOException {
	String imagePath = "resources/images/" + imageName;
	return ImageIO.read(new File(imagePath));
    }
}
