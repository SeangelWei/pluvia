package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import utils.*;

import java.util.ArrayList;
import java.util.List;

public class Level extends GameObject {
    Player player;
    List<Ball> balls = new ArrayList<Ball>();
    public int gainedPoints = 0;
    public TimeBar timeBar;
    public Texture background;
    public List<AnimationHelper> currentAnimations = new ArrayList<AnimationHelper>();

    public Level(float x, float y) {
        super(x, y);
    }

    public void init(Player player, List<Ball> balls, int timeLeftSpeed, float[] medals){
        this.player = null;
        this.balls.clear();

        this.player = new Player(player.position.x);
        for (Ball ball : balls) {
            this.balls.add(new Ball(ball.position.x, ball.position.y, ball.getSize(), ball.getxVector()));
        }
        timeBar = new TimeBar(140, 445, timeLeftSpeed, medals);
    }

    @Override
    public void update() {
        for (Ball ball : balls) {
            ball.update();
        }
        player.update();
        timeBar.update();
        for (int i = 0; i < currentAnimations.size(); i++) {
            AnimationHelper holder = currentAnimations.get(i);
            holder.stateTime += Game.delta();
            if(Assets.ballPoppingAnimation.isAnimationFinished(holder.stateTime)) {
                currentAnimations.remove(i);
            }
        }
        updateLogic();
    }

    private void updateLogic() {
        for (int i = 0; i < balls.size();i++) {
            if (checkCollision(player.position.x, player.position.y, player.bounds.getWidth(), player.bounds.getHeight(), balls.get(i))
                    && !player.isTouched && !player.isInvincible) {
                createNewBall(balls.get(i));
                balls.remove(i);
                player.setLives(player.getLives() - 1);
                player.isTouched = true;
            }
        }

        for(int i = 0; i < balls.size();i++){
            if(player.getShot() != null){
                if(checkCollision(getShot().position.x, getShot().position.y, getShot().bounds.getWidth(), getShot().bounds.getHeight(), balls.get(i))){
                    player.setShot(null);
                    createNewBall(balls.get(i));
                    generatePowerUp();
                    balls.remove(i);
                }
            }
        }
    }

    private void generatePowerUp() {
        //here goes the logic for the powerUp generation
        //should be done with some probabilities
    }

    private boolean checkCollision(float x, float y, float width, float height, Ball ball){
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
}
