package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import managers.GameManager;
import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level extends GameObject {
    Player player;
    List<Ball> balls = new ArrayList<Ball>();
    List<PowerUp> powerUps = new ArrayList<PowerUp>();
    public int gainedPoints = 0;
    public TimeBar timeBar;
    public Texture background;
    public List<AnimationHelper> currentAnimations = new ArrayList<AnimationHelper>();
    public ParticleEffect explosionParticle;
    public Level(float x, float y) {
        super(x, y);
    }

    public void init(Player player, List<Ball> balls, int timeLeftSpeed, float[] medals){
        this.player = null;
        this.balls.clear();
        this.powerUps.clear();

        this.player = new Player(player.position.x);
        for (Ball ball : balls) {
            this.balls.add(new Ball(ball.position.x, ball.position.y, ball.getSize(), ball.getxVector()));
        }
        timeBar = new TimeBar(200, 440, timeLeftSpeed, medals);
        explosionParticle = new ParticleEffect();
        explosionParticle.load(Gdx.files.internal("effects/explosion.p"),
                Gdx.files.internal("effects"));
    }

    @Override
    public void update() {
        for (Ball ball : balls) {
            ball.update();
        }
        for (PowerUp powerUp : powerUps) {
            if (powerUp != null) {
                powerUp.update();
            }
        }
        player.update();
        timeBar.update();
        for (int i = 0; i < currentAnimations.size(); i++) {
            AnimationHelper holder = currentAnimations.get(i);
            holder.stateTime += GameManager.delta();
            if(Assets.ballPoppingAnimation.isAnimationFinished(holder.stateTime)) {
                currentAnimations.remove(i);
            }
        }
        updateLogic();
    }

    private void updateLogic() {
        for (int i = 0; i < balls.size();i++) {
            if (checkCollisionSquareRound(player.position.x, player.position.y, player.bounds.getWidth(), player.bounds.getHeight(), balls.get(i))
                    && !player.isTouched && !player.isInvincible) {
                startExplosionEffect(balls.get(i).position.x, balls.get(i).position.y);
                createNewBall(balls.get(i));
                balls.remove(i);
                player.setLives(player.getLives() - 1);
                player.isTouched = true;
            }
        }

        for(int i = 0; i < balls.size();i++){
            if(player.getShot() != null){
                if(checkCollisionSquareRound(getShot().position.x, getShot().position.y, getShot().bounds.getWidth(), getShot().bounds.getHeight(), balls.get(i))){
                    Assets.explosion1.play();
                    startExplosionEffect(balls.get(i).position.x, balls.get(i).position.y);
                    player.setShot(null);
                    createNewBall(balls.get(i));
                    generatePowerUp(balls.get(i).position);
                    balls.remove(i);
                }
            }
        }

        for (PowerUp powerUp : powerUps) {
            Rectangle player = new Rectangle((int) getPlayer().position.x, (int) getPlayer().position.y, (int) getPlayer().bounds.getWidth(), (int) getPlayer().bounds.getHeight());
            Rectangle powerUpRect = new Rectangle((int) powerUp.position.x, (int) powerUp.position.y, (int) powerUp.bounds.getWidth(), (int) powerUp.bounds.getHeight());
            if (checkCollisionSquareSquare(player, powerUpRect)) {
                Assets.powerup1.play();
                switch (powerUp.powerUpType) {
                    case SPEED:
                        getPlayer().currentPowerUp = powerUp;
                        getPlayer().speed = 500;
                        break;
                    case IMMORTAL:
                        getPlayer().currentPowerUp = powerUp;
                        getPlayer().isInvincible = true;
                        break;
                    case TIME:
                        if (timeBar.timeLeft_x + 50 < GameManager.VIRTUAL_WIDTH) {
                            timeBar.timeLeft_x += 50;
                        } else {
                            timeBar.timeLeft_x = GameManager.VIRTUAL_WIDTH;
                        }
                        break;
                }
                powerUps.remove(powerUp);
                return;
            }
        }
    }

    private void startExplosionEffect(float x, float y) {
        explosionParticle.setPosition(x, y);
        explosionParticle.start();
    }

    private void generatePowerUp(Vector2 position) {
        Random generator = new Random();
        double value = generator.nextInt(10);
        if (value >= 0) {
            powerUps.add(new PowerUp(position.x, position.y));
        }
    }

    private boolean checkCollisionSquareRound(float x, float y, float width, float height, Ball ball){
        float circleDistanceX = abs(ball.position.x+ball.getRadius() - x - width/2);
        float circleDistanceY = abs(ball.position.y+ball.getRadius() - y - height/2);

        if (circleDistanceX > (width/2 + ball.getRadius())) { return false; }
        if (circleDistanceY > (height/2 + ball.getRadius())) { return false; }

        if (circleDistanceX <= (width/2)) { return true; }
        if (circleDistanceY <= (height/2)) { return true; }

        float cornerDistance_sq = pow(circleDistanceX - width/2, 2) +
                pow(circleDistanceY - height/2, 2);

        return (cornerDistance_sq <= pow(ball.getRadius(),2));
    }

    private boolean checkCollisionSquareSquare(Rectangle r1, Rectangle r2){
        return (r1.x <= r2.x + r2.width &&
                r2.x <= r1.x + r1.width &&
                r1.y <= r2.y + r2.height &&
                r2.y <= r1.y + r2.height);
    }

    private float pow(float n, float e) {
        return (float) Math.pow(n, e);
    }

    private float abs(float number) {
        return Math.abs(number);
    }

    private void createNewBall(Ball toDestroyBall){
        currentAnimations.add(new AnimationHelper(new Vector2(toDestroyBall.position.x-toDestroyBall.getRadius(), toDestroyBall.position.y)));
        switch(toDestroyBall.getSize()){
            case BIG:
                Ball middleBall1 = new Ball(toDestroyBall.position.x, toDestroyBall.position.y+toDestroyBall.getRadius(), Ball.sizeDef.MIDDLE, -2.4f);
                Ball middleBall2 = new Ball(toDestroyBall.position.x, toDestroyBall.position.y+toDestroyBall.getRadius(), Ball.sizeDef.MIDDLE, 2.2f);
                middleBall1.setyVector(25);
                middleBall2.setyVector(25);
                balls.add(middleBall1);
                balls.add(middleBall2);
                gainedPoints+=50;
                break;
            case MIDDLE:
                Ball smallBall1 = new Ball(toDestroyBall.position.x, toDestroyBall.position.y+toDestroyBall.getRadius(), Ball.sizeDef.SMALL, -2.4f);
                Ball smallBall2 = new Ball(toDestroyBall.position.x, toDestroyBall.position.y+toDestroyBall.getRadius(), Ball.sizeDef.SMALL, 2.2f);
                smallBall1.setyVector(25);
                smallBall2.setyVector(25);
                balls.add(smallBall1);
                balls.add(smallBall2);
                gainedPoints+=40;
                break;
            case SMALL:
                Ball smallerBall1 = new Ball(toDestroyBall.position.x, toDestroyBall.position.y+toDestroyBall.getRadius(), Ball.sizeDef.SMALLER, -2.4f);
                Ball smallerBall2 = new Ball(toDestroyBall.position.x, toDestroyBall.position.y+toDestroyBall.getRadius(), Ball.sizeDef.SMALLER, 2.2f);
                smallerBall1.setyVector(25);
                smallerBall2.setyVector(25);
                balls.add(smallerBall1);
                balls.add(smallerBall2);
                gainedPoints+=30;
                break;
            case SMALLER:
                gainedPoints+=20;
            default:
                break;
        }
    }
    public Player getPlayer() {
        return player;
    }
    public List<Ball> getBalls(){
        return balls;
    }
    public Shot getShot() {
        return player.getShot();
    }
    public List<PowerUp> getPowerUps() {
        return powerUps;
    }
}
