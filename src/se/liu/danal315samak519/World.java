package se.liu.danal315samak519;

import java.awt.*;
import java.io.IOException;

public class World
{
    private Tile[][] tiles;

    public World(final String tmxName) {
	try {
	    MapLoader mapLoader = new MapLoader(tmxName);
	    tiles = new Tile[mapLoader.getRows()][mapLoader.getColumns()];
	    for (int y = 0; y < tiles.length; y++) {
		for (int x = 0; x < tiles[0].length; x++) {
		    int id = mapLoader.getID(x, y);
		    Point coord = new Point(x * mapLoader.getTileWidth(), y * mapLoader.getTileHeight());
		    tiles[y][x] = new Tile(mapLoader.getTileImage(id), coord);
		}
	    }
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private void populateTileArray() {
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
