package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.entities.weapons.Projectile;
import se.liu.danal315samak519.entities.weapons.Sword;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * A movable that can move and attack. Can be either the player or an enemy.
 */
public abstract class Person extends Movable
{
    // CONSTANTS
    protected static final int TICKS_PER_WALK_FRAME = 8;
    protected static final int TICKS_PER_ATTACK_FRAME = 4;
    protected static final int[] EXP_REQUIREMENTS = new int[] { 2, 3, 5, 8, 12, 20, 23, 30, 999 }; //Can move to Player level.
    protected static final int ATTACK_COOLDOWN = 20;
    private static final int INVINCIBILITY_TICKS = 20;
    private static final int ATTACK_FRAME = 2;
    protected int walkCycleIndex = 0;
    protected int exp = 0;
    protected int level = 1;
    protected int maxHP;
    protected int hp;
    protected int damage = 1;
    protected BufferedImage[] currentFrames = null;
    protected BufferedImage[] downFrames = null;
    protected BufferedImage[] leftFrames = null;
    protected BufferedImage[] upFrames = null;
    protected BufferedImage[] rightFrames = null;
    private int ticksAttackCooldown = 0;
    private int ticksInvincible = 0;
    // Tick counters
    private int ticksSinceWalkFrameChange = 0;
    private int projectileWidth;
    private int projectileHeight;
    private int projectileVelocity;


    protected Person(final Point2D.Float coord) {
	final int projectileWidth = 15;
	final int projectileHeight = 15;
	final int playerWidth = 50;
	final int playerHeight = 50;
	setLocation(coord);
	setSize(playerWidth, playerHeight);
	setMaxSpeed(5);
	setHitBox();
	setProjectileVelocity(4);
	setProjectileWidth(projectileWidth);
	setProjectileHeight(projectileHeight);
    }

    @Override public void setDirection(final Direction direction) {
	super.setDirection(direction);
	setCurrentFrames();
    }

    public int getProjectileWidth() {
	return projectileWidth;
    }

    public void setProjectileWidth(final int projectileWidth) {
	this.projectileWidth = projectileWidth;
    }


    public void setProjectileHeight(final int projectileHeight) {
	this.projectileHeight = projectileHeight;
    }

    protected boolean shouldShowAttackFrame() {
	return false;
    }

    /**
     * Set which sprite to use based on direction and if attacking.
     */
    public void setCurrentFrames() {
	this.currentFrames = switch (this.getDirection()) {
	    case UP -> upFrames;
	    case DOWN -> downFrames;
	    case LEFT -> leftFrames;
	    case RIGHT -> rightFrames;
	};
    }

    public int getHp() {
	return hp;
    }

    public void setHp(int hp) {
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

    public int getTicksAttackCooldown() {
	return ticksAttackCooldown;
    }

    protected void becomeInvincible() {
	ticksInvincible = INVINCIBILITY_TICKS;
    }

    public Projectile getProjectile() {
	return new Projectile(this, projectileHeight, projectileWidth, projectileVelocity);
    }

    public Sword getSword() {
	return new Sword(this);
    }

    /**
     * Player and enemies display health differently, so they should override this abstract method.
     *
     * @param g
     */
    public abstract void drawHealth(final Graphics g);


    public void levelUp() {
	while (getIfReadyToLevelUp()) {
	    int currExpRequirement = EXP_REQUIREMENTS[level - 1];

	    exp -= currExpRequirement;
	    level++;
	    final int healthIncrease = 1;
	    this.maxHP += healthIncrease;
	    setHp(maxHP);
	}
    }

    public void heal(int healAmount) {
	if (this.hp + healAmount > this.maxHP) {
	    this.hp = maxHP;
	} else {
	    this.hp += healAmount;
	}
    }

    public boolean getIfReadyToLevelUp() { // You start as level 1 so index 0 of exp req and the exp is exp needed for next level...
	if (this.exp >= EXP_REQUIREMENTS[level - 1]) {
	    return true;
	}
	return false;
    }

    /**
     * Takes the specified damage, and become garbage if below 1 hp.
     *
     * @param damage
     */
    public void takeDamage(int damage) {
	if (getIsInvincible()) {
	    return; // Don't take damage if invincible!
	}
	this.hp -= damage;
	becomeInvincible();
	if (this.hp < 1) {
	    this.hp = 0;
	    setIsGarbage();
	}
    }

    public boolean getIsInvincible() {
	return ticksInvincible > 0;
    }

    /**
     * All movables should be nudged away from Obstacles
     *
     * @param movable
     */
    @Override public void tick() {
	super.tick();
	tickAttackCooldown();
	tickInvincibility();
	showNextFrame();
    }

    private void tickInvincibility() {
	if (ticksInvincible > 0) {
	    ticksInvincible--;
	}
    }

    private void tickAttackCooldown() {
	if (ticksAttackCooldown > 0) {
	    ticksAttackCooldown--;
	}
    }

    /**
     * Return true if Person is standing still.
     *
     * @return
     */
    private boolean getIfStandingStill() {
	return velX == 0 && velY == 0;
    }

    /**
     * Either show next walking- or attack frame, based on values.
     */
    private void showNextFrame() {
	if (shouldShowAttackFrame()) { // ATTACK
	    walkCycleIndex = ATTACK_FRAME; // 2 is where attack frame is located.
	} else { // WALK
	    if (getIfStandingStill()) { // Reset to standing
		walkCycleIndex = 0;
		ticksSinceWalkFrameChange = 0;
	    } else {
		ticksSinceWalkFrameChange++;
		if (ticksSinceWalkFrameChange > TICKS_PER_WALK_FRAME) {
		    walkCycleIndex++;
		    walkCycleIndex %= (currentFrames.length - 1);
		    ticksSinceWalkFrameChange = 0;
		}
	    }
	}
    }

    @Override public BufferedImage getCurrentSprite() {
	return getAnimationFrame(walkCycleIndex);
    }

    private BufferedImage getAnimationFrame(int index) {
	return currentFrames[index];
    }

    public boolean canAttack() {
	return this.ticksAttackCooldown < 1;
    }

    public void becomeAttacking() {
	this.ticksAttackCooldown = ATTACK_COOLDOWN;
    }

    public void setMaxHP(final int maxHP) {
	this.maxHP = maxHP;
    }

    public int getProjectileVelocity() {
	return projectileVelocity;
    }

    public void setProjectileVelocity(final int projectileVelocity) {
	this.projectileVelocity = projectileVelocity;
    }

    public int getDamage() {
	return damage;
    }

    protected void setDamage(int damage) {
	this.damage = damage;
    }

}
