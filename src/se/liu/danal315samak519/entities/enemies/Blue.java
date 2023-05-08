package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

public class Blue extends Enemy
{
    public Blue(final Point2D.Double coord, final Player player) {
	super(coord, player);
	setStats(2, 1);
	storeDirectionalFrames(120,0);
	setDir(Direction.DOWN);
    }

    @Override public void tick() {
	super.tick();
	setDir(facePlayer());
    }

    private Direction facePlayer() {
	return Direction.getDirectionBetweenPoints(this.getCoord(), player.getCoord());
    }
}