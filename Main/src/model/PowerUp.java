package model;

import utils.GameObject;

public class PowerUp extends GameObject {
    int speed;
    public static enum powerUpType { SMALLER, SMALL, MIDDLE, BIG }
    public powerUpType powerUpType;

    public PowerUp(float x, float y) {
        super(x, y);
    }

    @Override
    public void update() {
        //gravity pulls down
        //position.y += Game.delta() * speed;
    }
}
