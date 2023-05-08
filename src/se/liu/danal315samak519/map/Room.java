package se.liu.danal315samak519.map;

import se.liu.danal315samak519.entities.Obstacle;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Room
{
    private final String fileName;
    private final int tileHeight;
    private final int tileWidth;
    private List<Obstacle> obstacles;
    private Tile[][][] tiles; // columns, rows, layers
    private final int rows, columns, layers;

    public Room(final String fileName) {
	try {
	    this.fileName = fileName;
	    MapLoader mapLoader = new MapLoader(fileName);
	    this.rows = mapLoader.getRows();
	    this.columns = mapLoader.getColumns();
	    this.layers = mapLoader.getLayers();
	    this.tiles = mapLoader.getTiles();
	    this.tileWidth = mapLoader.getTileWidth();
	    this.tileHeight = mapLoader.getTileHeight();
	    this.obstacles = mapLoader.getObstacles();
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
	return this.fileName;
    }


    public int getRows() {
	return this.rows;
    }

    public List<Obstacle> getObstacles(){
	return this.obstacles;
    }

    public int getCenterX(){
	return getWidth()/2;
    }

    public int getCenterY(){
	return getHeight()/2;
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
    public int getHeight(){
	return tileHeight*rows;
    }

    public int getWidth(){
	return tileWidth*columns;
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
