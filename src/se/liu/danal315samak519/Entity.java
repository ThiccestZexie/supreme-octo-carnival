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
	nudgeHitBox(dx, dy);
    }

    public void setLocation(final double x, final double y) {
	if (coord != null) {
	    coord.setLocation(x, y);
	} else {
	    coord = new Point2D.Double(x, y);
	}
    }

    public void setVelocity(final int vx, final int vy) {
	setVelX(vx);
	setVelY(vy);
    }

    public void setVelY(final int vy){
	this.velY = vy;
	setAppropiateDir();
    }

    public void setVelX(final int vx){
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

    private void nudgeHitBox(final int dx, final int dy) {
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
