package model;

import utils.Game;
import utils.GameObject;

import java.util.Random;

public class PowerUp extends GameObject {
    private final float GRAVITY = 1.03f;
    public static enum powerUpTypeDef { SPEED, IMMORTAL, TIME }
    public powerUpTypeDef powerUpType;
    boolean moving = true;
    float speed = 100;
    float velocity_y = -1;
    float lifeTime = 100;
    int powBlinkerTimer = 0;
    int powerDuration;

    public PowerUp(float x, float y) {
        super(x, y);
        bounds.setHeight(50);
        bounds.setWidth(50);
        Random generator = new Random();
        int value = generator.nextInt(3);
        switch (value) {
            case 0:
                powerUpType = powerUpTypeDef.IMMORTAL;
                powerDuration = 200;
                break;
            case 1:
                powerUpType = powerUpTypeDef.SPEED;
                powerDuration = 200;
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
            speed *= GRAVITY;
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

    public void use() {
        if (powerUpType != powerUpTypeDef.TIME) {
            powerDuration--;
        }
    }
}
