package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class Game
{
    public List<MovableEntity> movableEntityList = new ArrayList<>();
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
	for (MovableEntity e : movableEntityList) {
	    e.tick();
	}
    }

    /**
     * Iterates through all foreground tiles and makes sure no entites can pass through them
     */
    private void handleWallCollisions() {
	for (Tile tile : world.getForegroundTileList()) {
	    Rectangle tileHitBox = new Rectangle(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
	    if (player.getHitBox().intersects(tileHitBox)) {
		Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		Point2D to = new Point2D.Double(player.getHitBox().getCenterX(), player.getHitBox().getCenterY());
		Direction pushBackDirection = DirectionUtil.getDirectionBetweenPoints(from, to);
		int pushBackAmount = 5;
		player.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
		player.setVelocity(0, 0);
	    }

	    for (MovableEntity movableEntity : movableEntityList) {
		if (movableEntity.getHitBox().intersects(tileHitBox)) {
		    Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		    Point2D to = new Point2D.Double(movableEntity.getHitBox().getCenterX(), movableEntity.getHitBox().getCenterY());
		    Direction pushBackDirection = DirectionUtil.getDirectionBetweenPoints(from, to);
		    int pushBackAmount = 1;
		    movableEntity.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
		    movableEntity.setVelocity(0, 0);
		}
	    }
	}
    }

    public void checkForHits(Character e)
    {
	for (MovableEntity movableEntity : movableEntityList) {
	    if (movableEntity instanceof Weapon) {
		Weapon theMurderWeapon = (Weapon) movableEntity;
		e.isHit(theMurderWeapon);
	    }
	}
    }

    private void removeGarbage() {
	movableEntityList.removeIf(MovableEntity::getIsGarbage);
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

    public void addEnemy(Point2D.Double coord)
    {
	addEntity(new Enemy(coord, player));
    }

    public void playerAttack() {
	player.becomeAttacking();
	addEntity(player.getSword());
	checkIfAnyEntityHit();
    }

    public List<MovableEntity> getEntityList() {
	return movableEntityList;
    }

    public void checkIfAnyEntityHit() {
	for (MovableEntity movableEntity : movableEntityList) {
	    if (movableEntity instanceof Character) {
		checkForHits((Character) movableEntity);
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

    private void addEntity(final MovableEntity movableEntity) {
	movableEntityList.add(movableEntity);
    }

}
