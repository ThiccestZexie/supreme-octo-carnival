package se.liu.danal315samak519;

import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.enemies.Enemy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Random;

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

	Game game = new Game();

	// Put game in gameComponent
	gameComponent = new GameComponent(game);
	frame.add(gameComponent);

	// Show it
	frame.setVisible(true);
	frame.pack();
	frame.toFront(); // FORCE THE GAME TO START ON TOP OF OTHER WINDOWS
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
