package se.liu.danal315samak519;

import java.awt.*;
import java.io.IOException;

public class World
{
    private Tile[][] tiles;
    private TileImageLoader tileImageLoader;
    private IDLoader idLoader;

    public World(final String tmxName, final String tileSetName) {
	try {
	    idLoader = new IDLoader(tmxName);
	    tileImageLoader = new TileImageLoader(tileSetName, idLoader.getTileWidth(), idLoader.getTileHeight());
	    populateTileArray();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private void populateTileArray() {
	tiles = new Tile[idLoader.getRows()][idLoader.getColumns()];
	for (int y = 0; y < tiles.length; y++) {
	    for (int x = 0; x < tiles[0].length; x++) {
		int id = idLoader.getID(x, y);
		Point coord = new Point(x * idLoader.getTileWidth(), y * idLoader.getTileHeight());
		tiles[y][x] = new Tile(tileImageLoader.getTileImage(id), coord);
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
