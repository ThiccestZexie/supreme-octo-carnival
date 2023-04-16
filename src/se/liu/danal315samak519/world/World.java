package se.liu.danal315samak519.world;

import com.opencsv.exceptions.CsvException;

import java.awt.*;
import java.io.IOException;

public class World
{
    private static int hardCodedTileHeight = 32;
    private static int hardCodedTileWidth = 32;
    private Tile[][] tiles;
    private TileImageLoader tileImageLoader;
    private IDLoader idLoader;

    public World(final String csvName, final String tileSetImageName){
	// TODO: Remove hardcoded tile sizes
	try {
	    tileImageLoader = new TileImageLoader(tileSetImageName, hardCodedTileWidth, hardCodedTileHeight);
	    idLoader = new IDLoader(csvName);
	    populateTileArray();
	} catch (IOException | CsvException e) {
	    throw new RuntimeException(e);
	}
    }

    private void populateTileArray() throws IOException, CsvException {
	tiles = new Tile[idLoader.getRows()][idLoader.getColumns()];
	for (int y = 0; y < tiles.length; y++) {
	    for (int x = 0; x < tiles[0].length; x++) {
		int id = idLoader.getValueAt(x, y);
		Point tileCoord = new Point(x*hardCodedTileWidth, y*hardCodedTileHeight);
		tiles[y][x] = new Tile(tileImageLoader.getTileImage(id), tileCoord);
	    }
	}
    }

    public Tile getTile(int x, int y) {
	return tiles[y][x];
    }

    public Tile getTile(Point point) {
	return getTile(point.x, point.y);
    }

    public int getRows() {
	return tiles.length;
    }

    public int getColumns() {
	return tiles[0].length;
    }
}
