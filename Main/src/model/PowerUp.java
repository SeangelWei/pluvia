package model;

import utils.Game;
import utils.GameObject;

import java.util.Random;

public class PowerUp extends GameObject {
    private float gravity = 1.03f;
    public static enum powerUpTypeDef { SPEED, IMMORTAL, TIME }
    public powerUpTypeDef powerUpType;
    boolean moving = true;
    float speed = 100;
    float velocity_y = -1;
    float lifeTime = 100;
    int powBlinkerTimer = 0;

    public PowerUp(float x, float y) {
        super(x, y);
        Random generator = new Random();
        int value = generator.nextInt(3);
        switch (value) {
            case 0:
                powerUpType = powerUpTypeDef.IMMORTAL;
                break;
            case 1:
                powerUpType = powerUpTypeDef.SPEED;
                break;
            case 2:
                powerUpType = powerUpTypeDef.TIME;
                break;
        }
    }

    @Override
    public void update() {
        if (moving) {
            float v = velocity_y * Game.delta() * speed;
            position.y += v;
            speed *= gravity;
            if (position.y <= 100) {
                moving = false;
            }
        }
    }

    public float getLifeTime() {
        return lifeTime;
    }

    public boolean getMoving() {
        return moving;
    }

    public int getPowBlinkerTimer() {
        return powBlinkerTimer;
    }

    public void setPowBlinkerTimer(int powBlinkerTimer) {
        this.powBlinkerTimer = powBlinkerTimer;
    }
}
