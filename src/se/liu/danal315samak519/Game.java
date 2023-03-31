package se.liu.danal315samak519;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Game
{
    public List<Entity> entityList = new ArrayList<>();
    private List<FrameListener> frameListeners = new ArrayList<>();
    private Map map;
    private Player player;

    public Game(final Player player) {
	this.player = player;
    }

    public void tick() {
	player.tick();
    }

    public void addPlayer(final Player player) {
	if (player != null) {
	    throw new IllegalStateException("Cannot add player, already one present!");
	}
	this.player = player;
    }

    public Player getPlayer() {
	return this.player;
    }

    public void addFrameListener(FrameListener fl)
    {
	frameListeners.add(fl);
    }

    public void notifyListeners() {
	for (FrameListener fl : frameListeners) {
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

    public int getPlayerVelX() {
	return player.getVelX();
    }

    public void setPlayerVelX(final int vx) {
	setPlayerVelocity(vx, getPlayerVelY());
    }

    public int getPlayerVelY() {
	return player.getVelY();
    }

    public void setPlayerVelY(final int vy) {
	setPlayerVelocity(getPlayerVelX(), vy);
    }

    public void setPlayerVelocity(final int vx, final int vy) {
	player.setCurrentVelocity(vx, vy);
    }

    public void addEnemy(Point coords)
    {
	entityList.add(new Enemy(coords));
    }

    public List<Entity> getEntityList() {
	return entityList;
    }
}
