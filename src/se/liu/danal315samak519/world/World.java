package se.liu.danal315samak519.world;

import com.opencsv.exceptions.CsvException;

import java.awt.*;
import java.io.IOException;

public class World
{
    private static int hardCodedTileHeight = 32;
    private static int hardCodedTileWidth = 32;
    private Tile[][] tiles = new Tile[hardCodedTileHeight][hardCodedTileWidth];
    private TileImageLoader tileImageLoader;
    private CSVLoader csvLoader;

    public World(final String csvName, final String tileSetImageName){
	// TODO: Remove hardcoded tile sizes
	try {
	    tileImageLoader = new TileImageLoader(tileSetImageName, hardCodedTileWidth, hardCodedTileHeight);
	    csvLoader = new CSVLoader(csvName);
	    populateTileArray();
	} catch (IOException | CsvException e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * Fills the array tileImages with images corresponding to the tiles' id:s found in the CSV file
     *
     * @throws IOException
     * @throws CsvException
     */
    private void populateTileArray() throws IOException, CsvException {
	for (int y = 0; y < tiles.length; y++) {
	    for (int x = 0; x < tiles[0].length; x++) {
		int id = csvLoader.getValueAt(x, y);

		tiles[y][x] = new Tile(tileImageLoader.getTileImage(id), new Point(x, y));
	    }
	}
    }

    public Tile getTile(int x, int y) {
	return tiles[y][x];
    }

    public Tile getTile(Point point) {
	return getTile(point.x, point.y);
    }

    public int getHeight() {
	return tiles.length;
    }

    public int getWidth() {
	return tiles[0].length;
    }
}
