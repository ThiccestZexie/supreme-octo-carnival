package se.liu.danal315samak519;

import se.liu.danal315samak519.map.Obstacle;
import se.liu.danal315samak519.map.Tile;
import se.liu.danal315samak519.map.World;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class Game
{
    public List<Movable> entities = new ArrayList<>();
    private List<FrameListener> frameListeners = new ArrayList<>();
    private Player player;
    private World world;
    private int currentWorldID = 0;

    public void tick()
    {
	removeGarbage();
	player.tick();
	handleWallCollisions();
	checkIfPlayerTouchesZone();
	checkForCollisionHits();
	for (Movable e : entities) {
	    e.tick();
	}
    }

    private void changeWorld(){
	currentWorldID++;
	setWorld(new World("map" + currentWorldID + ".tmx"));
	double centerX = world.getColumns() * world.getTileWidth() / 2.0;
	double centerY = world.getRows() * world.getTileHeight() / 2.0;
	player.setLocation(centerX, centerY);
    }

    private void checkIfPlayerTouchesZone() {
	for (Obstacle obstacle : getWorld().getZones()) {
	    if(obstacle.getHitBox().intersects(player.getHitBox())){
		changeWorld();
	    }
	}
    }

    /**
     * Makes sure no movableEntities pass through foreground tiles
     */
    private void handleWallCollisions() {
	if(getWorld().getLayers() < 2){
	    return; // Only background layers!
	}

	for (Tile tile : world.getForegroundTileList()) {
	    Rectangle tileHitBox = new Rectangle(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
	    // Handle player-wall collision
	    if (player.getHitBox().intersects(tileHitBox)) {
		Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		Point2D to = new Point2D.Double(player.getHitBox().getCenterX(), player.getHitBox().getCenterY());
		Direction pushBackDirection = DirectionUtil.getDirectionBetweenPoints(from, to);
		int pushBackAmount = 5;
		player.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
		player.setVelocity(0, 0);
	    }

	    // Handle movableEntity-wall collision
	    for (Movable movable : entities) {
		if (movable.getHitBox().intersects(tileHitBox)) {
		    Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		    Point2D to = new Point2D.Double(movable.getHitBox().getCenterX(), movable.getHitBox().getCenterY());
		    Direction pushBackDirection = DirectionUtil.getDirectionBetweenPoints(from, to);
		    int pushBackAmount = 1;
		    movable.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
		    movable.setVelocity(0, 0);
		}
	    }
	}
    }

    public void checkForHits(Character e)
    {
	for (Movable movable : entities) {
	    if (movable instanceof Weapon) {
		Weapon theMurderWeapon = (Weapon) movable;
		e.isHit((Sword) theMurderWeapon);
	    }

	}
    }

    /**
     * Check if anything damages the player.
     */
    public void checkForCollisionHits() {
	for (Movable movable : entities) {
	    if (movable instanceof Enemy) {
		if (((Enemy) movable).playerCollision(player)) {
		    player.takeDamage();
		}
	    }
	}

    }


    private void removeGarbage() {
	entities.removeIf(Movable::getIsGarbage);
    }

    public void setPlayer(final Player player) {
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

    public List<Movable> getEntities() {
	return entities;
    }

    public void checkIfAnyEntityHit() {
	for (Movable movable : entities) {
	    if (movable instanceof Character) {
		checkForHits((Character) movable);
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

    private void addEntity(final Movable movable) {
	entities.add(movable);
    }

}
