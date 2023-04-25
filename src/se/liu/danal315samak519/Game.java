package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class Game
{
    public List<MovableEntity> entities = new ArrayList<>();
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
	for (MovableEntity e : entities) {
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
	for (Zone zone : getWorld().getZones()) {
	    if(zone.getHitBox().intersects(player.getHitBox())){
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
	    for (MovableEntity movableEntity : entities) {
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
	for (MovableEntity movableEntity : entities) {
	    if (movableEntity instanceof Weapon) {
		Weapon theMurderWeapon = (Weapon) movableEntity;
		e.isHit(theMurderWeapon);
	    }

	}
    }

    /**
     * Check if anything damages the player.
     */
    public void checkForCollisionHits() {
	for (MovableEntity movableEntity : entities) {
	    if (movableEntity instanceof Enemy) {
		if (((Enemy) movableEntity).playerCollision(player)) {
		    player.takeDamage();
		}
	    }
	}

    }


    private void removeGarbage() {
	entities.removeIf(MovableEntity::getIsGarbage);
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

    public List<MovableEntity> getEntities() {
	return entities;
    }

    public void checkIfAnyEntityHit() {
	for (MovableEntity movableEntity : entities) {
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
	entities.add(movableEntity);
    }

}
