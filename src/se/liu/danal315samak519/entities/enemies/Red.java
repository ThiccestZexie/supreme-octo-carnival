package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

public class Red extends Enemy
{

    public Red(final Point2D.Double coord, final Player player) {
	super(coord, player);
	setMaxSpeed(3);
	setStats(3, 1);
	storeDirectionalFrames(0, 0);
	setDir(Direction.DOWN);
    }

    @Override public void tick() {
	super.tick();
	chasePlayer();
    }


}
