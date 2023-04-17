package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameComponent extends JComponent implements FrameListener
{
    private static final int CONSTANTNUDGE = 1;
    public Game game;

    public GameComponent(Game game)
    {
	setKeyBindings();
	this.game = game;
	game.addFrameListener(this);
   }

    private void paintPlayer(final Graphics g) {
	Player player = game.getPlayer();
	Point playerCoord = player.getCoord();
	Dimension playerSize = player.getSize();
	g.setColor(player.getColor());
	g.fillRect(playerCoord.x, playerCoord.y, playerSize.width, playerSize.height);
    }

    private void paintEntities(final Graphics g) {
	paintPlayer(g);
	for (Entity gE : game.getEntityList()) {
	    Point entityCoord = gE.getCoord();
	    Dimension entitySize = gE.getSize();
	    g.setColor(gE.getColor());
	    g.fillRect(entityCoord.x, entityCoord.y, entitySize.width, entitySize.height);
	}

    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	paintEntities(g);
	paintMap(g);
    }

    private void paintMap(final Graphics g) {
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
	    int speed = game.getPlayer().getSpeed();
	    switch (dir) {
		case UP -> game.setPlayerVelY(-speed);
		case DOWN -> game.setPlayerVelY(speed);
		case RIGHT -> game.setPlayerVelX(speed);
		case LEFT -> game.setPlayerVelX(-speed);
	    }
	    game.setPlayerDirection(dir);
	}
    }
	private class AttackAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
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
