package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.Status;
import se.liu.danal315samak519.weapons.Projectile;
import se.liu.danal315samak519.weapons.Sword;
import se.liu.danal315samak519.weapons.Weapon;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Character extends Movable
{
    // CONSTANTS FOR CHARACTERS

    protected static final int TICKS_PER_FRAME = 8;
    protected static final int[] EXP_REQUIREMENTS = new int[] { 2, 3, 5, 8, 12, 20, 23, 30, 999 }; //from level "0" to level "10"

    protected static final int iFramesDuration = 1000;

    protected static int attackSpeed = 750;

    protected int exp = 0;
    protected int currentIFramees = 0;
    protected int level;
    protected int maxHP;
    protected int hp;
    protected int currentFrameIndex = 0;
    // Sprite
    protected BufferedImage[] currentFrames;
    protected BufferedImage[] downFrames;
    protected BufferedImage[] leftFrames;
    protected BufferedImage[] upFrames;
    protected BufferedImage[] rightFrames;
    protected BufferedImage attackFrame;
    Timer attackSpeedTimer;
    private Status status = Status.NORMAL;
    Timer iFramesTimer = new Timer(iFramesDuration, e -> setStatus(Status.NORMAL)); // Timer for invisableFrames, 1s
    private int ticksCounted;
    // Lamda function som körs av en timer som gör så att man kan attackera, 0.5s


    protected Character(final Point2D.Double coord) {
	setLocation(coord);
	setSize(50, 50);
	setMaxSpeed(5);
	setHitBox();
    }

    @Override public void setDir(final Direction dir) {
	super.setDir(dir);
	setFramesBasedOnDirection();
    }

    public void setFramesBasedOnDirection() {
	this.currentFrames = switch (this.getDir()) {
	    case UP -> upFrames;
	    case DOWN -> downFrames;
	    case LEFT -> leftFrames;
	    case RIGHT -> rightFrames;
	};

    }

    public int getHp() {
	return hp;
    }

    private void setHp(int hp) {
	this.hp = hp;
    }

    protected void setStats(int maxHP, int level) {
	this.maxHP = maxHP;
	this.hp = this.maxHP;
	this.level = level;
    }

    public int getMaxHp() {
	return maxHP;
    }

    public int[] getExpRequirements() {
	return EXP_REQUIREMENTS;
    }

    public int getLevel() {
	return level;
    }

    public int getExp() {
	return exp;
    }

    public boolean getHitByWeapon(Weapon weapon)
    {
	Character owner = weapon.getOwner();

	if (this.hitBox.intersects(weapon.getHitBox())) {
	    if (hp >= 1 && this.getStatus() != Status.HIT && !this.equals(owner)) {
		hp--;
		this.setStatus(Status.HIT);
		iFramesTimer.start();
	    } else if (this.getStatus() != Status.HIT) {
		owner.incExp();
		owner.levelUp();
		this.isGarbage = true;
	    }
	    return true;

	}
	return false;
    }

    public Projectile getProjectile() {
	return new Projectile(this.coord, this);
    }

    public Sword getSword() {
	if (this.status == Status.NORMAL) {
	    return new Sword(this.coord, this);
	}
	return new Sword(new Point2D.Double(0, 0), this);
    }

    public void incExp() { //Exp should depend on enemey level
	exp++;
    }

    public void levelUp() {
	while (checkExpReq()) {
	    exp -= EXP_REQUIREMENTS[level - 1];
	    level++;
	    this.maxHP += 1;
	    setHp(maxHP);
	}
    }

    public void heal(int healAmmount) {
	this.hp += healAmmount;
    }

    public boolean checkExpReq() { // You start as level 1 so index 0 of exp req and the exp is exp needed for next level...
	if (this.exp >= EXP_REQUIREMENTS[level - 1]) {
	    return true;
	}
	return false;
    }

    private void decrHp() {
	hp--;
    }

    public void takeDamage() {
	if (getHp() > 0) {
	    decrHp();
	} else {
	    this.markGarbage();
	}
    }

    @Override public void tick() {
	if (getStatus() == Status.SLEEPING) {
	    return; // Do nothing just sleep zZzZ
	}
	super.tick();
	showNextStepInWalk();
    }

    /**
     * Increments currentSpriteFrameIndex IFF the character is walking. Resets to zero if player stops.
     */
    private void showNextStepInWalk() {
	if (velX == 0 && velY == 0) {
	    currentFrameIndex = 0;
	    ticksCounted = 0;
	} else {
	    ticksCounted++;
	    if (ticksCounted > TICKS_PER_FRAME) {
		currentFrameIndex++;
		currentFrameIndex %= 2;
		ticksCounted = 0;
	    }
	}
    }

    public BufferedImage getCurrentSprite() {
	return getSpriteFrameAt(currentFrameIndex);
    }

    private BufferedImage getSpriteFrameAt(int index) {
	return currentFrames[index];
    }

    public void setCurrentFrames(final BufferedImage[] frames) {
	this.currentFrames = frames;
    }

    public boolean canAttack() {
	return getStatus() == Status.NORMAL;
    }

    public void startAttackSpeedTimer() {
	attackSpeedTimer = new Timer(attackSpeed, e -> {
	    setStatus(Status.NORMAL);
	    attackSpeedTimer.stop();
	});
    }

    public boolean tryToAttack() {
	{
	    if (this.status == Status.NORMAL) {
		this.setStatus(Status.ATTACKING);
		startAttackSpeedTimer();
		return true;
	    }
	    return false;
	}

    }

    protected Status getStatus() {
	return this.status;
    }

    protected void setStatus(final Status status) {
	this.status = status;
    }


}
