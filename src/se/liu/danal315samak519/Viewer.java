package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

public class Viewer
{
    private static final int TARGET_FPS = 120;
    private GameComponent gameComponent;
    private final Action doTimerTick = new AbstractAction()
    {
	@Override public void actionPerformed(final ActionEvent e)
	{
	    gameComponent.frameChanged();
	    gameComponent.game.tick();
	}
    };

    public void show() {
	// Initisalise frame
	JFrame frame = new JFrame("Gamers");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//Add component

	Player player = new Player(new Point2D.Double(300, 300));
	player.setMaxSpeed(5);
	Game game = new Game(player);
	game.setWorld(new World("map0.tmx"));
	game.addEnemy(200, 600);
	game.addEnemy(523, 321);
	game.addEnemy(132, 90);

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
	int millisDelay = 1000 / TARGET_FPS;
	final Timer clockTimer = new Timer(millisDelay, doTimerTick);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
}
