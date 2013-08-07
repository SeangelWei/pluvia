package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import controllers.GamescreenController;
import model.Ball;
import model.Player;
import model.PowerUp;

public class WorldRenderer {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    boolean DEBUG = false;
    String playerCoords;
    String playerDim;
    String ballPos;
    String playerLives;
    String shotPos="";
    String gainedPointsString;
    int blinkTimer = 0;
    int playerBlinkerTimer = 0;
    boolean blinker;
    TextureRegion playerFrame;
    GamescreenController gsController;
    Player player;

    public WorldRenderer(SpriteBatch spriteBatch, GamescreenController gsController) {
        this.batch = spriteBatch;
        shapeRenderer = new ShapeRenderer();
        this.gsController = gsController;
        font = new BitmapFont(Gdx.files.internal("gui/arial-15.fnt"),
                Gdx.files.internal("gui/arial-15.png"), false);
    }

    public void render() {
        player = gsController.getPlayer();
        batch.begin();
        drawLevel();
        drawPlayerAndShot();
        drawLife();
        drawPowerUp();
        drawBalls();
        drawPoints();
        batch.end();
        drawTimeBar(); // the shapeRenderer is in this method active
        batch.begin();
        drawStates();
        drawDebug();
        batch.end();
    }

    private void drawPowerUp() {
        for (PowerUp powerUp : gsController.getLevel().getPowerUps()) {
            if (powerUp != null) {
                Vector2 position = powerUp.position;
                if (powerUp.getMoving()) {
                    drawLonelyPowerUp(powerUp, position);
                } else {
                    int powBlinkerTimer = powerUp.getPowBlinkerTimer();
                    powBlinkerTimer++;
                    powerUp.setPowBlinkerTimer(powBlinkerTimer);
                    if(powBlinkerTimer%5==0){
                        drawLonelyPowerUp(powerUp, position);
                    }
                    if(powBlinkerTimer > powerUp.getLifeTime()){
                        powerUp.setPowBlinkerTimer(0);
                        gsController.getLevel().getPowerUps().remove(powerUp);
                        return;
                    }
                }
            }
        }
    }

    private void drawLonelyPowerUp(PowerUp powerUp, Vector2 position) {
        switch (powerUp.powerUpType) {
            case SPEED:
                batch.draw(Assets.powerUp_speed, position.x, position.y, 50, 50);
                break;
            case IMMORTAL:
                batch.draw(Assets.powerUp_immortal, position.x, position.y, 50, 50);
                break;
            case TIME:
                batch.draw(Assets.powerUp_time, position.x, position.y, 50, 50);
                break;
        }
    }

    private void drawLevel() {
        batch.draw(gsController.getLevel().background, 0, 0, 800, 480);
        batch.draw(Assets.moon, 700, 380, 200, 200);
        if(gsController.getShot() != null){
            batch.draw(Assets.shot, gsController.getShot().position.x, gsController.getShot().position.y);
        }
        batch.draw(Assets.gs_bar, 0, 0);
        batch.draw(Assets.arrow_left, gsController.arrow_left.x, gsController.arrow_left.y);
        batch.draw(Assets.arrow_right, gsController.arrow_right.x, gsController.arrow_right.y);
        batch.draw(Assets.arrow_up, gsController.arrow_up.x, gsController.arrow_up.y);
    }

    private void drawPlayerAndShot() {
        if(player.isTouched){
            playerBlinkerTimer++;
            if(playerBlinkerTimer%5==0){
                drawPlayer();
            }
            if(playerBlinkerTimer > 100){
                player.isTouched = false;
                playerBlinkerTimer = 0;
            }
        } else {
            drawPlayer();
        }
    }

    private void drawPlayer() {
        playerFrame = player.isFacingLeft ? Assets.playerIdleLeft : Assets.playerIdleRight;
        if(player.state.equals(Player.State.WALKING)) {
            if (player.isFacingLeft) {
                playerFrame = Assets.walkLeftAnimation.getKeyFrame(player.stateTime, true);
            } else {
                playerFrame = Assets.walkRightAnimation.getKeyFrame(player.stateTime, true);
            }
        }
        if (player.currentPowerUp != null) {
            switch (player.currentPowerUp.powerUpType) {
                case SPEED:
                    batch.draw(Assets.powerUp_speed, 520, 35, 35, 35);
                    break;
                case IMMORTAL:
                    batch.draw(Assets.powerUp_immortal, 520, 35, 35, 35);
                    break;
            }
        }
        batch.draw(playerFrame, player.position.x, player.position.y);
        player.particleEffect.draw(batch, Game.delta());
    }

