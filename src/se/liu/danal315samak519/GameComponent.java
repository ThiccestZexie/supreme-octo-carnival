package se.liu.danal315samak519;

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
	for (MovableEntity movableEntity : game.getEntityList()) {
	    if (debug) {
		// PAINT HITBOX
		g.setColor(movableEntity.getColor());
		g.drawRect(movableEntity.getIntX(), movableEntity.getIntY(), movableEntity.getIntWidth(), movableEntity.getIntHeight());
	    }
	    if (movableEntity instanceof Character) {
		Character character = (Character) movableEntity;
		// PAINT SPRITE
		g.drawImage(character.getCurrentSprite(), character.getIntX(), character.getIntY(), character.getIntWidth(),
			    character.getIntHeight(), null);
	    }
	}
    }

    private void paintGUI(final Graphics g) {

	for (MovableEntity movableEntity : game.getEntityList()) {
	    if (movableEntity instanceof Enemy) {
		Enemy enemy = (Enemy) movableEntity;
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
	int expBarLength = 100;

	// Paint EXP bar
	g.setColor(Color.BLACK);
	g.fillRect(20, 20, expBarLength, 30);
	g.setColor(Color.GREEN);
	g.fillRect(20, 20, game.getPlayer().exp * expBarLength / game.getPlayer().getExpRequirements()[game.getPlayer().getLevel() - 1],
		   30);
	// Player hp bar
	int healthBarLength = 100;
	g.setColor(Color.BLACK);
	g.fillRect(20, 60, healthBarLength, 30);
	g.setColor(Color.RED);
	g.fillRect(20,60, game.getPlayer().getHp() * (healthBarLength/game.getPlayer().getMaxHp()), 30);
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

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	paintMapLayer(g,0); // Paint background
	paintEntities(g);
	paintPlayer(g);
	paintMapLayer(g,1); // Paint foreground
	paintGUI(g);

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
	for (int y = 0; y < world.getRows(); y++) {
	    for (int x = 0; x < world.getColumns(); x++) {
		Tile tile = world.getTile(x, y, layer);
		if(tile != null){
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
	addNewKeyBinding("Z", new ArrowAction());

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
	    game.playerAttack();
	}
    }
    private class ArrowAction extends AbstractAction
    {
	public void actionPerformed(ActionEvent e) {
	    game.playerShootArrow();
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
