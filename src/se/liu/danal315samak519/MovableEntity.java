package se.liu.danal315samak519;
import java.awt.geom.Point2D;

/**
 *
 */
public abstract class MovableEntity extends Entity
{
    protected Direction dir;
    protected double maxSpeed;
    protected double velX;
    protected double velY;

    protected MovableEntity(final Point2D.Double coord) {
	super(coord);
	setMaxSpeed(5);
    }

    public Direction getDir() {
	return dir;
    }

    public void setDir(final Direction dir) {
	this.dir = dir;
    }

    public double getMaxSpeed() {
	return maxSpeed;
    }

    public void setMaxSpeed(final int speed) {
	this.maxSpeed = speed;
    }

    public void nudge(final double dx, final double dy) {
	setLocation(getX() + dx, getY() + dy);
	nudgeHitBox(dx, dy);
    }

    public void setVelocity(final double vx, final double vy) {
	setVelX(vx);
	setVelY(vy);
    }

    public void setVelY(final double vy){
	this.velY = vy;
	setAppropiateDir();
    }

    public void setVelX(final double vx){
	this.velX = vx;
	setAppropiateDir();
    }

    private void setAppropiateDir() {
	Direction newDirection = DirectionUtil.velocityToDirection(getVelX(), getVelY());
	if(newDirection != null){
	    setDir(newDirection);
	}
    }

    public void tick() {
	nudge(velX, velY);
    }

    private void nudgeHitBox(final double dx, final double dy) {
	setLocationOfHitBox(hitBox.getX() + dx, hitBox.getY() + dy);
    }

    public double getVelX() {
	return velX;
    }

    public double getVelY() {
	return velY;
    }

    public boolean getIsGarbage() {
	return isGarbage;
    }
}
