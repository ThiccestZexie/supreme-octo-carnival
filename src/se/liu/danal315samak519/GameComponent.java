package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent
    // Create the main Gamecomponent (Game screen with map, entities, gui)...
{
    private GameMap map;
    private Color fallBackColor = Color.BLACK;
    private GamePlayer player = new GamePlayer("Daniel", new Point(50, 50)); //TODO

    public GameComponent(GameMap map)
    {
	this.map = map;
    }

    public void addPlayer(final GamePlayer player) {
	this.player = player;
    }

    public void repaintMap(final Rectangle r) {
		this.repaint(r);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	Point playerCoord = player.getCoord();
	Dimension playerSize = player.getSize();
	g.setColor(Color.BLACK);
	g.fillRect(playerCoord.x, playerCoord.y, playerSize.width, playerSize.height);
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
