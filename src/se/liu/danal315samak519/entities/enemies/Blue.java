package se.liu.danal315samak519.entities.enemies;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.entities.Player;

import java.awt.geom.Point2D;

public class Blue extends Enemy
{
    public Blue(final Point2D.Double coord, final Player player) {
	super(coord, player);
	storeDirectionalFrames(120,0);
	setDir(Direction.DOWN);
    }

    private double facePlayer(){
	Point2D.Double thisCoord = getCoord();
	Point2D.Double playedCoord = player.getCoord();
	double dy = thisCoord.getY() - playedCoord.getY();
	double dx = thisCoord.getX() - playedCoord.getX();
	return Math.atan2(dy,dx);

    }
    private void setDirTowardsPlayer(double tangent){
	double degrees = Math.toDegrees(tangent);
	if (degrees < 0) {
	    degrees += 360;
	}
	int sector = (int) Math.round(degrees / 90) % 4;

	switch (sector) {
	    case 0:
		setDir(Direction.LEFT);
		break;
	    case 1:
		setDir(Direction.UP);
		break;
	    case 2:
		this.setDir(Direction.RIGHT);
		break;
	    case 3:
		this.setDir(Direction.DOWN);
		break;
	    default:
		System.out.println(sector);
	}
    }

    @Override public void tick() {
	super.tick();
	setDirTowardsPlayer(facePlayer());
    }
}