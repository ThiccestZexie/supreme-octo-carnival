package se.liu.danal315samak519;

import java.awt.geom.Point2D;
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

    public void checkForHits(Character e)
    {
	for (Entity entity : entityList) {
	    if (entity instanceof Weapon) {
		Weapon theMurderWeapon = (Weapon) entity;
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

    public void addEnemy(Point2D.Double coord)
    {
	addEntity(new Enemy(coord));
    }

    public void addPlayerSword() {
	addEntity(player.getSword());
	checkIfAnyEntityHit();
    }


    public List<Entity> getEntityList() {
	return entityList;
    }

    public void checkIfAnyEntityHit() {
	for (Entity entity : entityList) {
	    if (entity instanceof Character) {
		checkForHits((Character) entity);
	    }
	}
    }

    public World getWorld() {
	return world;
    }

    public void setWorld(final World world) {
	this.world = world;
    }

    public void addEnemy(final double x, final double y) {
	addEnemy(new Point2D.Double(x, y));
    }

    private void addEntity(final Entity entity) {
	entityList.add(entity);
    }
}
