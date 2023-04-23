package se.liu.danal315samak519;

import java.awt.*;
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
	handleWallCollisions();
	for (Entity e : entityList) {
	    e.tick();
	}
    }

    /**
     * Iterates through all foreground tiles and makes sure no entites can pass through them
     */
    private void handleWallCollisions() {
	for (Tile tile : world.getForegroundTileList()) {
	    Rectangle tileHitBox = new Rectangle(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
	    if (player.getHitBox().intersects(tileHitBox)){
		Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		Point2D to = new Point2D.Double(player.getHitBox().getCenterX(), player.getHitBox().getCenterY());
		Direction pushBackDirection = DirectionUtils.directionBetweenPoints(from, to);
		player.nudge(pushBackDirection.getX(), pushBackDirection.getY());
		player.setCurrentVelocity(0,0);
	    }
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
	for (FrameListener frameListener : frameListeners) {
	    frameListener.frameChanged();
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
	addEntity(new Enemy(coord, player));
    }

    public void playerAttack() {
	player.becomeAttacking();
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
