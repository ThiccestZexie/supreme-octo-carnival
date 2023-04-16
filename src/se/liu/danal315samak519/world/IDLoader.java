package se.liu.danal315samak519.world;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IDLoader
{
    private int[][] tileIDs;

    public IDLoader(final String filename) throws FileNotFoundException {
	CSVReader reader = new CSVReader(new FileReader("resources/data/" + filename));
	setTileIDsFromReader(reader);
    }

    /**
     * Takes a CSVReader object (which is loaded with a file that can be parsed as integer matrix) and returns an integer 2d array.
     *
     * @param reader A CSVReader object
     *
     * @return
     */
    private void setTileIDsFromReader(CSVReader reader) {
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
	return tileIDs.length;
    }
    public int getColumns(){
	return tileIDs[0].length;
    }
}
