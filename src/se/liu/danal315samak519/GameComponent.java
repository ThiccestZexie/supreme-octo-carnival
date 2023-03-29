package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent implements FrameListener
{
    // Create the main Gamecomponent (Game screen with map, entities, gui)...

    private Game game;
    public GameComponent(Game game)
    {
	this.map = map;
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
