package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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



    public void repaintMap(final Rectangle r) {
	this.repaint(r);
    }

    private void paintPlayer(final Graphics g){
	GamePlayer player = game.getPlayer();
	Point playerCoord = player.getCoord();
	Dimension playerSize = player.getSize();
	g.setColor(Color.BLACK);
	g.fillRect(playerCoord.x, playerCoord.y, playerSize.width, playerSize.height);
    }


    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	paintPlayer(g);
	paintMap(g);
    }

    private void paintMap(final Graphics g) {
    }


	public void moveDir(KeyEvent e){
	    if(e.getKeyCode() == 39 ){
		game.nudgePlayer(5,0 );
	    }
	    if(e.getKeyCode() == 40 ){
		game.nudgePlayer(0,5 );
	    }
	    if(e.getKeyCode() == 38 ){
		game.nudgePlayer(0,-5 );
	    }
	    if(e.getKeyCode() == 37 ){
		game.nudgePlayer(-5,0 );
	    }
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
		switch (dir){
		    case UP, DOWN -> game.setPlayerVelocity(game.getPlayerVelocityX(), 0);
		    case RIGHT, LEFT -> game.setPlayerVelocity(0, game.getPlayerVelocityY());
		}
		}
    }

//    public boolean setMap(GameMap map){
//	if(changing map is allowed){
//	    this.map = map;
//	    return true;
//	}
//	else{
//	    return false;
//	}
//    }




}
