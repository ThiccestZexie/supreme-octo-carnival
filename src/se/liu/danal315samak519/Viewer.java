package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Viewer
{
    private static final int TIMER_DELAY = 17;
    private GameComponent gameComponent;

    public void show() {
	// Initisalise frame
	JFrame frame = new JFrame("Gamers");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//Add component

	Player player = new Player("DanelSamuel", new Point(10, 10), Color.green);
	player.setMaxSpeed(5);
	Game game = new Game(player);
	game.setWorld(new World("map0.tmx", "TX Tileset Grass.png"));
	game.addEnemy(new Point(100, 600));
	game.addEnemy(new Point(523, 321));
	game.addEnemy(new Point(132, 60));

	gameComponent = new GameComponent(game);
	frame.add(gameComponent);

	// Show it
	frame.setVisible(true);
	frame.pack();
	// FORCE THE GAME TO START ON TOP OF OTHER WINDOWS
	frame.toFront();
	startTimer();
    }

    public void startTimer()
    {
	final Timer clockTimer = new Timer(TIMER_DELAY, doTimerTick);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
    private final Action doTimerTick = new AbstractAction()
    {
	@Override public void actionPerformed(final ActionEvent e)
	{
	    gameComponent.frameChanged();
	    gameComponent.game.tick();
	}
    };
}
