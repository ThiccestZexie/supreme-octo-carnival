package se.liu.danal315samak519.world;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVLoader
{
    private CSVReader reader;

    public CSVLoader(final String filename){
	try {
	    reader = new CSVReader(new FileReader("resources/data/"+filename));
	} catch (FileNotFoundException e) {
	    throw new RuntimeException(e);
	}
    }

    public int getValueAt(int column, int row) throws CsvException, IOException {
	int currentRow = 0;
	String[] currentLine;
	while((currentLine = reader.readNext()) != null){
	    if(currentRow == row){
		reader.close();
		return Integer.parseInt(currentLine[column]); // Parse the desired value as integer
	    }
	}
	throw new CsvException("The specified row is outside of CSV range.");
    }
}
