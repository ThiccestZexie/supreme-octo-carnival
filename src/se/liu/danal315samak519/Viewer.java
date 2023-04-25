package se.liu.danal315samak519;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
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
	// Game
	Game game = new Game();
	// Player
	Player player = new Player(new Point2D.Double(500, 500));
	player.setMaxSpeed(5);
	game.setPlayer(player);
	// World
	game.setWorld(new World("map0.tmx"));
	Random random = new Random();
	// Enemies
	for (int i = 0; i < 7; i++) {
	    int randomX = 200 + random.nextInt(400);
	    int randomY = 200 + random.nextInt(400);
	    Point2D.Double randomCoord = new Point2D.Double(randomX, randomY);
	    game.addEnemy(randomCoord);
	}

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
