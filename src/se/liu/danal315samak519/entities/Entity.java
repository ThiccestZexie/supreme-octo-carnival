package se.liu.danal315samak519.entities;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Objects that have location, size, color and hitbox.
 */
public abstract class Entity
{
    private static final Random RANDOM = new Random();
    protected Point2D.Float coord = null;
    protected float width;
    protected float height;
    protected Color color = null;
    protected Rectangle2D.Float hitBox = null;
    protected boolean isGarbage = false;

    protected void setLocation(final Point2D.Float coord) {
	if (this.coord != null) {
	    this.coord.setLocation(coord);
	} else {
	    this.coord = coord;
	}
    }

    /**
     * Returns the static random object.
     * @return
     */
    protected Random getRandom(){
	return RANDOM;
    }

    public int getIntWidth() {
	return (int) getWidth();
    }

    public int getIntHeight() {
	return (int) getHeight();
    }

    public int getEntityIntX() {
	return (int) getX();
    }

    public int getEntityIntY() {
	return (int) getY();
    }

    protected void setLocationOfHitBox(float x, float y) {
	setHitBox(x, y, getWidth(), getHeight());
    }


    protected void setHitBox(float x, float y, float w, float h) {
	if (hitBox != null) {
	    this.hitBox.setRect(x, y, w, h);
	} else {
	    this.hitBox = new Rectangle2D.Float(x, y, w, h);
	}
    }

    protected void setHitBox() {
	setHitBox(getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle2D.Float getHitBox() {
	return this.hitBox;
    }

    public Point2D.Float getCoord() {
	return coord;
    }

    public float getX() {
	return coord.x;
    }

    public float getY() {
	return coord.y;
    }

    public Color getColor() {
	return color;
    }

    public void setColor(final Color color) {
	this.color = color;
    }

    public void setSize(float w, float h) {
	this.width = w;
	this.height = h;
    }

    public float getWidth() {
	return this.width;
    }

    public float getHeight() {
	return this.height;
    }

    public void setLocation(final float x, final float y) {
	if (coord != null) {
	    coord.setLocation(x, y);
	} else {
	    coord = new Point2D.Float(x, y);
	}
	setHitBox();
    }

    public void setCenterLocation(final float x, final float y) {
	final float xCoord = x - this.getWidth() / 2.0f;
	final float yCoord = y - this.getHeight() / 2.0f;
	setLocation(xCoord,yCoord );
    }

    public void setIsGarbage() {
	this.isGarbage = true;
    }

    public boolean getIsGarbage() {
	return isGarbage;
    }
}
