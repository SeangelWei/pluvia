package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import controllers.GamescreenController;
import model.Ball;
import model.Player;

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
        drawBalls();
        drawPoints();
        batch.end();
        drawTimeBar(); // the shapeRenderer is in this method active
        batch.begin();
        drawStates();
        drawDebug();
        batch.end();
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
        batch.draw(playerFrame, player.position.x, player.position.y);
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
    }

    private void drawPoints() {
        gainedPointsString = "Points: "+gsController.getLevel().gainedPoints;
        font.draw(batch, gainedPointsString, 20, 460);
    }

    private void drawTimeBar() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
        shapeRenderer.setColor(8, 8, 8, 1);
        shapeRenderer.filledRect(gsController.getLevel().timeBar.position.x, gsController.getLevel().timeBar.position.y, gsController.getLevel().timeBar.bounds.width, 15);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Rectangle);
        shapeRenderer.setColor(0, 0, 0, 0);
        shapeRenderer.rect(gsController.getLevel().timeBar.position.x, gsController.getLevel().timeBar.position.y, 480, 15);
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