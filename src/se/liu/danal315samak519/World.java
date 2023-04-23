package se.liu.danal315samak519;

import java.awt.*;
import java.io.IOException;

public class World
{
    // columns, rows, layers
    private Tile[][][] tiles;
    private int rows, columns, layers;

    public World(final String tmxName) {
	try {
	    MapLoader mapLoader = new MapLoader(tmxName);
	    this.rows = mapLoader.getRows();
	    this.columns = mapLoader.getColumns();
	    this.layers = mapLoader.getLayers();
	    this.tiles = mapLoader.getTiles();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
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
