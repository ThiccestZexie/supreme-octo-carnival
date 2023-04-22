package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class Entity
{
    protected Dimension size;
    protected Point2D.Double coord;
    protected Direction dir;
    protected int maxSpeed;
    protected int velX;
    protected int velY;
    protected Color color;
    protected Rectangle2D hitBox;
    protected boolean isGarbage = false;

    protected Entity(final Point2D.Double coord) {
	setLocation(coord);
	setMaxSpeed(5);
    }

    private void setLocation(final Point2D.Double coord) {
	if (this.coord != null) {
	    this.coord.setLocation(coord);
	} else {
	    this.coord = coord;
	}
    }

    public Direction getDir() {
	return dir;
    }

    public void setDir(final Direction dir) {
	this.dir = dir;
    }

    public int getIntWidth() {
	return (int) getWidth();
    }

    public int getIntHeight() {
	return (int) getHeight();
    }

    public int getIntX() {
	return (int) getX();
    }

    public int getIntY() {
	return (int) getY();
    }

    protected void setLocationOfHitBox(double x, double y) {
	setHitBox(x, y, getWidth(), getHeight());
    }

    protected void setLocationOfHitBox(Point2D point) {
	setHitBox(point.getX(), point.getY(), getWidth(), getHeight());
    }

    protected void setHitBox(double x, double y, double w, double h) {
	if (hitBox != null) {
	    this.hitBox.setRect(x, y, w, h);
	} else {
	    this.hitBox = new Rectangle2D.Double(x, y, w, h);
	}
    }

    protected void setHitBox() {
	setHitBox(getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle2D getHitBox() {
	return this.hitBox;
    }

    public Point2D.Double getCoord() {
	return coord;
    }

    public double getX() {
	return coord.getX();
    }

    public double getY() {
	return coord.getY();
    }

    public Color getColor() {
	return color;
    }

    public Dimension getSize() {
	return size;
    }

    public double getWidth() {
	return size.getWidth();
    }

    public double getHeight() {
	return size.getHeight();
    }

    public int getMaxSpeed() {
	return maxSpeed;
    }

    public void setMaxSpeed(final int speed) {
	this.maxSpeed = speed;
    }

    public void nudge(final int dx, final int dy) {
	setLocation(getX() + dx, getY() + dy);
    }

    public void setLocation(final double x, final double y) {
	if (coord != null) {
	    coord.setLocation(x, y);
	} else {
	    coord = new Point2D.Double(x, y);
	}
    }

    public void setCurrentVelocity(final int vx, final int vy) {
	this.velX = vx;
	this.velY = vy;
	setAppropiateDir();
    }

    private void setAppropiateDir() {
	if(getVelX() == 0 && getVelY() == 0){
	    return; // Do nothing
	}
	float angle = (float) Math.atan2(getVelY(), getVelX());

	if (-Math.PI / 4 < angle && angle < Math.PI / 4) {
	    setDir(Direction.RIGHT);
	} else if (-3 * Math.PI / 4 < angle && angle < -Math.PI / 4) {
	    setDir(Direction.UP);
	} else if (Math.PI / 4 < angle && angle < 3 * Math.PI / 4) {
	    setDir(Direction.DOWN);
	} else if (angle < -3 * Math.PI / 4 || angle > 3 * Math.PI / 4) {
	    setDir(Direction.LEFT);
	}
    }
    public void tick() {
	// Move along according to current velocity
	nudgeHitBox(velX, velY);
	nudge(velX, velY);
    }

    public void nudgeHitBox(final int dx, final int dy) {
	setLocationOfHitBox(hitBox.getX() + dx, hitBox.getY() + dy);
    }

    public int getVelX() {
	return velX;
    }

    public int getVelY() {
	return velY;
    }

    public boolean getIsGarbage() {
	return isGarbage;
    }
}
