package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameViewer
{

    private static final int TIMER_DELAY = 500;

    public void show(){
	// Initisalise frame
	JFrame frame = new JFrame("Gamers");
	frame.setPreferredSize(new Dimension(400, 300));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//Add component
	GameComponent gameComponent = new GameComponent(GameMap.START);

	gameComponent.addPlayer(new GamePlayer("DanielSamuel", new Point(50, 50)));
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
		    System.out.println("hi");
	    }
    };

    public void startTimer()
    {
	    final Timer clockTimer = new Timer(TIMER_DELAY, doSomething);
	    clockTimer.setCoalesce(true);
	    clockTimer.start();
    }
}
