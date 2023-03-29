package se.liu.danal315samak519;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameInputHandler extends KeyAdapter
{

    @Override
    public void keyPressed(KeyEvent e) {

	if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	    System.out.println("Right key pressed");
	}
	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	    System.out.println("Left key pressed");
	}

    }
}
