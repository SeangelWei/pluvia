package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import utils.Assets;
import managers.GameManager;
import utils.GameObject;

public class Player extends GameObject {
    Shot shot;
    int speed;
    int lives;
    public float stateTime;
    public boolean isFacingLeft;
    public boolean isTouched;
    public boolean isInvincible;
    public PowerUp currentPowerUp;
    public ParticleEffect particleEffect;
    public enum State {
        IDLE, WALKING
    }
    public State state = State.IDLE;

    public Player(float x){
        super(x, 100); // Y is always the same
        bounds.setWidth(Assets.playerIdleRight.getRegionWidth());
        bounds.setHeight(Assets.playerIdleLeft.getRegionHeight());
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("effects/smoke.p"),
                Gdx.files.internal("effects"));
        init();
    }

    private void init() {
        speed = 300;
        lives = 3;
        stateTime = 0;
        isFacingLeft = true;
        isTouched = false;
        isInvincible = false;
        currentPowerUp = null;
    }

    public void shot(){
        if(shot == null){
            shot = new Shot(position.x + bounds.width/2);
            shot.particleEffect.start();
        }
    }

    public void moveLeft(){
        if(position.x-1 > 0){
            position.x -= GameManager.delta() * speed;
            isFacingLeft = true;
            state = State.WALKING;
            stateTime += GameManager.delta();
        }
    }
    public void moveRight(){
        if(position.x+bounds.getWidth()+1 < 800){
            position.x += GameManager.delta() * speed;
            isFacingLeft = false;
            state = State.WALKING;
            stateTime += GameManager.delta();
        }
    }

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public void update() {
        if(shot != null) shot.update();
        if (currentPowerUp != null) {
            if (currentPowerUp.powerDuration <= 0) {
                switch (currentPowerUp.powerUpType) {
                    case SPEED:
                        speed = 300;
                        break;
                    case IMMORTAL:
                        isInvincible = false;
                        break;
                }
                currentPowerUp = null;
            } else {
                currentPowerUp.use();
            }
        }
        if(state == State.WALKING) {
            particleEffect.getEmitters().get(0).setContinuous(true);
            if(isFacingLeft) {
                particleEffect.setPosition(position.x+15, position.y);
            } else {
                particleEffect.setPosition(position.x, position.y);

            }
        } else {
            particleEffect.getEmitters().get(0).setContinuous(false);
        }
    }
}
