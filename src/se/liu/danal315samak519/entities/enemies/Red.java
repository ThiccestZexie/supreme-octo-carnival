package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

public class Red extends Enemy
{

    public Red(final Point2D.Double coord, final Player player) {
	super(coord, player);
	storeDirectionalFrames(0, 0);
	setDir(Direction.DOWN);
    }

    @Override public void tick() {
	super.tick();
	chasePlayer();
    }

    public void chasePlayer(){
	if (canSeePlayer()) { // CHASe PLAYER!!
	    Point2D velocity = getVelocityTowardsPlayer();
	    setVelocity((int) velocity.getX(), (int) velocity.getY());
	} else { // Chill...
	    setVelocity(0, 0);
	}
    }
}
