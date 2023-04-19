package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class GameComponent extends JComponent implements FrameListener
{
    private static final int CONSTANTNUDGE = 2;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    public Game game;

    public int i = 0;
    public boolean didPlayerLevel = false;
    int oldPlayerLevel;

    public GameComponent(Game game)
    {
	this.game = game;
	setKeyBindings();
	game.addFrameListener(this);
	oldPlayerLevel = game.getPlayer().getLevel();
    }

    private void paintPlayer(final Graphics g) {
	Player player = game.getPlayer();
	Point playerCoord = player.getCoord();
	Dimension playerSize = player.getSize();
	g.setColor(player.getColor());
	g.fillRect(playerCoord.x, playerCoord.y, playerSize.width, playerSize.height);
    }

    private void paintEntities(final Graphics g) {
	for (Entity gE : game.getEntityList()) {
	    Point entityCoord = gE.getCoord();
	    Dimension entitySize = gE.getSize();
	    g.setColor(gE.getColor());
	    g.fillRect(entityCoord.x, entityCoord.y, entitySize.width, entitySize.height);
	}

    }

    private void paintGUI(final Graphics g) {

	for (Entity gE : game.getEntityList()) {
	    if (gE instanceof Enemy) {
		// Maxhealth (BLACK)
		g.setColor(Color.BLACK);
		int blackWidth = gE.getMaxHp() * 60;
		int blackHeight = 20;
		int blackX = gE.getX() - blackWidth / 2 + gE.getWidth() / 2;
		int blackY = gE.getY() + gE.getHeight() + 15;
		g.fillRect(blackX, blackY, blackWidth, blackHeight); // Should be get maxHp

		//Current health (RED)
		int redWidth = gE.getHp() * 60;
		int redHeight = 20;
		int redX = blackX;
		int redY = blackY;
		g.setColor(Color.RED);
		g.fillRect(gE.getX() - blackWidth / 2 + gE.getWidth() / 2, gE.getY() + gE.getHeight() + 15, redWidth, redHeight);
	    }
	}
	int expBarLength = 100;

	g.setColor(Color.BLACK);
	g.fillRect(20,20,expBarLength, 30);
	g.setColor(Color.GREEN);
	g.fillRect(20,20,
		   game.getPlayer().exp * expBarLength/game.getPlayer().getExpRequirements()[game.getPlayer().getLevel()-1], 30);
    }

    private void paintLevelUPAnimation(final Graphics g){
	BufferedImage image = null;

	if (oldPlayerLevel < game.getPlayer().getLevel()){
	    didPlayerLevel = true;
	}
	this.oldPlayerLevel = game.getPlayer().getLevel();
	if (this.i >= 19)
	{
	    this.i = 0;
	    didPlayerLevel = false;
	}
	else if (didPlayerLevel){
	    g.drawImage(game.getPlayer().frames.get(this.i), game.getPlayer().getX(), game.getPlayer().getY() - 30, null);
	    this.i++;
	}


    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);

	paintMap(g);
	paintEntities(g);
	paintPlayer(g);
	paintGUI(g);
	paintLevelUPAnimation(g);

    }

    private void paintMap(final Graphics g) {
	World world = game.getWorld();
	for (int row = 0; row < world.getRows(); row++) {
	    for (int col = 0; col < world.getColumns(); col++) {
		Tile tile = world.getTile(col, row);
		int tileX = tile.getCoord().x;
		int tileY = tile.getCoord().y;
		int tileWidth = tile.getWidth();
		int tileHeight = tile.getHeight();
		g.drawImage(tile.getImage(), tileX, tileY, tileWidth, tileHeight, null);
	    }
	}
    }

    /**
     * This method is called when JFrame tries to pack all the components. Ensures the window size is the same as world size.
     */
    @Override public Dimension getPreferredSize() {
	int preferredWidth = game.getWorld().getRows() * TILE_WIDTH;
	int preferredHeight = game.getWorld().getColumns() * TILE_HEIGHT;
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
	    int maxSpeed = game.getPlayer().getMaxSpeed();
	    switch (dir) {
		case UP -> game.setPlayerVelY(-maxSpeed);
		case DOWN -> game.setPlayerVelY(maxSpeed);
		case RIGHT -> game.setPlayerVelX(maxSpeed);
		case LEFT -> game.setPlayerVelX(-maxSpeed);
	    }
	    game.setPlayerDirection(dir);
	}
    }

    private class AttackAction extends AbstractAction
    {
	public void actionPerformed(ActionEvent e) {
	    game.addPlayerSword();
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
	    if (dir == Direction.UP && game.getPlayerVelY() < 0) {
		game.setPlayerVelY(0);
	    }
	    if (dir == Direction.DOWN && game.getPlayerVelY() > 0) {
		game.setPlayerVelY(0);
	    }
	    if (dir == Direction.RIGHT && game.getPlayerVelX() > 0) {
		game.setPlayerVelX(0);
	    }
	    if (dir == Direction.LEFT && game.getPlayerVelX() < 0) {
		game.setPlayerVelX(0);
	    }
	}
    }
}
