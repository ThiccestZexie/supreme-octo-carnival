package se.liu.danal315samak519.world;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IDLoader
{
    private int[][] tileIDs;
    private int rows, columns;
    private int tileWidth, tileHeight;

    public IDLoader(final String filename){
	try {
	    String filePath = "resources/data/" + filename;
	    useInfoFromXMLFile(filePath);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public static void main(String[] args){
	IDLoader idLoader = new IDLoader("map0.tmx");
	System.out.println(idLoader.tileHeight);
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
	String csvData = layerElement.selectFirst("data").text();
	CSVReader reader = new CSVReader(new FileReader(csvData));
	setTileIDsFromCSVReader(reader);
    }

    /**
     * Takes a CSVReader object (which is loaded with a file that can be parsed as integer matrix) and returns an integer 2d array.
     *
     * @param reader A CSVReader object
     *
     * @return
     */
    private void setTileIDsFromCSVReader(CSVReader reader) {
	try {
	    List<int[]> rowList = new ArrayList<>();
	    String[] currentLine;
	    while ((currentLine = reader.readNext()) != null) {
		int[] row = new int[currentLine.length];
		for (int i = 0; i < currentLine.length; i++) {
		    row[i] = Integer.parseInt(currentLine[i]);
		}
		rowList.add(row);
	    }
	    tileIDs = rowList.toArray(new int[0][]);
	} catch (IOException | CsvException e) {
	    throw new RuntimeException(e);
	}
    }

    public int getValueAt(int x, int y) {
	return tileIDs[y][x];
    }
    public int getRows(){
	return rows; 
    }
    public int getColumns(){
	return columns;
    }
}
