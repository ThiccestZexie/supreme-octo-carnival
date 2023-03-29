package se.liu.danal315samak519;

import java.util.ArrayList;
import java.util.List;


public class Game
{
    private List<FrameListener> frameListeners = new ArrayList<>();
    private GameMap map;
    private GamePlayer player;
    private GameInputHandler gameInputHandler;

    public Game(final GameMap map, final GamePlayer player) {
	this.map = map;
	this.player = player;
    }

    public void tick(){
	player.tick();
    }

    public void addPlayer(final GamePlayer player) {
   	 this.player = player;
    }

    public GamePlayer getPlayer(){
	return this.player;
    }

    public void addFrameListener(FrameListener fl)
    {
    	frameListeners.add(fl);
    }

    public void notifyListeners(){
	for (FrameListener fl: frameListeners) {
	    fl.frameChanged();
	}
    }
    public void nudgePlayer(final int dx, final int dy) {
	player.nudge(dx, dy);
	notifyListeners();
    }

    public void movePlayerTo(final int x, final int y) {
	player.moveTo(x, y);
	notifyListeners();
    }

    public int getPlayerVelocityX(){
	return player.getVelocityX();
    }
    public int getPlayerVelocityY(){
	return player.getVelocityY();
    }

    public void setPlayerVelocity(final int vx, final int vy) {
	player.setVelocity(vx, vy);
    }
}
