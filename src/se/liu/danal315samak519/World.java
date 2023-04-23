package se.liu.danal315samak519;

import java.awt.*;
import java.io.IOException;

public class World
{
    // columns, rows, layers
    private Tile[][][] tiles;

    public World(final String tmxName) {
	try {
	    MapLoader mapLoader = new MapLoader(tmxName);
	    int rows = mapLoader.getRows();
	    int columns = mapLoader.getColumns();
	    int layers = mapLoader.getLayers();
	    tiles = new Tile[rows][columns][layers];
	    for (int y = 0; y < rows; y++) {
		for (int x = 0; x < columns; x++) {
		    for (int l = 0; l < layers; l++) {
			int id = mapLoader.getID(x, y, l);
			Point coord = new Point(x * mapLoader.getTileWidth(), y * mapLoader.getTileHeight());
			tiles[y][x][l] = new Tile(mapLoader.getTileImage(id), coord);
		    }
		}
	    }
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private void populateTileArray() {
    }

    public Tile getTile(int x, int y, int l) {
	return tiles[y][x][l];
    }

    public Tile getTile(Point point, int layer) {
	return getTile(point.x, point.y, layer);
    }

    public int getRows() {
	return tiles.length;
    }

    public int getColumns() {
	return tiles[0].length;
    }
}
