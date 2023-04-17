package se.liu.danal315samak519;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

public class IDLoader
{
    private int[][] tileIDs;
    private int rows, columns;
    private int tileWidth, tileHeight;

    public IDLoader(final String filename) throws IOException {
	String filePath = "resources/data/" + filename;
	useInfoFromXMLFile(filePath);
    }

    private void useInfoFromXMLFile(final String filePath) throws IOException {
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
