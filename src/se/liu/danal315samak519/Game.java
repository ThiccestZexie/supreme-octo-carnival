package se.liu.danal315samak519;

import se.liu.danal315samak519.map.Obstacle;
import se.liu.danal315samak519.map.Tile;
import se.liu.danal315samak519.map.World;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;


public class Game
{
    public List<Movable> movables = new ArrayList<>();
    private List<FrameListener> frameListeners = new ArrayList<>();
    private Player player;
    private World world;
    private int currentWorldID = 0;

    public void tick()
    {
	removeGarbage();
	player.tick();
	handleWallCollisions();
	handleObstacleCollisions();
	if (playerIsOutOfBounds()) {
	    changeToNextWorld();
	}
	checkForCollisionHits();
	for (Movable movable : movables) {
	    movable.tick();
	}
    }

    private void centerPlayerLocation() {
	player.setLocation(getWorld().getCenterX() - player.getWidth() / 2, getWorld().getCenterY() - player.getHeight() / 2);
    }

    private void changeToNextWorld() {
	currentWorldID++;
	setWorld(new World("map" + currentWorldID + ".tmx"));
	centerPlayerLocation();
    }

    private boolean playerIsOutOfBounds() {
	boolean above = player.getY() < 0;
	boolean below = player.getY() > world.getHeight();
	boolean left = player.getX() < 0;
	boolean right = player.getX() > world.getWidth();
	return above || below || left || right;
    }

    /**
     * Makes sure no movableEntities pass through foreground tiles
     */
    private void handleWallCollisions() {
	if (getWorld().getLayers() < 2) {
	    return; // No wall collision to handle, there are only background layers!
	}

	for (Tile tile : world.getForegroundTileList()) {
	    Rectangle tileHitBox = new Rectangle(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
	    // Handle player-wall collision
	    if (player.getHitBox().intersects(tileHitBox)) {
		Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		Point2D to = new Point2D.Double(player.getHitBox().getCenterX(), player.getHitBox().getCenterY());
		Direction pushBackDirection = Direction.getDirectionBetweenPoints(from, to);
		int pushBackAmount = 5;
		player.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
		player.setVelocity(0, 0);
	    }

	    // Handle movable-wall collision
	    for (Movable movable : movables) {
		if (movable.getHitBox().intersects(tileHitBox)) {
		    Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		    Point2D to = new Point2D.Double(movable.getHitBox().getCenterX(), movable.getHitBox().getCenterY());
		    Direction pushBackDirection = Direction.getDirectionBetweenPoints(from, to);
		    int pushBackAmount = 1;
		    movable.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
		    movable.setVelocity(0, 0);
		}
	    }
	}
    }

    private void handleObstacleCollisions() {
	for (Movable movable : movables) {
	    if (movable instanceof Obstacle && movable.getHitBox().intersects(player.getHitBox())) {
		Obstacle obstacle = (Obstacle) movable;
		Rectangle2D obstacleHitBox = obstacle.getHitBox();
		Point2D from = new Point2D.Double(obstacleHitBox.getCenterX(), obstacleHitBox.getCenterY());
		Point2D to = new Point2D.Double(player.getHitBox().getCenterX(), movable.getHitBox().getCenterY());
		Direction pushBackDirection = Direction.getDirectionBetweenPoints(from, to);
		int pushBackAmount = 2;
		player.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
		player.setVelocity(0, 0);
	    }
	}
    }

    public void checkForHits(Character e)
    {
	for (Movable movable : movables) {
	    if (movable instanceof Weapon) {
		Weapon theMurderWeapon = (Weapon) movable;
		e.isHit(theMurderWeapon);
	    }

	}
    }

    /**
     * Check if anything damages the player.
     */
    public void checkForCollisionHits() {
	for (Movable movable : movables) {
	    if (movable instanceof Enemy) {
		if (((Enemy) movable).playerCollision(player)) {
		    player.takeDamage();
		}
	    }
	}

    }

    private void removeGarbage() {
	movables.removeIf(Movable::getIsGarbage);
    }

    public Player getPlayer() {
	return this.player;
    }

    public void setPlayer(final Player player) {
	this.player = player;
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

    public void addEnemySword(Enemy enemy) {
	if (enemy.wantToAttack()) {
	    addEntity(getPlayer().getSword());
	}
    }

    public void playerAttack() {
	player.becomeAttacking();
	addEntity(player.getSword());
	checkIfAnyEntityHit();
    }

    public void playerShootArrow() {
	player.becomeAttacking();
	addEntity(player.shootProjectile());
    }

    public List<Movable> getMovables() {
	return movables;
    }

    public void checkIfAnyEntityHit() {
	for (Movable movable : movables) {
	    if (movable instanceof Character) {
		checkForHits((Character) movable);
	    }

	}
    }


    public World getWorld() {
	return world;
    }

    public void setWorld(final World world) {
	// Remove old obstacles
//	for (Obstacle obstacle : world.getObstacles()) {
//	    obstacle.isGarbage = true;
//	}
	// Actually change world
	this.world = world;
	// Add new obstacles
	for (Obstacle obstacle : world.getObstacles()) {
	    addEntity(obstacle);
	}
    }

    public void addEnemy(final double x, final double y) {
	addEnemy(new Point2D.Double(x, y));
    }

    private void addEntity(final Movable movable) {
	movables.add(movable);
    }

}
