package se.liu.danal315samak519.entities.weapons;

import se.liu.danal315samak519.entities.Movable;
import se.liu.danal315samak519.entities.Person;

import java.awt.image.BufferedImage;

/**
 * Superclass to all weapons.
 */
public abstract class Weapon extends Movable
{
    protected BufferedImage currentSprite = null;
    protected Person owner;
    protected int lifeSpan;

    protected Weapon(final Person owner) {
	this.owner = owner;
	this.lifeSpan = 5;
    }

    public Person getOwner() {
	return owner;
    }

    @Override public BufferedImage getCurrentSprite() {
	return currentSprite;
    }
    @Override public void tick() {
	super.tick();
	lifeSpan--;
	if (lifeSpan <= 0) {
	    isGarbage = true;
	}
    }

    public void followOwner() {
	setVelocity(owner.getVelX(), owner.getVelY());
    }

    public void setLifeSpan(final int lifeSpan) {
	this.lifeSpan = lifeSpan;
    }

    @Override public int getDamage() {
	return owner.getDamage();
    }

    @Override public void interactWith(final Movable movable) {
	Person person = (Person) movable;
	if(!person.equals(this.getOwner())){
	    person.takeDamage(1);
	    this.setIsGarbage();
	}

    }
}

