package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import managers.GameManager;
import utils.GameObject;

public class Ball extends GameObject {
    private int radius;
    private float XVelocity;
    private float YVelocity = 0;
    private final int groundPosition = 100;
    private static final double gravity = 1;
    private float speed;
    public ParticleEffect particleEffect;

    public static enum sizeDef { SMALLER, SMALL, MIDDLE, BIG }
    private sizeDef size;
    public static enum colorDef { BLUE, RED }
    private colorDef color;

    public Ball(float x, float y, sizeDef theSize, colorDef theColor, float direction) {
        super(x, y);
        XVelocity = direction;
        speed = 12;
        size = theSize;
        color = theColor;
        switch (theSize) {
            case BIG:
                radius = 40;
                break;
            case MIDDLE:
                radius = 30;
                break;
            case SMALL:
                radius = 20;
                break;
            case SMALLER:
                radius = 10;
                break;
        }
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("effects/ballJump.p"),
                Gdx.files.internal("effects"));
    }

    @Override
    public void update() {
        if(position.y-1 <= groundPosition){
            YVelocity *= -1;
            particleEffect.setPosition(position.x+radius, position.y);
            particleEffect.start();
        } else {
            YVelocity -= gravity;
        }

        position.x += XVelocity * GameManager.delta() * speed;
        position.y += YVelocity * GameManager.delta() * speed;

        if(position.x+radius*2 >= 800){
            XVelocity = -XVelocity;
        }
        if(position.x <= 0){
            XVelocity = -XVelocity;
        }
    }
    public sizeDef getSize() {
        return size;
    }

    public colorDef getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }

    public void setYVelocity(float yVector) {
        this.YVelocity = yVector;
    }

    public float getXVelocity() {
        return XVelocity;
    }
}
