package se.liu.danal315samak519;

import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Obstacle;
import se.liu.danal315samak519.entities.Person;
import se.liu.danal315samak519.entities.Player;
import se.liu.danal315samak519.entities.Potion;
import se.liu.danal315samak519.entities.enemies.Caster;
import se.liu.danal315samak519.entities.enemies.Enemy;
import se.liu.danal315samak519.entities.enemies.Knight;
import se.liu.danal315samak519.entities.enemies.Red;
import se.liu.danal315samak519.entities.enemies.Sentry;
import se.liu.danal315samak519.entities.weapons.Weapon;
import se.liu.danal315samak519.map.Room;
import se.liu.danal315samak519.map.Tile;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;


public class Game
{
    private static final int MARGIN = 150;
    private final RandomGenerator randomGenerator = new Random();
    private List<Movable> movables = new ArrayList<>();
    /**
     * Needs to be a linked list to allow for push/pop.
     */
    private LinkedList<Movable> pendingMovables = new LinkedList<>();
    private Collection<FrameListener> frameListeners = new ArrayList<>();
    private Player player = null;
    private Room room;
    private boolean roomIsCleared;
    /**
     * When the game is inactive, the game is paused.
     */
    private boolean paused = false;

    /**
     * A game with assumed map0.tmx
     */
    public Game() {
	this(new Room("map0.tmx"));
    }

    public Game(Room room) {
	this.room = room;
	resetGame();
    }

    public void pause() {
	paused = true;
    }


    public void unpause() {
	paused = false;
    }

    /**
     * Resets the game by setting player and room to default values
     */
    private void resetGame() {
	setPlayer(new Player(new Point2D.Float(getRoom().getCenterX(), getRoom().getCenterY())));
	resetRoom();
    }

    /**
     * Return true if player inside play area of room.
     */
    public boolean getIfPlayerInPlayArea() {
	return getRoom().getInsideRoomBounds().contains(player.getCoord());
    }

    /**
     * Make the entire game state update removing / adding entities and handle collisions
     */
    public void tick()
    {
	if (isPaused()) {
	    return; // Do nothing if paused
	}

	handlePlayerOutOfBounds();
	birthPending();
	setRoomIsCleared(true); // Assume room is cleared

	// Iterate through all movables, handle collisions and tick
	List<Movable> allMovables = getMovablesInclPlayer();
	for (Movable movable0 : allMovables) {
	    movable0.tick();
	    handleDeath(movable0);
	    handleWallCollision(movable0);
	    // Second iteration of all movables, for handling movable-movable collisions
	    for (Movable movable1 : allMovables) {
		handleMovableCollision(movable0, movable1);
	    }
	    while (movable0.hasPending()) {
		pushPending(movable0.popPending());
	    }
	    if (movable0 instanceof Enemy) { // NECESSARY INSTANCEOF CHECK!
		setRoomIsCleared(false); // Found enemy! Not cleared.
	    } else if (movable0 instanceof Obstacle) { // OPEN GATES IF NO MORE ENEMIES
		Obstacle obstacle = (Obstacle) movable0;
		// If room is cleared, open all gates
		if (getRoomIsCleared() || !getIfPlayerInPlayArea()) {
		    obstacle.open();
		} else { // Else close all gates
		    obstacle.close();
		}
	    }
	}
    }

    /**
     * Remove the movable if it is garbage, also adds its pending movables to the pending list. If the player is garbage, reset the game
     *
     * @param movable
     */
    private void handleDeath(final Movable movable) {
	if (movable.getIsGarbage()) {
	    if (isPlayerDead()) {
		pause();
		resetGame();
	    } else {
		while (movable.hasPending()) {
		    pushPending(movable.popPending());
		}
		movables.remove(movable);
	    }
	}
    }

    private boolean isPlayerDead() {
	return player.getIsGarbage();
    }

    private void handlePlayerOutOfBounds() {
	Direction outOfBoundsDirection = getOutOfBoundsDirection(getPlayer());
	if (outOfBoundsDirection != null) {
	    resetRoom();
	    placePlayerAtEntrance(outOfBoundsDirection);
	}
    }

