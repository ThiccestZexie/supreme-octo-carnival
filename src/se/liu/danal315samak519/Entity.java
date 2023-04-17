package se.liu.danal315samak519;

import java.awt.*;

public class Entity
{
    //...
    protected Dimension size;
    protected Point coord;
    protected Direction dir;
    protected int speed;
    protected int velX, velY;
    protected Color color;
    protected Rectangle hitBox;
    protected boolean isGarbage = false;
    private int exp;
    private int level;

    public Entity(final Point coord, final Color color) {
	this.size = new Dimension(50, 50); // HARDCODED SIZE OF 50,50 px TODO
	this.coord = coord;
	this.color = color;
	this.hitBox = new Rectangle(size);
	this.hitBox.setLocation(coord);
	dir = Direction.DOWN;
	isGarbage = false;
    }

    public Direction getDir() {
	return dir;
    }

    public Rectangle getHitBox(){
	return this.hitBox;
    }

    public void setDir(final Direction dir) {
	this.dir = dir;
    }

    public Point getCoord() {
	return coord;
    }
    public boolean isHit(Entity e)
    {
	if (this.hitBox.getBounds().intersects(e.getHitBox()))
		{
		    if (e instanceof WeaponEntity){
			this.isGarbage = true;
			return true;
		    }

		}
	return false;
    }


    public int getX(){
	return getCoord().x;
    }

    public int getY(){
	return getCoord().y;
    }

    public Color getColor() {
	return color;
    }

    public Dimension getSize() {
	return size;
    }
    public int getWidth() {
	return (int) size.getWidth();
    }
    public int getHeight() {
	return(int) size.getHeight();
    }

    public int getSpeed() {
	return speed;
    }

    public void setSpeed(final int speed) {
	this.speed = speed;
    }

    public void moveTo(final int x, final int y)
    {
	coord.move(x, y);
    }

    public void nudge(final int dx, final int dy) {
	coord.translate(dx, dy);
    }

    public void setCurrentVelocity(final int vx, final int vy) {
	this.velX = vx;
	this.velY = vy;
    }

    public void tick() {
	// Move along according to current velocity
	nudgeHitBox(velX, velY);
	nudge(velX, velY);
    }
    public void nudgeHitBox(final int speedX, final int speedY){
	hitBox.translate(speedX, speedY);
    }
    public int getVelX() {
	return velX;
    }

    public int getVelY() {
	return velY;
    }
    public int getLifeSpan(){
	// Returns -1 becuase they are immortal!!! as in they dont die of age...
	return -1;
    }
    public boolean getIsGarbage(){
	return isGarbage;
    }

    public WeaponEntity getSword(){
		return new WeaponEntity(this.coord, Color.BLACK, this );
    }

    public void getExp(){
	exp++;
    }

    public void levelUp(){
	while(canLevelUp()){
	    exp -= 2;
	    level++;
	}
    }

    public boolean canLevelUp(){ // need to figure out exp requirements
	if (this.exp >= 2){
	    return true;
	}
	return false;
    }
}
