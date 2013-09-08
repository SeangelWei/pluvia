package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import controllers.GamescreenController;
import managers.GameManager;
import models.Ball;
import models.Player;
import models.PowerUp;
import models.TimeBar;

public class WorldRenderer {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    boolean DEBUG = false;
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
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PipeDream.ttf"));
        font = generator.generateFont(30);
        generator.dispose();
    }

    public void render() {
        player = gsController.getPlayer();
        batch.begin();
        drawLevel();
        drawPlayerAndShot();
        drawLife();
        drawPowerUp();
        drawBalls();
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
                    if (powBlinkerTimer % 5 == 0) {
                        drawLonelyPowerUp(powerUp, position);
                    }
                    if (powBlinkerTimer > powerUp.getLifeTime()) {
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
        if (gsController.getShot() != null) {
            batch.draw(Assets.shot, gsController.getShot().position.x, gsController.getShot().position.y);
            Assets.shotEffect.draw(batch, GameManager.delta());
        }
        batch.draw(Assets.gs_bar, 0, 0);
        batch.draw(Assets.arrow_left, gsController.arrow_left.x, gsController.arrow_left.y);
        batch.draw(Assets.arrow_right, gsController.arrow_right.x, gsController.arrow_right.y);
        batch.draw(Assets.arrow_up, gsController.arrow_up.x, gsController.arrow_up.y);
    }

    private void drawPlayerAndShot() {
        if (player.isTouched) {
            playerBlinkerTimer++;
            if (playerBlinkerTimer % 5 == 0) {
                drawPlayer();
            }
            if (playerBlinkerTimer > 100) {
                player.isTouched = false;
                playerBlinkerTimer = 0;
            }
        } else {
            drawPlayer();
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
        Assets.playerEffect.draw(batch, GameManager.delta());
    }

    private void drawPlayer() {
        if (player.state.equals(Player.State.WALKING)) {
            if (player.isFacingLeft) {
                playerFrame = Assets.walkLeftAnimation.getKeyFrame(player.stateTime, true);
            } else {
                playerFrame = Assets.walkRightAnimation.getKeyFrame(player.stateTime, true);
            }
        } else if (player.state.equals(Player.State.IDLE)) {
            playerFrame = Assets.idleAnimation.getKeyFrame(player.stateTime, true);
        }
        batch.draw(playerFrame, player.position.x, player.position.y, 40, 58);
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
            diameter = ball.getRadius() * 2;
            switch (ball.getSize()) {
                case SMALL:
                    switch (ball.getColor()) {
                        case BLUE:
                            batch.draw(Assets.ball_small_blue, ball.position.x, ball.position.y, diameter, diameter);
                            break;
                        case RED:
                            batch.draw(Assets.ball_small_red, ball.position.x, ball.position.y, diameter, diameter);
                            break;
                    }
                    break;
                case MIDDLE:
                    switch (ball.getColor()) {
                        case BLUE:
                            batch.draw(Assets.ball_middle_blue, ball.position.x, ball.position.y, diameter, diameter);
                            break;
                        case RED:
                            batch.draw(Assets.ball_middle_red, ball.position.x, ball.position.y, diameter, diameter);
                            break;
                    }
                    break;
                case BIG:
                    switch (ball.getColor()) {
                        case BLUE:
                            batch.draw(Assets.ball_big_blue, ball.position.x, ball.position.y, diameter, diameter);
                            break;
                        case RED:
                            batch.draw(Assets.ball_big_red, ball.position.x, ball.position.y, diameter, diameter);
                            break;
                    }
                    break;
                case SMALLER:
                    switch (ball.getColor()) {
                        case BLUE:
                            batch.draw(Assets.ball_smaller_blue, ball.position.x, ball.position.y, diameter, diameter);
                            break;
                        case RED:
                            batch.draw(Assets.ball_smaller_red, ball.position.x, ball.position.y, diameter, diameter);
                            break;
                    }
                    break;
            }
            Assets.ballEffect.draw(batch, GameManager.delta());

        }
//        for (AnimationHelper currentAnimation : gsController.getLevel().currentAnimations) {
//            batch.draw(Assets.ballPoppingAnimation.getKeyFrame(currentAnimation.stateTime), currentAnimation.vector2.x, currentAnimation.vector2.y);
//        }
        Assets.explosionEffect.draw(batch, GameManager.delta());
    }

    private void drawTimeBar() {
        TimeBar timeBar = gsController.getLevel().timeBar;
        float width = timeBar.bounds.width;
        float timebar_x = timeBar.position.x;
        shapeRenderer.setProjectionMatrix(GameManager.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(timeBar.position.x - 1, timeBar.position.y, width + 15, 17);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0.9f, 0.2f, 0.1f, 1));
        shapeRenderer.rect(timeBar.position.x - 1, timeBar.position.y, timeBar.timeLeft_x + 15, 17);
        shapeRenderer.end();
        batch.begin();
        Assets.timeBarEffect.draw(batch, GameManager.delta());
        batch.draw(Assets.timeBar, timeBar.position.x - 5, timeBar.position.y - 7);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(width * timeBar.gold + timebar_x, timeBar.position.y - 10, 3, 35);
        shapeRenderer.rect(width * timeBar.silver + timebar_x, timeBar.position.y - 10, 3, 35);
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
                for (int i = 0; i < 3; i++) {
                    batch.draw(Assets.starEmpty, 340 + (i * 45), 305, 40, 40);
                }
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
        if (DEBUG) {
            String ballPos = "";
            String shotPos = "";
            if (!gsController.getBalls().isEmpty()) {
                ballPos = "Ball Position: " + gsController.getBalls().get(0).position.x + ", " + gsController.getBalls().get(0).position.y;
            }
            if (gsController.getShot() != null)
                shotPos = "Shot coords: " + gsController.getShot().position.x + ", " + gsController.getShot().position.y;
            font.draw(batch, "Player coords: " + player.position.x + ", " + player.position.y, 0, 470);
            font.draw(batch, "Player Dimension: " + player.bounds.width + ", " + player.bounds.height, 0, 455);
            font.draw(batch, ballPos, 0, 440);
            font.draw(batch, "Player Lives: " + player.getLives(), 0, 425);
            font.draw(batch, shotPos, 0, 410);
        }
    }
}