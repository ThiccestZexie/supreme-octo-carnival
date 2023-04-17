package se.liu.danal315samak519;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IDLoader
{
    private BufferedImage tileSetImage;
    private int[][] tileIDs;
    private int rows, columns;
    private int tileWidth, tileHeight;
    private static final String DATA_FOLDER = "resources/data/";
    private static final String IMAGES_FOLDER = "resources/images/";

    public IDLoader(final String fileName) throws IOException {
	String filePath = DATA_FOLDER + fileName;
	readMapFile(filePath);

//	this.tileSetImage = ImageIO.read(new File(IMAGES_FOLDER + imageName));
    }
    /**
     * Reads a .tsx file and returns the source image value
     * @param tileSetPath the path to a .tsx file
     * @return
     */
    private String findImageName(String tileSetPath) throws IOException {
	String filePath = tileSetPath;
	File file = new File(filePath);
	Document doc = Jsoup.parse(file, "UTF-8");

	// Get image name from <img> tag
	Element imageElement = doc.selectFirst("img");
	String sourceValue = imageElement.attr("source");
	String imageName = sourceValue.substring(sourceValue.lastIndexOf('/') + 1);

	return imageName;
    }

    /**
     * Reads these values from a map file
     * - rows, columns, tileWidth, tileHeight
     * - the CSV data
     * - tileset(s)
     * @param filePath the path to a .tmx file to read
     * @throws IOException
     */
    private void readMapFile(final String filePath) throws IOException {
	// Load XML file
	File file = new File(filePath);
	Document doc = Jsoup.parse(file, "UTF-8");

	// Set fields from attributes in file
	Element mapElement = doc.selectFirst("map");
	this.columns = Integer.parseInt(mapElement.attr("width"));
	this.rows = Integer.parseInt(mapElement.attr("height"));
	this.tileWidth = Integer.parseInt(mapElement.attr("tilewidth"));
	this.tileHeight = Integer.parseInt(mapElement.attr("tileheight"));

	// Fill tileIDs using the CSV part of XML file
	Element layerElement = doc.selectFirst("layer");
	String dataString = layerElement.selectFirst("data").text();
	String separator = System.lineSeparator();
	String[] dataRows = dataString.split(", ");

	this.tileIDs = new int[rows][columns];
	for (int y = 0; y < rows; y++) {
	    String[] rowValues = dataRows[y].split(",");
	    for (int x = 0; x < columns; x++) {
		// Subtract 1 from ID to make it zero-indexed
		tileIDs[y][x] = Integer.parseInt(rowValues[x]) - 1;
	    }
	}

	// Handle multiple tilesets
	Elements tileSetElements = doc.select("tileset");
	for (Element tileSetElement : tileSetElements){
	    tileSetElement.text();
	}
    }

    public int getID(int x, int y) {
	return tileIDs[y][x];
    }

    public int getRows() {
	return rows;
    }

    public int getColumns() {
	return columns;
    }

    public int getTileWidth() {
	return tileWidth;
    }

    public int getTileHeight() {
	return tileHeight;
    }
}
