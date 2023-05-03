package se.liu.danal315samak519;

import se.liu.danal315samak519.entities.Character;
import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Player;
import se.liu.danal315samak519.entities.enemies.Blue;
import se.liu.danal315samak519.entities.enemies.Enemy;
import se.liu.danal315samak519.entities.enemies.Red;
import se.liu.danal315samak519.map.Tile;
import se.liu.danal315samak519.map.World;
import se.liu.danal315samak519.weapons.Projectile;
import se.liu.danal315samak519.weapons.Weapon;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Game
{
    public List<Movable> movables = new ArrayList<>();
    private LinkedList<Movable> pendingMovables = new LinkedList<>();
    private List<FrameListener> frameListeners = new ArrayList<>();
    private Player player = null;
    private World world = null;
    private int currentWorldID = 0;

    /**
     * Make the entire game state update! 1. Remove and add new movables to movableList 2. Tick every movable 3. Handle all collisions
     */
    public void tick()
    {
	removeGarbage();
	birthNewEntites();

	// Iterate through all movables (incl. player) and do appropiate actions
	List<Movable> allMovables = getMovablesInclPlayer();
	for (Movable movable0 : allMovables) {

	    movable0.tick();
	    handleWallCollision(movable0);

	    if (movable0 instanceof Enemy) {
		aiDecide((Enemy) movable0);
	    }

	    // Second iteration of all movables, for handling
	    // combinations of movables (e.g. colliding with eachother)
	    for (Movable movable1 : allMovables) {
		handleMovableCollision(movable0, movable1);
	    }
	}
    }

    /**
     * @return a list of "all" movables, which includes the player. Makes iterating easier.
     */
    private List<Movable> getMovablesInclPlayer() {
	List<Movable> list = new ArrayList<>(getMovables());
	list.add(getPlayer());
	return list;
    }

    private void changeWorld() {
	currentWorldID++;
	setWorld(new World("map" + currentWorldID + ".tmx"));
	double centerX = world.getColumns() * world.getTileWidth() / 2.0;
	double centerY = world.getRows() * world.getTileHeight() / 2.0;
	player.setLocation(centerX, centerY);
    }

    /**
     * Makes sure the input movable can't pass through walls.
     *
     * @param movable
     */
    private void handleWallCollision(final Movable movable) {
	if (getWorld().getLayers() < 2) {
	    throw new RuntimeException("There is no foreground layer in loaded world! Can't check wall collisions.");
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
	    if (movable.getHitBox().intersects(tileHitBox)) {
		Point2D from = new Point2D.Double(tileHitBox.getCenterX(), tileHitBox.getCenterY());
		Point2D to = new Point2D.Double(movable.getHitBox().getCenterX(), movable.getHitBox().getCenterY());
		Direction pushBackDirection = Direction.getDirectionBetweenPoints(from, to);
		int pushBackAmount = 1;
		movable.nudge(pushBackAmount * pushBackDirection.getX(), pushBackAmount * pushBackDirection.getY());
		movable.setVelocity(0, 0);
		if (movable instanceof Projectile) {
		    movable.markGarbage();
		}
	    }
	}
    }

    public void aiDecide(Enemy enemy) {
	if (!(enemy instanceof Blue)) {
	    return;
	}
	if (enemy.checkIfPlayerIsInFront(500, 100)) {
	    if (enemy.tryToAttack()) {
		pendingMovables.push(enemy.getProjectile());
	    }
	}
    }

    private void birthNewEntites() {
	while (!pendingMovables.isEmpty()) {
	    addEntity(pendingMovables.pop());
	}
    }

    public void checkForHits(Character e)
    {
	for (Movable movable : movables) {
	    if (movable instanceof Weapon) {
		Weapon theMurderWeapon = (Weapon) movable;
		e.getHitByWeapon(theMurderWeapon);
	    }
	}
    }

    /**
     * 1. if the movable is Enemy, and intersecting with player -> player.takeDamage() 2. if the movable is Projectile, cast to projectile
     * and call arrowHit with self.
     */
    public void handleMovableCollision(final Movable movable0, final Movable movable1) {
	if (!movable0.getHitBox().intersects(movable1.getHitBox())) {
	    return; // No need to continue if no collision between movable0 and movable1
	}

	// Enemy-Player
	if (movable0 instanceof Enemy && movable1 instanceof Player) {
	    ((Player) movable1).takeDamage();

	}
	// Projectile-Character
	else if (movable0 instanceof Weapon && movable1 instanceof Character) {
	    Weapon weapon = (Weapon) movable0;
	    Character character = (Character) movable1;
	    if (!character.equals(weapon.getOwner())) {
		character.takeDamage();
		weapon.markGarbage();
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

    public void addBlue(Point2D.Double coord)
    {
	addEntity(new Blue(coord, player));
    }

    public void addRed(Point2D.Double coord)
    {
	addEntity(new Red(coord, player));
    }

    public void playerAttack() {
	if (player.tryToAttack()) {
	    addEntity(player.getSword());
	}

    }

    public void playerShootArrow() {
	if (player.tryToAttack()) {
	    addEntity(player.getProjectile());
	}
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
	this.world = world;
    }

    public void addBlue(final double x, final double y) {
	addBlue(new Point2D.Double(x, y));
    }

    private void addEntity(final Movable movable) {
	movables.add(movable);
    }

}


