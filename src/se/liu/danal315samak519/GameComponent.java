package se.liu.danal315samak519;

import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Player;
import se.liu.danal315samak519.map.Room;
import se.liu.danal315samak519.map.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handles all the painting to the screen. Also handles the keybindings.
 */
public class GameComponent extends JComponent implements FrameListener
{
    private static final int ALPHA = 150;
    private static final int LEVEL_UP_FRAMES = 19;
    private static final int ABOVE_PLAYER_INCREMENT = 30;
    private Game game = new Game();
    public int indexOfLevelUpFrame = 0;
    public boolean didPlayerLevel = false;
    private int oldPlayerLevel;
    private Point tileSize = null;
    private boolean debug = true;
    private boolean showSkills = false;
    private boolean randomizeOnce = true;
    private long lastFrameTime;

    private Decrees decree00 = new Decrees("Movement Increase"), decree01 = new Decrees("Movement Increase");

    public GameComponent()
    {
	int tileWidth = game.getRoom().getTileWidth();
	int tileHeight = game.getRoom().getTileHeight();
	tileSize = new Point(tileWidth, tileHeight);
	setKeyBindings();
	game.addFrameListener(this);
	oldPlayerLevel = game.getPlayer().getLevel();

    }

    /**
     * The main painting method, every other painting method is called from here.
     */
    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	paintMapLayer(g, 0); // Paint background
	paintEntities(g);
	paintPlayer(g);
	paintMapLayer(g, 1); // Paint foreground
	paintGUI(g);
	if (game.isPaused()) {
	    paintPauseMenu(g);
	} else {
	    if (showSkills) {
		paintDecreeOverlay(g);
	    }
	    if (debug) {
		paintDebug(g);
	    }
	}
    }

    /**
     * Paint a semi-transparent black overlay, with text saying "Press any key to resume".
     *
     * @param g
     */
    private void paintPauseMenu(final Graphics g) {
	int r = 0, gr = 0,b = 0, a = ALPHA;
	final Point screenCenter = new Point(getWidth()/2,getHeight()/2) ;

	g.setColor(new Color(r, gr, b, a));
	g.fillRect(0, 0, getWidth(), getHeight());
	g.setColor(Color.WHITE);
	g.drawString("Press ESC to resume!", screenCenter.x, screenCenter.y);
    }
    public Game getGame(){
	return game;
    }

    private void paintPlayer(final Graphics g) {
	Player player = game.getPlayer();

	if (debug) {
	    // PAINT HITBOX
	    g.setColor(player.getColor());
	    g.drawRect(player.getEntityIntX(), player.getEntityIntY(), player.getIntWidth(), player.getIntHeight());
	}

	paintLevelUpAnimation(g);
	// PAINT SPRITE
	g.drawImage(player.getCurrentSprite(), player.getEntityIntX(), player.getEntityIntY(), player.getIntWidth(), player.getIntHeight(), null);

    }

    private void paintEntities(final Graphics g) {
	for (Movable movable : game.getMovables()) {
	    if (debug) {
		// PAINT HITBOX
		g.setColor(movable.getColor());
		g.drawRect(movable.getEntityIntX(), movable.getEntityIntY(), movable.getIntWidth(), movable.getIntHeight());
	    }
	   movable.draw(g);
	}
    }

    private void paintGUI(final Graphics g) {

	for (Movable movable : game.getMovables()) {
	   movable.drawHealth(g);
	}
	final int expBarLength = 165;
	final int expX = 5, expY = 60, expHeight = ABOVE_PLAYER_INCREMENT;
	final int expRequirement = game.getPlayer().getExpRequirements()[game.getPlayer().getLevel() - 1];
	// Paint EXP bar
	g.setColor(Color.BLACK);
	g.fillRect(expX, expY, expBarLength, expHeight);
	g.setColor(Color.GREEN);
	g.fillRect(expX, expY, game.getPlayer().getExp() * expBarLength / expRequirement,
		   expHeight);
	// Player hp bar
	paintPlayerHP(g);
    }

    /**
     * Paints the health of the player using heart sprites in the top left corner of the screen.
     *
     * @param g
     */
    private void paintPlayerHP(Graphics g) {

	int xCoord = 0;
	int yCoord = 0;
	int heartPos = 0;
	final int spaceBetweenHearts = 60;
	final int emptyHearts =  game.getPlayer().getMaxHp() / 2;
	while (heartPos < emptyHearts)  {
	    g.drawImage(game.getPlayer().emptyHeart, xCoord, yCoord, null);
	    heartPos++;
	    xCoord += spaceBetweenHearts;
	}
	xCoord = 0;
	heartPos = 0;
	while (heartPos < game.getPlayer().getHp()) {
	    g.drawImage(game.getPlayer().halfHeart, xCoord, yCoord, null);
	    heartPos++;
	    if (heartPos < game.getPlayer().getHp()) {
		g.drawImage(game.getPlayer().fullHeart, xCoord, yCoord, null);
	    }
	    heartPos++;
	    xCoord += spaceBetweenHearts;
	}
    }


    private void paintLevelUpAnimation(final Graphics g) {
	if (oldPlayerLevel < game.getPlayer().getLevel()) {
	    didPlayerLevel = true;
	    showSkills = true;
	}
	this.oldPlayerLevel = game.getPlayer().getLevel();
	if (this.indexOfLevelUpFrame >= LEVEL_UP_FRAMES) {
	    this.indexOfLevelUpFrame = 0;
	    didPlayerLevel = false;
	} else if (didPlayerLevel) {
	    Image currentFrame = game.getPlayer().levelUpFrames[this.indexOfLevelUpFrame];
	    g.drawImage(currentFrame, game.getPlayer().getEntityIntX(), game.getPlayer().getEntityIntY() - ABOVE_PLAYER_INCREMENT, null);
	    this.indexOfLevelUpFrame++;
	}
    }

    private void paintDecreeOverlay(final Graphics g) {

	Graphics2D g2 = (Graphics2D) g;
	//Draw one shape on left side of screen and rightside

	final int frameWidth = tileSize.x * 22;
	final int frameHeight = tileSize.y * 10;
	final int frameX = tileSize.x * 4;
	final int frameY = (int) (getPreferredSize().height / 2.5);


	//Sets decree types
	if (randomizeOnce) {
	    decree00 = new Decrees(decree00.getRandomDecree());
	    decree01 = new Decrees(decree01.getRandomDecree());
	    randomizeOnce = false;
	}


	// Draws background for decrees
	final int r = 0, gr = 0, b = 0, a = 210;
	final int arcWidth = 25, arcHeight = 25;
	Color color = new Color(r, gr, b, a);
	g2.setColor(color);
	g2.fillRoundRect(frameX, frameY, frameWidth, frameHeight, arcWidth, arcHeight);
	color = Color.WHITE;
	final int strokeWidth = 5;
	g2.setColor(color);
	g2.setStroke(new BasicStroke(strokeWidth));
	g2.drawRoundRect(frameX, frameY, frameWidth, frameHeight, arcWidth, arcHeight);

	//Adds decrees as clickable objects
	final int decreeWidth = 100;
	final int decreeHeight = 100;

	int decreeOneX = frameX + decreeWidth;
	int decreeOneY = frameY + frameHeight / 3;
	int decreeTwoX = decreeOneX + frameWidth - decreeWidth * 3; // need to have frameXY - 2 decreewidths...
	int decreeTwoY = decreeOneY;
	final int decreeFontSize = 20;
	//decree 1
	g2.setColor(Color.RED);
	g2.fillRect(decreeOneX, decreeOneY, decreeWidth, decreeHeight);
	String effect = decree00.getEffect();
	g2.setFont(new Font("Monospaced", Font.BOLD, decreeFontSize));
	FontMetrics fm = g2.getFontMetrics();
	int textWidth = fm.stringWidth(effect);
	final Point stringPos = new Point(decreeOneX + (decreeWidth - textWidth) / 2, decreeOneY - decreeFontSize);
	g2.drawString(effect, stringPos.x,stringPos.y );

	//decree 2
	g2.setColor(Color.BLUE);
	g2.fillRect(decreeTwoX, decreeTwoY, decreeWidth, decreeHeight);
	effect = decree01.getEffect();
	fm = g2.getFontMetrics();
	textWidth = fm.stringWidth(effect);

	final Point stringPosTwo = new Point(decreeTwoX + (decreeWidth - textWidth) / 2, decreeTwoY - decreeFontSize);
	g2.drawString(effect, stringPosTwo.x, stringPosTwo.y);

	this.addMouseListener(new MyMouseAdapter(decreeOneX, decreeWidth, decreeOneY, decreeHeight, decreeTwoX, decreeTwoY));
    }

    private int getFPS() {
	final double oneSecondInNano =  1_000_000_000.0;
	long currentTime = System.nanoTime();
	long elapsedNanos = currentTime - lastFrameTime;
	lastFrameTime = currentTime;
	double elapsedSeconds = elapsedNanos / oneSecondInNano;
	double currentFPS = 1.0 / elapsedSeconds;

	return (int) currentFPS;
    }

    private void paintDebug(final Graphics g) {
	final int x = 5, y = 15;
	g.drawString(String.valueOf(getFPS()), x, y);
    }

    private void paintMapLayer(final Graphics g, final int layer) {
	Room room = game.getRoom();
	if (layer >= room.getLayers()) {
	    throw new IllegalArgumentException("Can't draw the specified layer " + layer + " on map " + room.getName());
	}

	for (int y = 0; y < room.getRows(); y++) {
	    for (int x = 0; x < room.getColumns(); x++) {
		Tile tile = room.getTile(x, y, layer);
		if (tile != null) {
		    int tileX = x * tileSize.x;
		    int tileY = y * tileSize.y;
		    g.drawImage(tile.getImage(), tileX, tileY, tileSize.x, tileSize.y, null);
		}
	    }
	}
    }

    /**
     * This method is called when JFrame tries to pack all the components. Ensures the window size is the same as world size.
     */
    @Override public Dimension getPreferredSize() {
	int preferredWidth = game.getRoom().getWidth();
	int preferredHeight = game.getRoom().getHeight();
	return new Dimension(preferredWidth, preferredHeight);
    }


    /**
     * Adds new keystrokes to the input mapping
     *
     * @param keyStrokeString the keystroke as a string (for example "pressed SPACE")
     * @param action          which AbstractAction to be performed when the keystroke is used
     */
    private void addNewKeyBinding(String keyStrokeString, Action action) {
	this.getInputMap().put(KeyStroke.getKeyStroke(keyStrokeString), keyStrokeString);
	this.getActionMap().put(keyStrokeString, action);
    }

    private void setKeyBindings()
    {
	/**
	 * If any key is pressed, the game is unpaused
	 */
	addNewKeyBinding("pressed", new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		game.unpause();
	    }
	});
	//Primary Keys
	addNewKeyBinding("SPACE", new AttackAction());
	addNewKeyBinding("Z", new ShootAction());
	// if ESCAPE is pressed, the game is paused
	addNewKeyBinding("ESCAPE", new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		game.togglePause();
	    }
	});



	// Pressing keys
	addNewKeyBinding("pressed UP", new MoveAction(Direction.UP));
	addNewKeyBinding("pressed DOWN", new MoveAction(Direction.DOWN));
	addNewKeyBinding("pressed LEFT", new MoveAction(Direction.LEFT));
	addNewKeyBinding("pressed RIGHT", new MoveAction(Direction.RIGHT));

	// Releasing keys
	addNewKeyBinding("released UP", new StopAction(Direction.UP));
	addNewKeyBinding("released DOWN", new StopAction(Direction.DOWN));
	addNewKeyBinding("released LEFT", new StopAction(Direction.LEFT));
	addNewKeyBinding("released RIGHT", new StopAction(Direction.RIGHT));

	// Debug
	addNewKeyBinding("F1", new DebugAction());
	addNewKeyBinding("F2", new SkillsAction());
    }

    @Override public void frameChanged() {
	this.repaint();
    }

    private class MoveAction extends AbstractAction
    {
	private final Direction dir;

	private MoveAction(Direction dir) {
	    this.dir = dir;
	}

	public void actionPerformed(ActionEvent e) {
	    float maxSpeed = game.getPlayer().getMaxSpeed();
	    switch (dir) {
		case UP -> game.getPlayer().setVelY(-maxSpeed);
		case DOWN -> game.getPlayer().setVelY(maxSpeed);
		case RIGHT -> game.getPlayer().setVelX(maxSpeed);
		case LEFT -> game.getPlayer().setVelX(-maxSpeed);
	    }
	}
    }

    private class AttackAction extends AbstractAction
    {
	public void actionPerformed(ActionEvent e) {
	    game.playerSwordAttack();
	}
    }

    private class ShootAction extends AbstractAction
    {
	public void actionPerformed(ActionEvent e) {
	    game.playerShootArrow();
	}
    }

    private class SkillsAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    showSkills = !showSkills;
	    randomizeOnce = true;
	}
    }

    private class DebugAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    debug = !debug;
	}
    }

    private class StopAction extends AbstractAction
    {
	private final Direction dir;

	private StopAction(Direction dir) {
	    this.dir = dir;
	}

	public void actionPerformed(ActionEvent e) {
	    // Cancel velocity in appropiate direction!!
	    if (dir == Direction.UP && game.getPlayer().getVelY() < 0) {
		game.getPlayer().setVelY(0);
	    }
	    if (dir == Direction.DOWN && game.getPlayer().getVelY() > 0) {
		game.getPlayer().setVelY(0);
	    }
	    if (dir == Direction.RIGHT && game.getPlayer().getVelX() > 0) {
		game.getPlayer().setVelX(0);
	    }
	    if (dir == Direction.LEFT && game.getPlayer().getVelX() < 0) {
		game.getPlayer().setVelX(0);
	    }
	}
    }

    /**
     * This class is used to pause the game when ESCAPE is pressed, and resume when any other key is pressed.
     */
    private class PauseAction extends AbstractAction
    {
	private boolean shouldPause;

	private PauseAction(boolean shouldPause) {
	    this.shouldPause = shouldPause;
	}

	public void actionPerformed(final ActionEvent e) {
	    if (shouldPause) {
		game.pause();
	    } else {
		game.unpause();
	    }
	}
    }

    private class MyMouseAdapter extends MouseAdapter
    {
	private final int decreeOneX;
	private final int decreeWidth;
	private final int decreeOneY;
	private final int decreeHeight;
	private final int decreeTwoX;
	private final int decreeTwoY;

	private MyMouseAdapter(final int decreeOneX, final int decreeWidth, final int decreeOneY, final int decreeHeight,
			      final int decreeTwoX, final int decreeTwoY)
	{
	    this.decreeOneX = decreeOneX;
	    this.decreeWidth = decreeWidth;
	    this.decreeOneY = decreeOneY;
	    this.decreeHeight = decreeHeight;
	    this.decreeTwoX = decreeTwoX;
	    this.decreeTwoY = decreeTwoY;
	}

	public void mouseClicked(MouseEvent e) {
	    int x = e.getX();
	    int y = e.getY();
	    if (x >= decreeOneX && x <= decreeOneX + decreeWidth && y >= decreeOneY && y <= decreeOneY + decreeHeight) {
		// Code to execute when the red rectangle is clicked
		if (showSkills) {
		    game.getPlayer().addDecree(decree00);
		    showSkills = false;
		}
	    } else if (x >= decreeTwoX && x <= decreeTwoX + decreeWidth && y >= decreeTwoY && y <= decreeTwoY + decreeHeight) {
		// Code to execute when the blue rectangle is clicked
		if (showSkills) {
		    game.getPlayer().addDecree(decree01);
		    showSkills = false;
		}

	    }
	}
    }
}

