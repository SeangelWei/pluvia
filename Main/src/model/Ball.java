package model;

import utils.Game;
import utils.GameObject;

public class Ball extends GameObject {
    private int radius;
    private float xVector;
    private float yVector = 0;
    private final int groundPosition = 100;
    private static final double gravity = 1;
    private float speed;

    public static enum sizeDef { SMALLER, SMALL, MIDDLE, BIG }
    private sizeDef size;

    public Ball(float x, float y, sizeDef theSize, float direction) {
        super(x, y);
        xVector = direction;
        speed = 12;
        setSize(theSize);
        switch (theSize) {
            case BIG:
                radius = 50;
                break;
            case MIDDLE:
                radius = 40;
                break;
            case SMALL:
                radius = 30;
                break;
            case SMALLER:
                radius = 20;
                break;
        }
    }

    @Override
    public void update() {
        if(position.y-1 <= groundPosition){
            yVector *= -1;
        } else {
            yVector -= gravity;
        }

        position.x += xVector* Game.delta() * speed;
        position.y += yVector* Game.delta() * speed;

        if(position.x+radius >= 750){
            xVector = -xVector;
        }
        if(position.x+radius <= 50){
            xVector = -xVector;
        }
    }
    public sizeDef getSize() {
        return size;
    }

    public void setSize(sizeDef size) {
        this.size = size;
    }

    public int getRadius() {
        return radius;
    }

    public void setyVector(float yVector) {
        this.yVector = yVector;
    }

    public float getxVector() {
        return xVector;
    }
}
