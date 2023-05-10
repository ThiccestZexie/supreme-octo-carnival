package se.liu.danal315samak519.entities;

import se.liu.danal315samak519.Direction;
import se.liu.danal315samak519.weapons.Projectile;
import se.liu.danal315samak519.weapons.Sword;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Movables that can attack, take damage and make decisions on how to move.
 */
public abstract class Character extends Movable
{
    // CONSTANTS
    protected static final int TICKS_PER_WALKFRAME = 8;
    private static final int TICKS_PER_ATTACKFRAME = 4;
    protected static final int[] EXP_REQUIREMENTS = new int[] { 2, 3, 5, 8, 12, 20, 23, 30, 999 }; //from level "0" to level "10"
    private static final int INVINCIBILITY_TICKS = 20;

    protected static int attackSpeed = 20;
    public int ticksAttackCooldown = 0;
    public int ticksInvincible = 0;
    protected int walkCycleIndex = 0;
    protected int exp = 0;
    protected int level;
    protected int maxHP;
    protected int hp;
    protected BufferedImage[] currentFrames;
    protected BufferedImage[] downFrames;
    protected BufferedImage[] leftFrames;
    protected BufferedImage[] upFrames;
    protected BufferedImage[] rightFrames;
    protected BufferedImage[] attackFrames;
    // Tick counters
    private int ticksSinceWalkFrameChange = 0;
    private int projectileWidth;
    private int projectileHeight;


    protected Character(final Point2D.Float coord) {
	setLocation(coord);
	setSize(50, 50);
	setMaxSpeed(5);
	setHitBox();
	projectileWidth = 5;
	projectileHeight = 5;
    }

    @Override public void setDir(final Direction dir) {
	super.setDir(dir);
	setCurrentFrames();
    }

    public int getProjectileWidth() {
	return projectileWidth;
    }

    public void setProjectileWidth(final int projectileWidth) {
	this.projectileWidth = projectileWidth;
    }

    public int getProjectileHeight() {
	return projectileHeight;
    }

    public void setProjectileHeight(final int projectileHeight) {
	this.projectileHeight = projectileHeight;
    }

    private boolean shouldShowAttackFrame() {
	return this.ticksAttackCooldown > TICKS_PER_ATTACKFRAME;
    }

    /**
     * Set which sprite to use based on direction and if attacking.
     */
    public void setCurrentFrames() {
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

    protected void becomeInvincible() {
	ticksInvincible = INVINCIBILITY_TICKS;
    }

    public Projectile getProjectile() {
	return new Projectile(this.coord, this, projectileHeight, projectileWidth);
    }

    public Sword getSword() {
	return new Sword(this.coord, this);
    }

    public void incrementExp() { //Exp should depend on enemey level
	exp++;
    }

    public void levelUp() {
	while (getIfReadyToLevelUp()) {
	    exp -= EXP_REQUIREMENTS[level - 1];
	    level++;
	    this.maxHP += 1;
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

    protected void decrementHp() {
	hp--;
    }

    public boolean getIsInvincible() {
	return ticksInvincible > 0;
    }

    public boolean getHasAttackCooldown() {
	return ticksAttackCooldown > 0;
    }

    public void tryTakeDamage() {
	if (getIsInvincible()) {
	    return; // Don't take damage!
	}

	if (getHp() > 1) {
	    decrementHp();
	} else {
	    this.markAsGarbage(); // DEAD
	}
	becomeInvincible();
    }

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

    private boolean getIfStandingStill(){
	return velX == 0 && velY == 0;
    }

    /**
     * Either show next walking- or attack frame, based on values.
     */
    private void showNextFrame() {
	if (shouldShowAttackFrame()) { // ATTACK
	    walkCycleIndex = 2; // 2 is where attack frame is located.
	} else { // WALK
	    if (getIfStandingStill()) { // Reset to standing
		walkCycleIndex = 0;
		ticksSinceWalkFrameChange = 0;
	    } else {
		ticksSinceWalkFrameChange++;
		if (ticksSinceWalkFrameChange > TICKS_PER_WALKFRAME) {
		    walkCycleIndex++;
		    walkCycleIndex %= (currentFrames.length-1);
		    ticksSinceWalkFrameChange = 0;
		}
	    }
	}
    }

    public BufferedImage getCurrentSprite() {
	return getSpriteFrame(walkCycleIndex);
    }

    private BufferedImage getSpriteFrame(int index) {
	return currentFrames[index];
    }

    public void setCurrentFrames(final BufferedImage[] frames) {
	this.currentFrames = frames;
    }

    public boolean canAttack() {
	return this.ticksAttackCooldown < 1;
    }

    public void becomeAttacking() {
	this.ticksAttackCooldown = attackSpeed;
    }
}