    /**
     * Place the player at appropiate entrance in the new map, when player has exited the previous
     *
     * @param outOfBoundsDirection
     */
    private void placePlayerAtEntrance(final Direction outOfBoundsDirection) {
	Direction entranceDirection = outOfBoundsDirection.getOpposite();
	final float margin = 10.0f;
	switch (entranceDirection) {
	    case UP -> getPlayer().setCenterLocation(this.getRoom().getCenterX(), margin);
	    case DOWN -> getPlayer().setCenterLocation(this.getRoom().getCenterX(), this.getRoom().getHeight() - margin);
	    case LEFT -> getPlayer().setCenterLocation(margin, this.getRoom().getCenterY());
	    case RIGHT -> getPlayer().setCenterLocation(this.getRoom().getWidth() - margin, this.getRoom().getCenterY());
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

    private void changeRoom(Room room) {
	// Clear previous movables (if necessary)
	if (getMovables() != null) {
	    for (Movable movable : getMovables()) {
		movable.markAsGarbage();
	    }
	}
	// Actually set to new room
	setRoom(room);
	// Populate with new movables
	spawnEnemies();
	spawnObstacles();
    }

    /**
     * "Changes" the room to the same one, effectively resetting everything Currently hardcoded to map0.tmx over and over.
     */
    private void resetRoom() {
	changeRoom(new Room("map0.tmx"));
    }

    private void spawnObstacles() {
	for (Obstacle obstacle : getRoom().getObstacles()) {
	    addMovable(obstacle);
	}
    }

    /**
     * Spawn enemies in the room 1/4 chance for each type of enemy
     */
    private void spawnEnemies() {
	int minEnemies = 1;
	int maxEnemies = 2;

	int randomEnemyCount = minEnemies + randomGenerator.nextInt(maxEnemies - minEnemies + 1);
	for (int i = 0; i < randomEnemyCount; i++) {
	    int enemyTypes = 4;
	    int randomEnemyType = randomGenerator.nextInt(enemyTypes);
	    switch (randomEnemyType) {
		case 0 -> addMovable(new Caster(getRandomCoord(), player));
		case 1 -> addMovable(new Sentry(getRandomCoord(), player));
		case 2 -> addMovable(new Red(getRandomCoord(), player));
		case 3 -> addMovable(new Knight(getRandomCoord(), player));
	    }
	}
    }

    /**
     * @return a random point within the room, but not too close to the edges
     */
    private Point2D.Float getRandomCoord() {
	int margin = MARGIN;
	return new Point2D.Float(margin + randomGenerator.nextInt(getRoom().getWidth() - 2 * margin),
				 margin + randomGenerator.nextInt(getRoom().getHeight() - 2 * margin));
    }

    /**
     * Makes sure the input movable can't pass through foreground tiles.
     *
     * @param movable
     */
    private void handleWallCollision(final Movable movable) {
	int minLayerAmount = 2;
	if (getRoom().getLayers() < minLayerAmount) {
	    throw new IndexOutOfBoundsException("There is no foreground layer in loaded room! Can't check wall collisions.");
	}
	for (Tile tile : room.getForegroundTileList()) {
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
	float centerX = (float) movable.getHitBox().getCenterX();
	float centerY = (float) movable.getHitBox().getCenterY();

	if (centerX < 0) {
	    return Direction.LEFT;
	} else if (centerX > getRoom().getWidth()) {
	    return Direction.RIGHT;
	} else if (centerY < 0) {
	    return Direction.UP;
	} else if (centerY > getRoom().getHeight()) {
	    return Direction.DOWN;
	}
	return null; // Movable is in bounds!
    }

    private void pushPending(Movable movable) {
	if (movable != null) {
	    pendingMovables.push(movable);
	}
    }

    private void birthPending() {
	while (!pendingMovables.isEmpty()) {
	    addMovable(pendingMovables.pop());
	}
    }

    /**
     * Handle collisions where two movables are involved Unfortunately this is terrible abstraction
     */
    private void handleMovableCollision(final Movable movable0, final Movable movable1) {
	if (!movable0.getHitBox().intersects(movable1.getHitBox()) || movable0.equals(movable1)) {
	    return; // No need to continue if no collision between movable0 and movable1, or if they are equal.
	}
	if (movable0 instanceof Obstacle && movable1 instanceof Person) {
	    movable1.nudgeAwayFrom(movable0.getHitBox());
	}
	if (movable0 instanceof Enemy && movable1 instanceof Person) {
	    movable0.nudgeAwayFrom(movable1.getHitBox());
	    if (movable1 instanceof Player) {
		((Player) movable1).tryTakeDamage(((Enemy) movable0).getDamage());
	    }
	}
	// Projectile-Person
	if (movable0 instanceof Weapon && movable1 instanceof Person) {
	    Weapon weapon = (Weapon) movable0;
	    Person person = (Person) movable1;
	    if (!person.equals(weapon.getOwner())) {
		person.tryTakeDamage(1); // Hardcoded projectile damage of 1
		weapon.markAsGarbage();
	    }
	}
	// Player-Potion
	if (movable0 instanceof Potion && movable1 instanceof Player) {
	    Potion potion = (Potion) movable0;
	    Player player = (Player) movable1;
	    potion.pickUp(player);
	}
    }

    private boolean getRoomIsCleared() {
	return roomIsCleared;
    }

    private void setRoomIsCleared(final boolean b) {
	roomIsCleared = b;
    }

    public Player getPlayer() {
	return this.player;
    }

    private void setPlayer(final Player player) {
	this.player = player;
    }

    public void addFrameListener(FrameListener fl)
    {
	frameListeners.add(fl);
    }

    private void notifyListeners() {
	for (FrameListener frameListener : frameListeners) {
	    frameListener.frameChanged();
	}
    }

    public void playerSwordAttack() {
	if (player.canAttack()) {
	    player.becomeAttacking();
	    addMovable(player.getSword());
	}
	notifyListeners();
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

    public Room getRoom() {
	return room;
    }

    private void setRoom(final Room room) {
	this.room = room;
    }

    /**
     * Add a movable to the list of movable, if it is not null.
     *
     * @param movable
     */
    private void addMovable(final Movable movable) {
	if (movable != null) {
	    movables.add(movable);
	}
    }

    public boolean isPaused() {
	return paused;
    }

    public void togglePause() {
	paused = !paused;
    }

    /**
     * Return a list of all movables that are instances of Person, including the player!
     *
     * @return
     */
    public List<Person> getPersons() {
	List<Person> persons = new ArrayList<>();
	for (Movable movable : getMovablesInclPlayer()) {
	    if (movable instanceof Person) {
		persons.add((Person) movable);
	    }
	}
	return persons;
    }
}


