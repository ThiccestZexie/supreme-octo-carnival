package se.liu.danal315samak519;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Game
{
    public List<Entity> entityList = new ArrayList<>();
    private List<FrameListener> frameListeners = new ArrayList<>();
    private Player player;
    private World world;

    public Game(final Player player) {
	this.player = player;
    }

    public void tick()
    {
	removeGarbage();
	player.tick();
	for (Entity e : entityList) {
	    e.tick();
	}
    }

    public void checkForHits(Entity e)
    {
	for (Entity w : entityList) {
	    if (w instanceof WeaponEntity) {
		WeaponEntity theMurderWeapon = (WeaponEntity) w;
		e.isHit(theMurderWeapon);
	    }
	}
    }

    private void removeGarbage() {
	entityList.removeIf(Entity::getIsGarbage);
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

    public void setPlayerDirection(final Direction dir) {
	player.setDir(dir);
    }

    public void addEnemy(Point coords)
    {
	entityList.add(new Enemy(coords));
    }

    public void addPlayerSword() {
	entityList.add(player.getSword());
	attack();
    }


    public List<Entity> getEntityList() {
	return entityList;
    }

    public void attack() {
	for (Entity e : entityList) {
	    checkForHits(e);
	}
    }

    public World getWorld() {
	return world;
    }

    public void setWorld(final World world) {
	this.world = world;
    }
}
