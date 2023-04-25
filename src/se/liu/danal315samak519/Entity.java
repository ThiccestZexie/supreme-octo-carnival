package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class Entity
{
    protected Point2D.Double coord;
    protected double width, height;
    protected Color color;
    protected Rectangle2D hitBox;
    protected boolean isGarbage = false;

    protected void setLocation(final Point2D.Double coord) {
	if (this.coord != null) {
	    this.coord.setLocation(coord);
	} else {
	    this.coord = coord;
	}
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

    public void setSize(double w, double h) {
	this.width = w;
	this.height = h;
	setHitBox();
    }

    public double getWidth() {
	return this.width;
    }

    public double getHeight() {
	return this.height;
    }

    public void setLocation(final double x, final double y) {
	if (coord != null) {
	    coord.setLocation(x, y);
	} else {
	    coord = new Point2D.Double(x, y);
	}
	setHitBox();
    }
}
