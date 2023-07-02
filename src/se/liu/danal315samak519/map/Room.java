package se.liu.danal315samak519.map;

import se.liu.danal315samak519.entities.Obstacle;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.module.FindException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

public class Room
{
    private final String fileName;
    private final int tileHeight;
    private final int tileWidth;
    private final int rows, columns, layers;
    private List<Obstacle> obstacles;
    private Tile[][][] tiles; // columns, rows, layers
    private Rectangle insideRoomBounds;

    public Room(final String fileName) {
	try {
	    this.fileName = fileName;
	    RoomFileLoader roomFileLoader = new RoomFileLoader(fileName);
	    this.rows = roomFileLoader.getRows();
	    this.columns = roomFileLoader.getColumns();
	    this.layers = roomFileLoader.getNumLayers();
	    this.tiles = roomFileLoader.getTiles();
	    this.tileWidth = roomFileLoader.getTileWidth();
	    this.tileHeight = roomFileLoader.getTileHeight();
	    setInsideRoomBounds();
	    this.obstacles = roomFileLoader.getObstacles();
	} catch (IOException e) {
	    throw new FindException(e);
	}
    }

    private void setInsideRoomBounds() {
	this.insideRoomBounds = new Rectangle(getTileWidth(), getTileHeight(), getWidth() - getTileWidth(), getHeight() - getTileHeight());
    }

    public Tile getTile(int x, int y, int l) {
	return tiles[y][x][l];
    }

    public Tile getTile(Point point, int layer) {
	return getTile(point.x, point.y, layer);
    }

    public String getName() {
	return this.fileName;
    }

    /**
     * Gets the area that Characters can inhabit in the room.
     */
    public Rectangle getInsideRoomBounds() {
	return insideRoomBounds;
    }


    public int getRows() {
	return this.rows;
    }

    public List<Obstacle> getObstacles() {
	return this.obstacles;
    }

    public int getCenterX() {
	return getWidth() / 2;
    }

    public int getCenterY() {
	return getHeight() / 2;
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

    public int getHeight() {
	return tileHeight * rows;
    }

    public int getWidth() {
	return tileWidth * columns;
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
		if (tile != null) {
		    foregroundTileList.add(tile);
		}
	    }
	}
	return foregroundTileList;
    }
}
