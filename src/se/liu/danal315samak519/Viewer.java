package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Initiates the game and starts the timer.
 */
public class Viewer
{
    private static final int TARGET_FPS = 120;
    private GameComponent gameComponent = new GameComponent();
    private final Action doTimerTick = new AbstractAction()
    {
	@Override public void actionPerformed(final ActionEvent e)
	{
	    gameComponent.frameChanged();
	    gameComponent.game.tick();
	}
    };

    /**
     * Creates new frame, shows it and starts the timer.
     */
    public void show() {
	// Create frame
	JFrame frame = new JFrame("Gamers");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(gameComponent);

	// Show frame
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
