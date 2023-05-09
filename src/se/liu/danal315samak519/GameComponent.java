package se.liu.danal315samak519;

import se.liu.danal315samak519.entities.Character;
import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Player;
import se.liu.danal315samak519.entities.Potion;
import se.liu.danal315samak519.entities.enemies.Enemy;
import se.liu.danal315samak519.map.Tile;
import se.liu.danal315samak519.map.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameComponent extends JComponent implements FrameListener
{
    public Game game;
    public int i = 0;
    public boolean didPlayerLevel = false;
    int oldPlayerLevel;
    private int tileWidth;
    private int tileHeight;
    private boolean debug = true;

    private boolean showSkills = false;
    private long lastFrameTime;

    public GameComponent(Game game)
    {
	this.game = game;
	this.tileWidth = game.getWorld().getTileWidth();
	this.tileHeight = game.getWorld().getTileHeight();
	setKeyBindings();
	game.addFrameListener(this);
	oldPlayerLevel = game.getPlayer().getLevel();
    }

    private void paintPlayer(final Graphics g) {
	Player player = game.getPlayer();

	if (debug) {
	    // PAINT HITBOX
	    g.setColor(player.getColor());
	    g.drawRect(player.getIntX(), player.getIntY(), player.getIntWidth(), player.getIntHeight());
	}

	paintLevelUpAnimation(g);
	// PAINT SPRITE
	g.drawImage(player.getCurrentSprite(), player.getIntX(), player.getIntY(), player.getIntWidth(), player.getIntHeight(), null);

    }

    private void paintEntities(final Graphics g) {
	for (Movable movable : game.getMovables()) {
	    if (debug) {
		// PAINT HITBOX
		g.setColor(movable.getColor());
		g.drawRect(movable.getIntX(), movable.getIntY(), movable.getIntWidth(), movable.getIntHeight());
	    }
	    if (movable instanceof Character) {
		Character character = (Character) movable;
		// PAINT SPRITE
		g.drawImage(character.getCurrentSprite(), character.getIntX(), character.getIntY(), character.getIntWidth(),
			    character.getIntHeight(), null);
	    }
	    if (movable instanceof Potion) {
		Potion potion = (Potion) movable;
		g.drawImage(potion.getFullHeart(), potion.getIntX(), potion.getIntY(), potion.getIntWidth(), potion.getIntHeight(), null);
	    }
	}
    }

    private void paintGUI(final Graphics g) {

	for (Movable movable : game.getMovables()) {
	    if (movable instanceof Enemy) {
		Enemy enemy = (Enemy) movable;
		// Maxhealth (BLACK)
		g.setColor(Color.BLACK);
		int blackWidth = enemy.getMaxHp() * 60;
		int blackHeight = 20;
		int blackX = enemy.getIntX() - blackWidth / 2 + enemy.getIntWidth() / 2;
		int blackY = enemy.getIntY() + enemy.getIntHeight() + 15;
		g.fillRect(blackX, blackY, blackWidth, blackHeight); // Should be getInt maxHp

		//Current health (RED)
		int redWidth = enemy.getHp() * 60;
		int redHeight = 20;
		int redX = blackX;
		int redY = blackY;
		g.setColor(Color.RED);
		g.fillRect(enemy.getIntX() - blackWidth / 2 + enemy.getIntWidth() / 2, enemy.getIntY() + enemy.getIntHeight() + 15,
			   redWidth, redHeight);
	    }
	}
	int expBarLength = 165;

	// Paint EXP bar
	g.setColor(Color.BLACK);
	g.fillRect(5, 60, expBarLength, 30);
	g.setColor(Color.GREEN);
	g.fillRect(5, 60, game.getPlayer().getExp() * expBarLength / game.getPlayer().getExpRequirements()[game.getPlayer().getLevel() - 1],
		   30);
	// Player hp bar
	drawPlayerLife(g);
    }

    public void drawPlayerLife(Graphics g) {
	int fullHearts = game.getPlayer().getHp() / 2;
	int halfHearts = game.getPlayer().getHp() % 2;
	int xCoord = 0;
	int yCoord = 0;
	int heartPos = 0;
	int spaceBetweenHearts = 60;
	while (heartPos < game.getPlayer().getMaxHp() / 2) {

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
	}
	this.oldPlayerLevel = game.getPlayer().getLevel();
	if (this.i >= 19) {
	    this.i = 0;
	    didPlayerLevel = false;
	} else if (didPlayerLevel) {
	    Image currentFrame = game.getPlayer().levelUpFrames[this.i];
	    g.drawImage(currentFrame, game.getPlayer().getIntX(), game.getPlayer().getIntY() - 30, null);
	    this.i++;
	}
    }

    private void paintOverlay(final Graphics g) {

	Graphics2D g2 = (Graphics2D) g;
	//Draw one shape on left side of screen and rightside

	int frameWidth = tileWidth * 22;
	int frameHeight = tileHeight * 10;
	int frameX = tileWidth * 4;
	int frameY = (int) (getPreferredSize().height / 2.5);
	Color color = new Color(0, 0, 0, 210);
	g2.setColor(color);
	g2.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 35, 35);

	Decrees decree00 = new Decrees(1);
	Decrees decree01 = new Decrees(2);

	color = new Color(255, 255, 255);
	g2.setColor(color);
	g2.setStroke(new BasicStroke(5));
	g2.drawRoundRect(frameX + 5, frameY + 5, frameWidth - 10, frameHeight - 10, 25, 25);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	paintMapLayer(g, 0); // Paint background
	paintEntities(g);
	paintPlayer(g);
	paintMapLayer(g, 1); // Paint foreground
	paintGUI(g);
	if (showSkills) {
	    paintOverlay(g);
	}
	if (debug) {
	    paintDebug(g);
	}
    }

    private int getFPS() {
	long currentTime = System.nanoTime();
	long elapsedNanos = currentTime - lastFrameTime;
	lastFrameTime = currentTime;
	double elapsedSeconds = elapsedNanos / 1_000_000_000.0;
	double currentFPS = 1.0 / elapsedSeconds;

	return (int) currentFPS;
    }

    private void paintDebug(final Graphics g) {
	g.drawString(String.valueOf(getFPS()), 5, 15);
    }

    private void paintMapLayer(final Graphics g, final int layer) {
	World world = game.getWorld();
	if (layer >= world.getLayers()) {
	    throw new IllegalArgumentException("Can't draw the specified layer " + layer + " on map " + world.getName());
	}

	for (int y = 0; y < world.getRows(); y++) {
	    for (int x = 0; x < world.getColumns(); x++) {
		Tile tile = world.getTile(x, y, layer);
		if (tile != null) {
		    int tileX = x * tileWidth;
		    int tileY = y * tileHeight;
		    g.drawImage(tile.getImage(), tileX, tileY, tileWidth, tileHeight, null);
		}
	    }
	}
    }

    /**
     * This method is called when JFrame tries to pack all the components. Ensures the window size is the same as world size.
     */
    @Override public Dimension getPreferredSize() {
	int preferredWidth = game.getWorld().getRows() * tileWidth;
	int preferredHeight = game.getWorld().getColumns() * tileHeight;
	return new Dimension(preferredWidth, preferredHeight);
    }


    /**
     * Adds new keystrokes to the input mapping
     *
     * @param keyStrokeString the keystroke as a string (for example "pressed SPACE")
     * @param action          which AbstractAction to be performed when the keystroke is used
     */
    private void addNewKeyBinding(String keyStrokeString, AbstractAction action) {
	this.getInputMap().put(KeyStroke.getKeyStroke(keyStrokeString), keyStrokeString);
	this.getActionMap().put(keyStrokeString, action);
    }

    private void setKeyBindings()
    {
	//Primary Keys
	addNewKeyBinding("SPACE", new AttackAction());
	addNewKeyBinding("Z", new ShootAction());

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
	    double maxSpeed = game.getPlayer().getMaxSpeed();
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

}
