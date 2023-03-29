package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameViewer
{
    private GameComponent gameComponent;

    private static final int TIMER_DELAY = 500;

    public void show(){
	// Initisalise frame
	JFrame frame = new JFrame("Gamers");
	frame.setPreferredSize(new Dimension(400, 300));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//Add component
	Game game = new Game(GameMap.START, new GamePlayer("DanelSamuel", new Point(10,10)));
	gameComponent = new GameComponent(game);
	frame.add(gameComponent);

	// Show it
	startTimer();
	frame.setVisible(true);
	frame.pack();
    }

    private final Action doSomething = new AbstractAction()
    {
	    @Override public void actionPerformed(final ActionEvent e)
	    {
			gameComponent.frameChanged();
	    }
    };

    public void startTimer()
    {
	    final Timer clockTimer = new Timer(TIMER_DELAY, doSomething);
	    clockTimer.setCoalesce(true);
	    clockTimer.start();
    }
}
