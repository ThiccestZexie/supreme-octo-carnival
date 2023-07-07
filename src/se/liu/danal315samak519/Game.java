package se.liu.danal315samak519;

import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Obstacle;
import se.liu.danal315samak519.entities.Person;
import se.liu.danal315samak519.entities.Player;
import se.liu.danal315samak519.entities.enemies.Caster;
import se.liu.danal315samak519.entities.enemies.Knight;
import se.liu.danal315samak519.entities.enemies.Red;
import se.liu.danal315samak519.entities.enemies.Sentry;
import se.liu.danal315samak519.map.Room;
import se.liu.danal315samak519.map.Tile;

import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;


@SuppressWarnings("SSBasedInspection")
public class Game
{
    private static final int MARGIN = 150;
    private final RandomGenerator randomGenerator = new Random();
    private List<Movable> movables = new ArrayList<>();
    private Deque<Movable> pendingMovables = new ArrayDeque<>();
    private Collection<FrameListener> frameListeners = new ArrayDeque<>();
    private Player player = null;
    private Room room;

    /**
     * How many enemies were spawned in the room creation
     */
    private int enemyAmount = 0;
    /**
     * Whether the game is paused. If true, no ticks are made.
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
	checkIfRoomCompleted();

	// Iterate through all movables, handle collisions and tick
	List<Movable> allMovables = getMovablesIncludingPlayer();
	for (Movable movable0 : allMovables) {
	    movable0.tick();
	    handleDeath(movable0);
	    handleWallCollision(movable0);
	    // Second iteration of all movables, for handling movable-movable collisions
	    for (Movable movable1 : allMovables) {
		handleMovableCollision(movable0, movable1);
	    }
	    // Spawn all the movables' pending movables
	    while (movable0.hasPending()) {
		pushPending(movable0.popPending());
	    }
	}
    }

    /**
     * Makes the room complete if the player is outside the room or all enemies are dead.
     */
    private void checkIfRoomCompleted() {
	boolean roomComplete = !getIfPlayerInPlayArea() || player.getKillsInRoom() == enemyAmount;
	for (Obstacle obstacle : getRoom().getObstacles()) {
	    obstacle.setRoomComplete(roomComplete);
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
    private List<Movable> getMovablesIncludingPlayer() {
	List<Movable> movablesWithPlayer = new ArrayList<>(getMovables());
	movablesWithPlayer.add(getPlayer());
	return movablesWithPlayer;
    }

    /**
     * Clears the room of movables, changes the room, and repopulates the room with the new room's movables.
     *
     * @param room
     */
    private void changeRoom(Room room) {
	garbageEverything();
	setRoom(room);
	getPlayer().setKillsInRoom(0);
	// Populate with new movables
	spawnEnemies();
	spawnObstacles();
    }

    /**
     * Marks every movable as garbage, effectively removing them from the game.
     */
    private void garbageEverything() {
	for (Movable movable : getMovables()) {
	    movable.setIsGarbage();
	}
    }

    /**
     * "Changes" the room to the same one, effectively resetting everything. Currently hardcoded to map0.tmx over and over.
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
     * Spawn enemies in the room with equal chance for each type of enemy
     */
    private void spawnEnemies() {
	final int minEnemies = 1;
	final int maxEnemies = 2;

	int randomEnemyCount = minEnemies + randomGenerator.nextInt(maxEnemies - minEnemies + 1);
	this.enemyAmount = randomEnemyCount;
	for (int i = 0; i < randomEnemyCount; i++) {
	    final int enemyTypes = 4;
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
     * Returns a random point within the room, but not too close to the edges
     *
     * @return a Point2D.Float with the 'random' coordinates
     */
    private Point2D.Float getRandomCoord() {
	return new Point2D.Float(MARGIN + randomGenerator.nextInt(getRoom().getWidth() - 2 * MARGIN),
				 MARGIN + randomGenerator.nextInt(getRoom().getHeight() - 2 * MARGIN));
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
	for (Tile tile : room.getForegroundTiles()) {
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

    /**
     * Spawn movables that are waiting to be spawned.
     */
    private void birthPending() {
	while (!pendingMovables.isEmpty()) {
	    addMovable(pendingMovables.pop());
	}
    }

    /**
     * Handle collisions where two movables are involved. They should always be pushed away from eachother, and also interact with
     * eachother, which means different things for different combinations.
     */
    private void handleMovableCollision(final Movable movable, final Movable other) {

	if(!movable.getHitBox().intersects(other.getHitBox())){
	}
	else if(other instanceof  Person){
		movable.interactWith(other);
	}
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

    public void doPlayerSwordAttack() {
	if (player.canAttack()) {
	    player.becomeAttacking();
	    addMovable(player.getSword());
	}
    }

    public void doPlayerArrowShoot() {
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
	for (Movable movable : getMovablesIncludingPlayer()) {
	    if (movable instanceof Person) {
		persons.add((Person) movable);
	    }
	}
	return persons;
    }
}


