package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import managers.GameManager;
import utils.GameObject;

public class Ball extends GameObject {
    private int radius;
    private float xVector;
    private float yVector = 0;
    private final int groundPosition = 100;
    private static final double gravity = 1;
    private float speed;
    public ParticleEffect particleEffect;

    public static enum sizeDef { SMALLER, SMALL, MIDDLE, BIG }
    private sizeDef size;

    public Ball(float x, float y, sizeDef theSize, float direction) {
        super(x, y);
        xVector = direction;
        speed = 12;
        setSize(theSize);
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
            yVector *= -1;
            particleEffect.setPosition(position.x+radius, position.y);
            particleEffect.start();
        } else {
            yVector -= gravity;
        }

        position.x += xVector* GameManager.delta() * speed;
        position.y += yVector* GameManager.delta() * speed;

        if(position.x+radius*2 >= 800){
            xVector = -xVector;
        }
        if(position.x <= 0){
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
