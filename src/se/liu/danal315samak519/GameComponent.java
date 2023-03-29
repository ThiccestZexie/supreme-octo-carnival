package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameComponent extends JComponent implements FrameListener
{
    // Create the main Gamecomponent (Game screen with map, entities, gui)...
    private final int CONSTANTNUDGE = 1;

    public Game game;

    public GameComponent(Game game)
    {
	setKeyBindings();
	this.game = game;
	game.addFrameListener(this);

    }


    private void paintPlayer(final Graphics g){
	GamePlayer player = game.getPlayer();
	Point playerCoord = player.getCoord();
	Dimension playerSize = player.getSize();
	g.setColor(player.getColor());
	g.fillRect(playerCoord.x, playerCoord.y, playerSize.width, playerSize.height);
    }

    private void paintEntities(final Graphics g){
	paintPlayer(g);
	for (GameEntity gE: game.gameEntityList) {
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

    private void setKeyBindings()
    {
	    this.getInputMap().put(KeyStroke.getKeyStroke("pressed UP"), "moveUp");
	    this.getInputMap().put(KeyStroke.getKeyStroke("pressed DOWN"), "moveDown");
	    this.getInputMap().put(KeyStroke.getKeyStroke("pressed LEFT"), "moveLeft");
	    this.getInputMap().put(KeyStroke.getKeyStroke("pressed RIGHT"), "moveRight");

	    this.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "stopUp");
	    this.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "stopDown");
	    this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "stopLeft");
	    this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "stopRight");

	    this.getActionMap().put("moveUp", new ActionMove(Direction.UP));
	    this.getActionMap().put("moveDown", new ActionMove(Direction.DOWN));
	    this.getActionMap().put("moveLeft", new ActionMove(Direction.LEFT));
	    this.getActionMap().put("moveRight", new ActionMove(Direction.RIGHT));

	    this.getActionMap().put("stopUp", new ActionStop(Direction.UP));
	    this.getActionMap().put("stopDown", new ActionStop(Direction.DOWN));
	    this.getActionMap().put("stopLeft", new ActionStop(Direction.LEFT));
	    this.getActionMap().put("stopRight", new ActionStop(Direction.RIGHT));
    }

    @Override public void frameChanged() {
	this.repaint();
    }

	    private class ActionMove extends AbstractAction{
			private final Direction dir;
			private ActionMove(Direction dir){
			    this.dir = dir;
			}
			public void actionPerformed(ActionEvent e) {
				switch (dir){
				    case UP -> game.setPlayerVelocity(game.getPlayerVelocityX(), -CONSTANTNUDGE);
				    case DOWN -> game.setPlayerVelocity(game.getPlayerVelocityX(), CONSTANTNUDGE);
				    case RIGHT -> game.setPlayerVelocity(CONSTANTNUDGE, game.getPlayerVelocityY());
				    case LEFT -> game.setPlayerVelocity(-CONSTANTNUDGE, game.getPlayerVelocityY());
				}
			}
	    }
    private class ActionStop extends AbstractAction{
		private final Direction dir;
		private ActionStop(Direction dir){
		    this.dir = dir;
	}
	    public void actionPerformed(ActionEvent e) {
		    if(dir == Direction.UP && game.getPlayerVelocityY() < 0){
			game.setPlayerVelocityY(0);
		    }
		    if(dir == Direction.DOWN && game.getPlayerVelocityY() > 0){
			game.setPlayerVelocityY(0);
		    }
		    if(dir == Direction.RIGHT && game.getPlayerVelocityX() > 0){
			game.setPlayerVelocityX(0);
		    }
		    if(dir == Direction.LEFT && game.getPlayerVelocityX() < 0){
			game.setPlayerVelocityX(0);
		    }
		}
    }
}
