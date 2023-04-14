package se.liu.danal315samak519;

import java.awt.*;

public class WeaponEntity extends Entity
{
    private Entity owner;
    private int lifeSpan;

    public WeaponEntity(final Point coord, final Color color, final Entity owner) {
	super(coord,	 color);
	size = new Dimension(60, 10);
	this.owner = owner;
	this.hitBox = new Rectangle(size);
	this.coord = new Point((owner.getX() + owner.getWidth()),
			       (owner.getY()+ (owner.getHeight()/2)) - (this.getHeight()/2));
	 lifeSpan = 5;
    }

    @Override public void tick() {
	super.tick();
	lifeSpan--;
	if(lifeSpan <= 0){
	    isGarbage = true;
	}
    }

    @Override public int getLifeSpan() {
	return lifeSpan;
    }
}
