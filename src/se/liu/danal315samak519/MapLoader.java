package se.liu.danal315samak519;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapLoader
{
    private static final String DATA_FOLDER = "resources/data/";
    private static final String IMAGES_FOLDER = "resources/images/";
    private int[] tilesetGIDs;
    private int[][] tileIDs;
    private int rows, columns;
    private int tileWidth, tileHeight;
    private BufferedImage[] tilesetImages;

    private Tile[][] tiles;

    /**
     * Loads a map file and dispenses tile images as needed.
     *
     * @param mapFileName the name of the .tmx file containing the map data
     *
     * @throws IOException
     */
    public MapLoader(final String mapFileName) throws IOException {
	// Load XML file
	String filePath = DATA_FOLDER + mapFileName;
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
		tileIDs[y][x] = Integer.parseInt(rowValues[x]);
	    }
	}

	// Handle multiple tilesets
	Elements tileSetElements = doc.select("tileset");
	int numTileSets = tileSetElements.size();
	tilesetGIDs = new int[numTileSets];
	tilesetImages = new BufferedImage[numTileSets];
	for (int i = 0; i < numTileSets; i++) {
	    Element element = tileSetElements.get(i);
	    tilesetGIDs[i] = Integer.parseInt(element.attr("firstgid"));
	    // Parse image
	    String tilesetName = element.attr("source");
	    String imagePath = findImagePath(tilesetName);
	    tilesetImages[i] = ImageIO.read(new File(imagePath));
	}

	// Fill tiles array
	tiles = new Tile[rows][columns];
	for (int y = 0; y < rows; y++) {
	    for (int x = 0; x < columns; x++) {
		int id = this.getID(x, y);
		Point coord = new Point(x * getTileWidth(), y * getTileHeight());
		tiles[y][x] = new Tile(getTileImage(id), coord);
	    }
	}
    }

    public static void main(String[] args) throws IOException {
	MapLoader mapLoader = new MapLoader("map0.tmx");
    }

    /**
     * Reads a .tsx file and returns the source image value
     *
     * @param tilesetName the path to a .tsx file
     *
     * @return
     */
    private String findImagePath(String tilesetName) throws IOException {
	String filePath = DATA_FOLDER + tilesetName;
	File file = new File(filePath);
	Document doc = Jsoup.parse(file, "UTF-8");

	// Get image name from <img> tag
	Element imageElement = doc.selectFirst("img");
	String sourceValue = imageElement.attr("source");
	String imageName = sourceValue.substring(sourceValue.lastIndexOf('/') + 1);

	return IMAGES_FOLDER + imageName;
    }

    /**
     * Reads these values from a map file - rows, columns, tileWidth, tileHeight - the CSV data - tileset(s)
     *
     * @param filePath the path to a .tmx file to read
     *
     * @throws IOException
     */
    private void readMapFile(final String filePath) throws IOException {
    }

    /**
     * @param id the value of a cell. A value above the current tileset image amount meaning the next tileset should be used.
     *
     * @return
     */
    public BufferedImage getTileImage(int id) {
	int index = getIndexOf(id);
	int firstGID = tilesetGIDs[index];
	BufferedImage image = tilesetImages[index];
	int tilesetColumns = image.getWidth() / getTileWidth();
	int tilesetRows = image.getHeight() / getTileHeight();
	int col = (id - firstGID) % tilesetColumns;
	int row = (id - firstGID) / tilesetRows;
	return getSubImageOfTileSet(col, row, image);
    }

    private int getIndexOf(int id) {
	int index = -1;
	for (int i = 0; i < tilesetGIDs.length; i++) {
	    if (tilesetGIDs[i] > id) {
		break;
	    }
	    index = i;
	}
	return index;
    }

    private BufferedImage getSubImageOfTileSet(int col, int row, BufferedImage image) {
	int x = col * tileWidth;
	int y = row * tileHeight;
	return image.getSubimage(x, y, tileWidth, tileHeight);
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
