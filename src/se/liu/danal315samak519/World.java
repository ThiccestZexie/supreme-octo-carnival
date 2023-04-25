package se.liu.danal315samak519;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World
{
    private final String name;
    private final int tileHeight;
    private final int tileWidth;
    private List<Zone> zones;
    private Tile[][][] tiles; // columns, rows, layers
    private final int rows, columns, layers;

    public World(final String tmxName) {
	try {
	    this.name = tmxName;
	    MapLoader mapLoader = new MapLoader(tmxName);
	    this.rows = mapLoader.getRows();
	    this.columns = mapLoader.getColumns();
	    this.layers = mapLoader.getLayers();
	    this.tiles = mapLoader.getTiles();
	    this.tileWidth = mapLoader.getTileWidth();
	    this.tileHeight = mapLoader.getTileHeight();
	    this.zones = mapLoader.getZoneList();
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
    public String getName(){
	return this.name;
    }


    public int getRows() {
	return this.rows;
    }

    public List<Zone> getZones(){
	return this.zones;
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

    /**
     * @return a flattened list of the foreground map layer, null elements removed.
     */
    public List<Tile> getForegroundTileList() {
	List<Tile> foregroundTileList = new ArrayList<>();
	int foregroundLayer = 1;

	for (int y = 0; y < getRows(); y++) {
	    for (int x = 0; x < getColumns(); x++) {
		Tile tile = getTile(x, y, foregroundLayer);
		if(tile != null){
		    foregroundTileList.add(tile);
		}
	    }
	}
	return foregroundTileList;
    }
}
