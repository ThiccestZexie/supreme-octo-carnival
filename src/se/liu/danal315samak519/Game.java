package se.liu.danal315samak519;

import se.liu.danal315samak519.entities.Character;
import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Obstacle;
import se.liu.danal315samak519.entities.Player;
import se.liu.danal315samak519.entities.Potion;
import se.liu.danal315samak519.entities.enemies.Blue;
import se.liu.danal315samak519.entities.enemies.Enemy;
import se.liu.danal315samak519.entities.enemies.Knight;
import se.liu.danal315samak519.entities.enemies.Red;
import se.liu.danal315samak519.map.Tile;
import se.liu.danal315samak519.map.World;
import se.liu.danal315samak519.weapons.Weapon;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Game
{
    private final Random random;
    public List<Movable> movables = new ArrayList<>();
    private LinkedList<Movable> pendingMovables = new LinkedList<>();
    private List<FrameListener> frameListeners = new ArrayList<>();
    private Player player = null;
    private World world = null;
    private int currentWorldID = 0;

    /**
     * A game with assumed map0.tmx
     */
    public Game() {
	this(new World("map0.tmx"));
    }

    /**
     * A game with argument world
     *
     * @param world
     */
    public Game(World world) {
	random = new Random();
	setPlayer(new Player(new Point2D.Double(world.getCenterX(), world.getCenterY())));
	changeWorld(world);
    }

    /**
     * Make the entire game state update removing / adding entities and handle collisions
     */
    public void tick()
    {
	Direction outOfBoundsDirection = getOutOfBoundsDirection(getPlayer());
	if (outOfBoundsDirection != null) {
//	    changeToNextWorld();
	    resetWorld();
	    placePlayerAtEntrance(outOfBoundsDirection);
	}
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
     * Place the player at appropiate entrance in the new map, when player has exited the previous
     *
     * @param outOfBoundsDirection
     */
    private void placePlayerAtEntrance(final Direction outOfBoundsDirection) {
	Direction entranceDirection = outOfBoundsDirection.getOpposite();
	double margin = 10.0;
	switch (entranceDirection) {
	    case UP -> getPlayer().setCenterLocation(this.getWorld().getCenterX(), margin);
	    case DOWN -> getPlayer().setCenterLocation(this.getWorld().getCenterX(), this.getWorld().getHeight() - margin);
	    case LEFT -> getPlayer().setCenterLocation(margin, this.getWorld().getCenterY());
	    case RIGHT -> getPlayer().setCenterLocation(this.getWorld().getWidth() - margin, this.getWorld().getCenterY());
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

    public void changeWorld(World world) {
	// Clear previous movables (if necessary)
	if (getMovables() != null) {
	    for (Movable movable : getMovables()) {
		movable.markAsGarbage();
	    }
	}
	// Actually set to new world
	setWorld(world);
	// Populate with new movables
	spawnEnemies();
//	spawnObstacles();
    }

    /**
     * "Changes" the world to the same one, effectively resetting everything
     */
    public void resetWorld() {
	changeWorld(getWorld());
    }

    private void spawnObstacles() {
	for (Obstacle obstacle : getWorld().getObstacles()) {
	    addMovable(obstacle);
	}
    }

    private void spawnEnemies() {
	for (int i = 0; i < 1; i++) {
	    int randomX = 200 + random.nextInt(400);
	    int randomY = 200 + random.nextInt(400);
	    Point2D.Double randomCoord = new Point2D.Double(randomX, randomY);
	    this.addBlue(randomCoord);
	}
	for (int i = 0; i < 1; i++) {
	    int randomX = 200 + random.nextInt(400);
	    int randomY = 200 + random.nextInt(400);
	    Point2D.Double randomCoord = new Point2D.Double(randomX, randomY);
	    this.addRed(randomCoord);
	}
	for (int i = 0; i < 1; i++) {
	    int randomX = 200 + random.nextInt(400);
	    int randomY = 200 + random.nextInt(400);
	    Point2D.Double randomCoord = new Point2D.Double(randomX, randomY);
	    this.addKnight(randomCoord);
	}
    }

    private void changeToNextWorld() {
	currentWorldID++;
	changeWorld(new World("map" + currentWorldID + ".tmx"));
    }

    public List<Movable> getPendingMovables() {
	return pendingMovables;
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
	if (movable instanceof Obstacle) {
	    return; // Don't handle wall collisions on obstacles!!
	}
	for (Tile tile : world.getForegroundTileList()) {
	    movable.nudgeAwayFrom(tile.getHitBox());
	}
    }

    /**
     * Return the direction the movable is out of bounds in. Return null if inside of bounds.
     *
     * @param movable
     *
     * @return
     */
    private Direction getOutOfBoundsDirection(Movable movable) {
	double centerX = movable.getHitBox().getCenterX();
	double centerY = movable.getHitBox().getCenterY();

	if (centerX < 0) {
	    return Direction.LEFT;
	} else if (centerX > getWorld().getWidth()) {
	    return Direction.RIGHT;
	} else if (centerY < 0) {
	    return Direction.UP;
	} else if (centerY > getWorld().getHeight()) {
	    return Direction.DOWN;
	}
	return null; // Movable is in bounds
    }

    public void aiDecide(Enemy enemy) {
	if (!(enemy instanceof Blue)) {
	    return;
	}
	if (enemy.checkIfPlayerIsInFront(500, 100)) {
	    if (enemy.canAttack()) {
		enemy.becomeAttacking();
		pushPending(enemy.getProjectile());
	    }
	}
    }

    private void pushPending(Movable movable) {
	pendingMovables.push(movable);
    }

    private void birthNewEntites() {
	while (!pendingMovables.isEmpty()) {
	    addMovable(pendingMovables.pop());
	}
    }

    /**
     * Handle collisions where two movables are involved
     */
    public void handleMovableCollision(final Movable movable0, final Movable movable1) {
	if (!movable0.getHitBox().intersects(movable1.getHitBox())) {
	    return; // No need to continue if no collision between movable0 and movable1
	}

	if (movable0 instanceof Obstacle && movable1 instanceof Player) {
	    movable1.nudgeAwayFrom(movable0.getHitBox());
	}
	// Enemy-Player
	else if (movable0 instanceof Enemy && movable1 instanceof Player) {
	    movable0.nudgeAwayFrom(movable1.getHitBox());
	    ((Player) movable1).tryTakeDamage();
	}
	// Projectile-Character
	else if (movable0 instanceof Weapon && movable1 instanceof Character) {
	    Weapon weapon = (Weapon) movable0;
	    Character character = (Character) movable1;
	    if (!character.equals(weapon.getOwner())) {
		character.tryTakeDamage();
		weapon.markAsGarbage();
	    }
	}
	// Player-Potion
	else if (movable0 instanceof Potion && movable1 instanceof Player) {
	    Potion potion = (Potion) movable0;
	    Player player = (Player) movable1;
	    potion.pickUp(player);
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
	addMovable(new Blue(coord, player));
    }

    public void addKnight(Point2D.Double coord)
    {
	addMovable(new Knight(coord, player));
    }

    public void addRed(Point2D.Double coord)
    {
	addMovable(new Red(coord, player));
    }

    public void playerSwordAttack() {
	if (player.canAttack()) {
	    player.becomeAttacking();
	    addMovable(player.getSword());
	}
    }

    public void playerShootArrow() {
	if (player.canAttack()) {
	    player.becomeAttacking();
	    addMovable(player.getProjectile());
	}
    }

    public List<Movable> getMovables() {
	return movables;
    }

    public World getWorld() {
	return world;
    }

    private void setWorld(final World world) {
	this.world = world;
    }

    public void addMovable(final Movable movable) {
	if (movable != null) {
	    movables.add(movable);
	}
    }

}


