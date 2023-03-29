package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameViewer
{
    private GameComponent gameComponent;

    private static final int TIMER_DELAY = 2;

    public void show(){
	// Initisalise frame
	JFrame frame = new JFrame("Gamers");
	frame.setPreferredSize(new Dimension(800, 400));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//Add component
	GameInputHandler gameInputHandler = new GameInputHandler();
	Game game = new Game(GameMap.START, new GamePlayer("DanelSamuel", new Point(10,10), Color.green));
	game.addEnemy(new Point(100, 600));
	game.addEnemy(new Point(523, 321));
	game.addEnemy(new Point(132, 60));

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
			gameComponent.game.tick();
	    }
    };

    public void startTimer()
    {
	    final Timer clockTimer = new Timer(TIMER_DELAY, doSomething);
	    clockTimer.setCoalesce(true);
	    clockTimer.start();
    }
}