    private void drawLife() {
        for (int i = 0; i < 3; i++) {
            batch.draw(Assets.lifeEmpty, 350 + (40 * i), 30);
        }
        for (int i = 0; i < player.getLives(); i++) {
            batch.draw(Assets.lifeFilled, 350 + (40 * i), 30);
        }
    }

    private void drawBalls() {
        int diameter;
        for (Ball ball : gsController.getBalls()) {
            diameter = ball.getRadius()*2;
            switch (ball.getSize()) {
                case SMALL:
                    batch.draw(Assets.ball_small, ball.position.x, ball.position.y, diameter, diameter);
                    break;
                case MIDDLE:
                    batch.draw(Assets.ball_middle, ball.position.x, ball.position.y, diameter, diameter);
                    break;
                case BIG:
                    batch.draw(Assets.ball_big, ball.position.x, ball.position.y, diameter, diameter);
                    break;
                case SMALLER:
                    batch.draw(Assets.ball_smaller, ball.position.x, ball.position.y, diameter, diameter);
                    break;
            }
        }
//        for (AnimationHelper currentAnimation : gsController.getLevel().currentAnimations) {
//            batch.draw(Assets.ballPoppingAnimation.getKeyFrame(currentAnimation.stateTime), currentAnimation.vector2.x, currentAnimation.vector2.y);
//        }
        gsController.getLevel().explosionParticle.draw(batch, Game.delta());
    }

    private void drawPoints() {
        gainedPointsString = "Points: "+gsController.getLevel().gainedPoints;
        font.draw(batch, gainedPointsString, 20, 460);
    }

    private void drawTimeBar() {
        TimeBar timeBar = gsController.getLevel().timeBar;
        shapeRenderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
        shapeRenderer.setColor(8, 8, 8, 1);
        shapeRenderer.filledRect(timeBar.position.x, timeBar.position.y, timeBar.timeLeft_x, 15);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Rectangle);
        shapeRenderer.setColor(0, 0, 0, 0);
        shapeRenderer.rect(timeBar.position.x, timeBar.position.y, timeBar.bounds.width, 15);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        float width = timeBar.bounds.width;
        float timebar_x = timeBar.position.x;
        shapeRenderer.line(width*timeBar.gold+timebar_x, timeBar.position.y-10, width*timeBar.gold+timebar_x, timeBar.position.y+20);
        shapeRenderer.line(width*timeBar.silver+timebar_x, timeBar.position.y-10, width*timeBar.silver+timebar_x, timeBar.position.y+20);
        shapeRenderer.end();
    }

    private void drawStates() {
        switch (gsController.getGameState()) {
            case paused:
                batch.draw(Assets.transparent, 0, 0);
                batch.draw(Assets.pauseState, 250, 160);
                break;
            case win:
                batch.draw(Assets.transparent, 0, 0);
                batch.draw(Assets.winState, 250, 160);
                break;
            case gameover:
                batch.draw(Assets.transparent, 0, 0);
                batch.draw(Assets.gameoverState, 250, 160);
                break;
            case ready:
                batch.draw(Assets.transparent, 0, 0);
                batch.draw(Assets.readyState, 180, 200);
                if (blinker) {
                    batch.draw(Assets.readyBlink, 280, 140);
                }
                blinkTimer++;
                if (blinkTimer > 25) {
                    blinkTimer = 0;
                    blinker = !blinker;
                }
                break;
            case playing:
                break;
        }
    }

    private void drawDebug() {
        if(DEBUG){
            playerCoords = "Player coords: "+player.position.x+", "+player.position.y;
            playerDim = "Player Dimension: "+player.bounds.width+", "+player.bounds.height;
            if(!gsController.getBalls().isEmpty()){
                ballPos = "Ball Position: "+gsController.getBalls().get(0).position.x+", "+gsController.getBalls().get(0).position.y;
            }
            playerLives = "Player Lives: "+player.getLives();
            if(gsController.getShot() != null)
                shotPos = "Shot coords: "+gsController.getShot().position.x+", "+gsController.getShot().position.y;
            font.draw(batch, playerCoords, 0, 470);
            font.draw(batch, playerDim, 0, 455);
            font.draw(batch, ballPos, 0, 440);
            font.draw(batch, playerLives, 0, 425);
            font.draw(batch, shotPos, 0, 410);
        }
    }
}