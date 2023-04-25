package se.liu.danal315samak519;

import java.awt.*;
import java.awt.geom.Point2D;

public class Arrow extends Weapon
{

    public Arrow(final Point2D.Double coord, final Character owner) {
	super(coord, owner);
	this.setLifeSpan(100);
	this.size =  new Dimension(5,5);
	this.dir = owner.dir;

	setHitBox();
    }

    @Override public void tick() {
	super.tick();
	switch (this.dir){
	    case UP -> velY = -2;
	    case DOWN -> velY = 2;
	    case LEFT -> velX = -2;
	    case RIGHT -> velX = 2;
	}
    }
}
