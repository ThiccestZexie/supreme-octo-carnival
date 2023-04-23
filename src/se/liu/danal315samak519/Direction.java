package se.liu.danal315samak519;

public enum Direction
{
    UP, DOWN, LEFT, RIGHT;

    public int getX(){
        return switch(this){
            case UP, DOWN -> 0;
            case LEFT -> -1;
            case RIGHT -> 1;
        };
    }
    public int getY(){
        return switch(this){
            case UP -> -1;
            case DOWN -> 1;
            case LEFT, RIGHT -> 0;
        };

    }
}
