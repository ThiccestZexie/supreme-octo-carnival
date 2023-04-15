package se.liu.danal315samak519;

import java.awt.*;

public class WeaponEntity extends Entity
{
    private Entity owner;
    private int lifeSpan;
    private Point tempCoord;

    public WeaponEntity(final Point coord, final Color color, final Entity owner) {
	//We have two choices when it comes to spawning the weapon first look at dir then spawn everything according to that...
	super(coord, color);
	this.owner = owner;
	this.coord = coord;
	switch (owner.getDir()){
	    case UP:
		this.size =new Dimension(10, 60);
	    	tempCoord = new Point((owner.getX() + (owner.getWidth()/2) - this.getWidth()/2), (owner.getY() - this.getHeight()));
		    break;
	    case DOWN:
		this.size =new Dimension(10, 60);
		tempCoord = new Point((owner.getX() + (owner.getWidth()/2) - this.getWidth()/2),
				       (owner.getY() + (owner.getHeight())));
		break;
	    case LEFT:
		size = new Dimension(60, 10);
		tempCoord = new Point((owner.getX() - this.getWidth()),
				       (owner.getY()+ (owner.getHeight()/2)) - (this.getHeight()/2));
		break;
	    case RIGHT:
		size = new Dimension(60, 10);
		tempCoord = new Point((owner.getX() + owner.getWidth()),
			       (owner.getY()+ (owner.getHeight()/2)) - (this.getHeight()/2));
		break;

	}
	this.coord = tempCoord;
	this.hitBox = new Rectangle(tempCoord, size);
	lifeSpan = 5;
    }

    @Override public void tick() {
	super.tick();
	lifeSpan--;
	followOwner();
	if(lifeSpan <= 0){
	    isGarbage = true;
	}
    }
    public void followOwner(){
	    this.velX = owner.getVelX();
	    this.velY = owner.getVelY();
    }
    @Override public int getLifeSpan() {
	return lifeSpan;
    }
}
