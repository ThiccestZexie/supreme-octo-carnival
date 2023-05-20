package se.liu.danal315samak519.map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import se.liu.danal315samak519.ImageLoader;
import se.liu.danal315samak519.entities.Obstacle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads a map file and dispenses tile images as needed.
 */
public class RoomFileLoader
{
    private static final String DATA_FOLDER = "resources/data/";
    private static final String IMAGES_FOLDER = "resources/images/";
    private final int numLayers;
    private int[] tilesetGIDs;
    private Tile[][][] tiles;
    private List<Obstacle> obstacles;
    private int rows, columns;
    private int tileWidth, tileHeight;
    private ImageLoader[] tileSetLoaders;

    /**
     * Loads a map file and dispenses tile images as needed.
     * @param mapFileName The name of the map file to load.
     * @throws IOException If the map file could not be loaded.
     */
    public RoomFileLoader(final String mapFileName) throws IOException {
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
	this.numLayers = Integer.parseInt(mapElement.attr("nextlayerid")) - 1;

	// Handle multiple tilesets
	Elements tileSetElements = doc.select("tileset");
	int numTileSets = tileSetElements.size();
	tilesetGIDs = new int[numTileSets];
	tileSetLoaders = new ImageLoader[numTileSets];
	for (int i = 0; i < numTileSets; i++) {
	    Element element = tileSetElements.get(i);
	    tilesetGIDs[i] = Integer.parseInt(element.attr("firstgid"));
	    // Parse image
	    String tilesetName = element.attr("source");
	    String imageName = findTilesetsImage(tilesetName);
	    tileSetLoaders[i] = new ImageLoader(imageName);
	}

	// Read tile layers (the map)
	Elements layerElements = doc.select("layer");
	this.tiles = new Tile[rows][columns][numLayers];
	for (int l = 0; l < this.numLayers; l++) {
	    Element layerElement = layerElements.get(l);
	    String dataString = layerElement.selectFirst("data").text();
	    String[] dataRows = dataString.split(", ");
	    for (int y = 0; y < rows; y++) {
		String[] rowValues = dataRows[y].split(",");
		for (int x = 0; x < columns; x++) {
		    int value = Integer.parseInt(rowValues[x]);
		    setTile(value, y, x, l);
		}
	    }
	}

	// Read object layers (obstacles, etc..)
	Elements objects = doc.select("objectgroup[id=4] object");
	obstacles = new ArrayList<>();
	for (Element object : objects) {
	    int id = Integer.parseInt(object.attr("id"));
	    float openX = Float.parseFloat(object.attr("x"));
	    float openY = Float.parseFloat(object.attr("y"));
	    float w = Float.parseFloat(object.attr("width"));
	    float h = Float.parseFloat(object.attr("height"));
	    float closedX = getProperty(object, "closedX");
	    float closedY = getProperty(object, "closedY");
	    obstacles.add(new Obstacle(openX, openY, closedX, closedY, w, h, id));
	}
    }
    private float getProperty(Element object, String propertyName) throws IllegalArgumentException {
	Element properties = object.selectFirst("properties");
	if (properties == null) {
	    throw new IllegalArgumentException("Object is missing custom properties (probably endX and endY).");
	}
	Element property = properties.selectFirst("property[name=" + propertyName + "]");
	if (property == null) {
	    throw new IllegalArgumentException("Missing property: " + propertyName);
	}
	String valueStr = property.attr("value");
	try {
	    return Float.parseFloat(valueStr);
	} catch (NumberFormatException e) {
	    throw new IllegalArgumentException("Invalid value for property " + propertyName + ": " + valueStr);
	}
    }

    private void setTile(final int value, int y, int x, int l) {
	if (value != 0) {
	    Point point = new Point(x * tileWidth, y * tileHeight);
	    tiles[y][x][l] = new Tile(getTileImage(value), point);
	} else {
	    tiles[y][x][l] = null;
	}
    }

    /**
     * Reads a .tsx file and returns the source image value
     *
     * @param tilesetName the path to a .tsx file
     *
     * @return the name of the image used
     */
    private String findTilesetsImage(String tilesetName) throws IOException {
	String filePath = DATA_FOLDER + tilesetName;
	File file = new File(filePath);
	Document doc = Jsoup.parse(file, "UTF-8");

	// Get image name from <img> tag
	Element imageElement = doc.selectFirst("img");
	String sourceValue = imageElement.attr("source");
	String imageName = sourceValue.substring(sourceValue.lastIndexOf('/') + 1);

	return imageName;
    }

    /**
     * Takes value from the csv encoding Tiled makes and returns corresponding subimage of tileset.
     *
     * @param value the value of a cell. A value above the current tileset image amount meaning the next tileset should be used.
     *
     * @return an image for value
     */
    public BufferedImage getTileImage(int value) {
	if (value == 0) { // Catch "transparent / empty" tiles from map file
	    return null;
	}
	int index = getTileSetOfValue(value);
	int firstGID = tilesetGIDs[index];
	ImageLoader imageLoader = tileSetLoaders[index];
	int tilesetColumns = imageLoader.getWidth() / getTileWidth();
	int tilesetRows = imageLoader.getHeight() / getTileHeight();
	int col = (value - firstGID) % tilesetColumns;
	int row = (value - firstGID) / tilesetRows;
	int x = col * getTileWidth();
	int y = row * getTileHeight();
	return imageLoader.getSubImage(x, y, getTileWidth(), getTileHeight());
    }

    private int getTileSetOfValue(int value) {
	int index = -1;
	for (int i = 0; i < tilesetGIDs.length; i++) {
	    if (tilesetGIDs[i] > value) {
		break;
	    }
	    index = i;
	}
	return index;
    }

    public List<Obstacle> getObstacles(){
	return obstacles;
    }

    public int getNumLayers() {
	return numLayers;
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

    public Tile[][][] getTiles() {
	return this.tiles;
    }
}
