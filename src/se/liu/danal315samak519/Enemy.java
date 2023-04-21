package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;

public class Enemy extends Entity
{

    private Player player;

    public Enemy(final Point coord, final Player player)
    {
	super(coord, Color.red);
	this.player = player;
	this.maxSpeed = 3;
    }

    public Point2D.Double getVelocityTowardsPlayer() {
	Point playerCoord = player.getCoord();
	Point enemyCoord = this.getCoord();
	Double x = playerCoord.getX() - enemyCoord.getX();
	Double y = playerCoord.getY() - enemyCoord.getY();
	Double magn = Math.sqrt(x * x + y * y);
	Point2D.Double velocity = new Point2D.Double(maxSpeed * x / magn, maxSpeed * y / magn);
	return velocity;
    }

    private Double distanceToPlayer() {
	return player.getCoord().distance(this.getCoord());
    }

    public boolean canSeePlayer() {
	if (distanceToPlayer() < 500) {
	    return true;
	}
	return false;
    }

    public boolean wantToAttack()
    {
	if (distanceToPlayer() < 60) {
	    return true;
	}
	return false;
    }

    @Override public void tick() {
	super.tick();
	if (canSeePlayer()) { // CHASe PLAYER!!
	    Point2D velocity = getVelocityTowardsPlayer();
	    setCurrentVelocity((int) velocity.getX(), (int) velocity.getY());
	} else { // Chill...
	    setCurrentVelocity(0, 0);
	}
    }
}
