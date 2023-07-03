package se.liu.danal315samak519;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Initiates the game and starts the timer.
 */
public class Viewer
{
    private static final int TARGET_FPS = 60;
    private GameComponent gameComponent = new GameComponent();

    /**
     * Creates new frame, shows it and starts the timer.
     */
    public void show() {
	// Create frame
	JFrame frame = new JFrame("Rogue Runner");
	frame.setIconImage(ImageLoader.loadImage("icon.png"));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.add(gameComponent);

	// Show frame
	frame.setVisible(true);
	frame.pack();
	frame.toFront(); // FORCE THE GAME TO START ON TOP OF OTHER WINDOWS
	startTimer();
    }

    /**
     * Starts the timer for game ticks.
     */
    public void startTimer() {
	int millisDelay = 1000 / TARGET_FPS;
	ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	executorService.scheduleAtFixedRate(this::doTimerTick, 0, millisDelay, TimeUnit.MILLISECONDS);
    }

    private void doTimerTick() {
	gameComponent.frameChanged();
	gameComponent.game.tick();
    }
}
