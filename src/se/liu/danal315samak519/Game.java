package se.liu.danal315samak519;

import se.liu.danal315samak519.entities.Character;
import se.liu.danal315samak519.entities.Entity;
import se.liu.danal315samak519.entities.enemies.Blue;
import se.liu.danal315samak519.entities.enemies.Enemy;
import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Player;
import se.liu.danal315samak519.Weapons.Projectile;
import se.liu.danal315samak519.Weapons.Weapon;
import se.liu.danal315samak519.entities.enemies.Red;
import se.liu.danal315samak519.map.Obstacle;
import se.liu.danal315samak519.map.Tile;
import se.liu.danal315samak519.map.World;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Game
{
    public List<Movable> entities = new ArrayList<>();
    private List<FrameListener> frameListeners = new ArrayList<>();
    private Player player = null;
    private World world = null;
    private int currentWorldID = 0;
    LinkedList<Movable> wantsToBeBorn = new LinkedList<>();

    public void tick()
    {
	removeGarbage();
	birtherOfAllThings();
	player.tick();
	handleWallCollisions();
//	checkIfPlayerTouchesZone();
	checkForCollisionHits();
	for (Movable e : getEntities()) {
	    if (e instanceof Enemy) {
		Enemy enemy = (Enemy) e;
		aiDecide(enemy);
	    }
	    if(e instanceof Projectile){
		checkForPlayerHits(e);
	    }
	    e.tick();
	}
    }

    private void changeWorld() {
	currentWorldID++;
	setWorld(new World("map" + currentWorldID + ".tmx"));
	double centerX = world.getColumns() * world.getTileWidth() / 2.0;
	double centerY = world.getRows() * world.getTileHeight() / 2.0;
	player.setLocation(centerX, centerY);
    }
//TODO idunno
   // private void checkIfPlayerTouchesZone() {
//	for (Obstacle obstacle : getWorld().getZones()) {
//	    if (obstacle.getHitBox().intersects(player.getHitBox())) {
//		changeWorld();
//	    }
//	}
 //   }

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
	    return; // Only background layers!
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

	    // Handle movableEntity-wall collision
	    for (Movable movable : entities) {
		if (movable.getHitBox().intersects(tileHitBox)) {
		    Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		    Point2D to = new Point2D.Double(movable.getHitBox().getCenterX(), movable.getHitBox().getCenterY());
		    Direction pushBackDirection = Direction.getDirectionBetweenPoints(from, to);
		    int pushBackAmount = 1;
		    movable.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
		    movable.setVelocity(0, 0);
		    if (movable instanceof Projectile){
			((Projectile) movable).setGarbage(true);
		    }
		}
	    }
	}
    }
    public void aiDecide(Enemy enemy){
	if (!(enemy instanceof Blue)){
	    return;
	}
	    if (enemy.checkIfPlayerIsInFront(500,100)){
		if (enemy.tryToAttack())
		{
			wantsToBeBorn.push(enemy.getProjectile());
		}
	}
    }
    private void birtherOfAllThings(){
	while(!wantsToBeBorn.isEmpty()){
	    addEntity(wantsToBeBorn.pop());
	}
    }
    public void checkForHits(Character e)
    {
	for (Movable movable : entities)
	{
	    if (movable instanceof Weapon)
	    {
		Weapon theMurderWeapon = (Weapon) movable;
		e.isHit(theMurderWeapon);
	    }
	}
    }

	    /**
	     * Check if anything damages the player.
	     */
	    public void checkForCollisionHits () {
		for (Movable movable : entities) {
		    if (movable instanceof Enemy) {
			if (((Enemy) movable).playerCollision(player)) {
			    player.takeDamage();
			}
		    }
		    if (movable instanceof Projectile) {
			arrowHit((Projectile) movable);
		    }

		}

	    }

	    private void arrowHit (Projectile arrow){
		for (Movable target : entities) {
		    if (target instanceof Character && !target.equals(arrow))
			if (arrow.hitEntity(target)) {
			    ((Character) target).isHit(arrow);
			}
		}
	    }
	    private void checkForPlayerHits(Entity e){
		if(player.getHitBox().intersects(e.getHitBox())){
		    player.isHit((Weapon) e);
		}
	    }
	    private void removeGarbage () {
		entities.removeIf(Movable::getIsGarbage);
	    }

	    public void setPlayer ( final Player player){
		this.player = player;
	    }

	    public Player getPlayer () {
		return this.player;
	    }

	    public void addFrameListener (FrameListener fl)
	    {
		frameListeners.add(fl);
	    }

	    public void notifyListeners () {
		for (FrameListener frameListener : frameListeners) {
		    frameListener.frameChanged();
		}
	    }

	    public void nudgePlayer ( final int dx, final int dy){
		player.nudge(dx, dy);
		notifyListeners();
	    }

	    public void addBlue(Point2D.Double coord)
	    {
		addEntity(new Blue(coord, player));
	    }
	    public void addRed(Point2D.Double coord)
	    {
		addEntity(new Red(coord, player));
	    }

	    public void playerAttack () {
		if (player.tryToAttack()) {
		    addEntity(player.getSword());
		    checkIfAnyEntityHit();
		}

	    }

	    public void playerShootArrow () {
		if (player.tryToAttack()) {
		    addEntity(player.getProjectile());
		}
	    }

	    public List<Movable> getEntities () {
		return entities;
	    }

	    public void checkIfAnyEntityHit () {
		for (Movable movable : entities) {
		    if (movable instanceof Character) {
			checkForHits((Character) movable);
		    }

		}
	    }


	    public World getWorld () {
		return world;
	    }

	    public void setWorld ( final World world){
		this.world = world;
	    }

	    public void addBlue(final double x, final double y){
		addBlue(new Point2D.Double(x, y));
	    }

	    private void addEntity ( final Movable movable){
		entities.add(movable);
	    }

	}


