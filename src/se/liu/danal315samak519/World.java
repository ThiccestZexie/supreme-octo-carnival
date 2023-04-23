package se.liu.danal315samak519;

import java.awt.*;
import java.io.IOException;

public class World
{
    private final int tileHeight;
    private final int tileWidth;
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
	    this.tileWidth = mapLoader.getTileWidth();
	    this.tileHeight = mapLoader.getTileHeight();
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
	return this.rows;
    }

    public int getColumns() {
	return columns;
    }

    public int getLayers() {
	return layers;
    }

    public int getTileWidth() {
	return tileWidth;
    }
    public int getTileHeight() {
	return tileHeight;
    }
}
