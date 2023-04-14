package se.liu.danal315samak519.world;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Test
{
    public static void main(String[] args) {
	try{
	    FileReader fileReader = new FileReader("E:\\OneDrive - Linköpings universitet\\Kurser\\TDDE30\\Projekt\\maps\\map1.csv");
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }
}
