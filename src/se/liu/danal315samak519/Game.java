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

	// Adding player to temp movables list for ease
	List<Movable> movablesWithPlayer = new ArrayList<>(getMovables());
	movablesWithPlayer.add(player);

	for (Movable movable : movablesWithPlayer) {
	    // handle collisions
	    handleWallCollisions(movable);
	    handleObstacleCollisions(movable);
	    if (movable instanceof Enemy) {
		checkForCollisionHits(movable);
	    }

	    // tick
	    movable.tick();
	}
	if (playerIsOutOfBounds()) {
	    changeToNextWorld();
	}
    }

    private void placePlayerInCenterOfWorld() {
	player.setLocation(getWorld().getCenterX() - player.getWidth() / 2, getWorld().getCenterY() - player.getHeight() / 2);
    }

    private void changeToNextWorld() {
	currentWorldID++;
	setWorld(new World("map" + currentWorldID + ".tmx"));
	placePlayerInCenterOfWorld();
    }

    private boolean playerIsOutOfBounds() {
	boolean above = player.getY() < 0;
	boolean below = player.getY() > world.getHeight();
	boolean left = player.getX() < 0;
	boolean right = player.getX() > world.getWidth();
	return above || below || left || right;
    }

    /**
     * Makes sure entities can't pass through obstacles
     */
    private void handleObstacleCollisions(final Movable movable) {
	for(Obstacle obstacle : world.getObstacles()){
	    if(obstacle.equals(movable)){
		return; // Don't check collision with self
	    }
	    Rectangle2D obstacleHitBox = obstacle.getHitBox();
	    if(movable.getHitBox().intersects(obstacleHitBox)){
		Point2D from = new Point2D.Double(obstacleHitBox.getCenterX(), obstacleHitBox.getCenterY());
		Point2D to = new Point2D.Double(movable.getHitBox().getCenterX(), movable.getHitBox().getCenterY());
		Direction pushBackDirection = Direction.getDirectionBetweenPoints(from, to, obstacle.getWidth(), obstacle.getHeight());
		switch(pushBackDirection){
		    case UP, DOWN -> movable.setVelY(0);
		    case LEFT, RIGHT-> movable.setVelX(0);
		}
		int pushBackAmount = 1;
		movable.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
	    }
	}
    }

    /**
     * Makes sure no movableEntities pass through foreground tiles
     */
    private void handleWallCollisions(final Movable movable) {
	if (getWorld().getLayers() < 2) {
	    return; // No wall collision to handle, there are only background layers!
	}

	for (Tile tile : world.getForegroundTileList()) {
	    Rectangle tileHitBox = new Rectangle(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
	    // Handle movable-wall collision
	    if (movable.getHitBox().intersects(tileHitBox)) {
		Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		Point2D to = new Point2D.Double(movable.getHitBox().getCenterX(), movable.getHitBox().getCenterY());
		Direction pushBackDirection = Direction.getDirectionBetweenPoints(from, to);
		switch(pushBackDirection){
		    case UP, DOWN -> movable.setVelY(0);
		    case LEFT, RIGHT-> movable.setVelX(0);
		}
		int pushBackAmount = 1;
		movable.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
	    }
	}
    }

    public void checkForHits(Character e)
    {
	for (Movable movable : movables) {
	    if (movable instanceof Weapon) {
		Weapon theMurderWeapon = (Weapon) movable;
		e.isHit((Sword) theMurderWeapon);
	    }
	}
    }

    /**
     * Check if anything damages the player.
     */
    public void checkForCollisionHits(Movable movable) {
	if (((Enemy) movable).playerCollision(player)) {
	    player.takeDamage();
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
