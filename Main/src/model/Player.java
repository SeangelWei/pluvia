package model;

import utils.Assets;
import utils.Game;
import utils.GameObject;
import utils.Pluvia;

public class Player extends GameObject {
    Shot shot;
    int speed;
    int lives;
    public boolean isMovingRight;
    public boolean isTouched;

    public Player(float x){
        super(x, 100); // Y is always the same
        bounds.setWidth(Assets.playerLeft.getWidth());
        bounds.setHeight(Assets.playerLeft.getHeight());
        init();
    }

    private void init() {
        speed = 300;
        lives = 3;
        isMovingRight = true;
        isTouched = false;
    }

    public void shot(){
        if(shot == null){
            shot = new Shot(position.x + bounds.width/2);
        }
    }

    public void moveLeft(){
        if(position.x-1 > 0){
            position.x -= Game.delta() * speed;
            isMovingRight = false;
        }
    }
    public void moveRight(){
        if(position.x+bounds.getWidth()+1 < 800){
            position.x += Game.delta() * speed;
            isMovingRight = true;
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
    }
}
