package se.liu.danal315samak519.world;

import java.awt.*;

public class World
{
    private Tile[][] tiles;

    public int getHeight(){
	return tiles.length;
    }

    public int getWidth(){
	return tiles[0].length;
    }

    public Tile getTileAt(int x, int y){
	return tiles[y][x];
    }

    public Tile getTileAt(Point point){
	return getTileAt(point.x, point.y);
    }
}
