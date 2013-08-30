package controllers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import models.Ball;
import models.Level;
import models.Player;
import models.Shot;
import utils.Assets;
import utils.Button;
import managers.GameManager;
import utils.Pluvia;
import view.GameScreen;

import java.util.List;

import static controllers.GamescreenController.gameStateDef.paused;
import static controllers.GamescreenController.gameStateDef.playing;

public class GamescreenController {
    public enum gameStateDef { paused, playing, win, gameover, ready }
    gameStateDef gameState;
    Pluvia pluvia;
    GameScreen gameScreen;
    public Rectangle arrow_right;
    public Rectangle arrow_left;
    public Rectangle arrow_up;
    public Button resume;
    public Button restart;
    public Button exitGame;
    public Button nextLevel;

    public GamescreenController(Pluvia pluvia, GameScreen gameScreen) {
        arrow_left = new Rectangle(50, 8, 80, 80);
        arrow_right = new Rectangle(170, 8, 80, 80);
        arrow_up = new Rectangle(680, 0, 80, 80);
        resume = new Button(0, 0, Assets.resumeButton);
        restart = new Button(0, 0, Assets.restartButton);
        exitGame = new Button(0, 0, Assets.exitGameButton);
        nextLevel = new Button(0, 0, Assets.nextLevelButton);
        this.pluvia = pluvia;
        this.gameScreen = gameScreen;
        addButtonListeners();
    }

    public void update(){
        updateInput();
        if(gameState == playing) {
            pluvia.getLevelManager().currentLevel.update();
            if(getBalls().size() == 0){
                setGameState(gameStateDef.win);
                pluvia.getProgressManager().saveLevelProgress(pluvia.getLevelManager().currentLevelNumber, getCalculatedPoints());
            }
            if (getPlayer().getLives() == 0) {
                setGameState(gameStateDef.gameover);
            }
            if(getShot()!= null && getShot().position.y > 100){
                getPlayer().setShot(null);
            }
            if(getLevel().timeBar.finished){
                setGameState(gameStateDef.gameover);
            }
        }
    }

    private void updateInput() {
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            if(getGameState() == playing) {
                gameScreen.inputManager.LEFT = gameScreen.inputManager.isTouched(arrow_left, 50, 10, 70, 50);
                gameScreen.inputManager.RIGHT = gameScreen.inputManager.isTouched(arrow_right, 10, 50, 70, 50);
                gameScreen.inputManager.SPACE = gameScreen.inputManager.isTouched(arrow_up, 50, 50, 70, 50);
                if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                    setGameState(paused);
                }
            }
        } else {
            if(getGameState() == playing) {
                gameScreen.inputManager.update();
            }
            if(gameScreen.inputManager.ESCAPE) {
                if(getGameState() == playing) {
                    setGameState(paused);
                } else if(getGameState() == paused) {
                    setGameState(playing);
                }
            }
        }
    }

    private int getCalculatedPoints() {
        if(getLevel().timeBar.timeLeft_x > getLevel().timeBar.gold * 480) {
            return 3;
        } else if(getLevel().timeBar.timeLeft_x > getLevel().timeBar.silver * 480) {
            return 2;
        }
        return 1;
    }

    private void addButtonListeners() {
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setGameState(playing);
            }
        });
        restart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restart();
            }
        });
        exitGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pluvia.getScreenManager().changeTo("LevelScreen");
            }
        });
        nextLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadNextLevel();
                setGameState(playing);
            }
        });
//        gameScreen.stage.addListener(new ClickListener() {
//            @Override
//            public boolean keyTyped(InputEvent event, char character) {
//                if (gameState == paused && event.getKeyCode() == com.badlogic.gdx.InputManager.Keys.BACK) {
//                    setGameState(playing);
//                }
//                return false;
//            }
//        });
        gameScreen.stage.addActor(resume);
        gameScreen.stage.addActor(restart);
        gameScreen.stage.addActor(exitGame);
        gameScreen.stage.addActor(nextLevel);
        gameScreen.stage.setViewport(GameManager.VIRTUAL_WIDTH, GameManager.VIRTUAL_HEIGHT, true);

    }

    public gameStateDef getGameState(){
        return gameState;
    }
    public void setGameState(gameStateDef gameState){
        gameScreen.inputManager.LEFT = false;
        gameScreen.inputManager.RIGHT = false;
        gameScreen.inputManager.SPACE = false;
        this.gameState = gameState;
        repositionButtons();
    }

    public void loadNextLevel() {
        pluvia.getLevelManager().loadNextLevel();
    }

    public void restart() {
        pluvia.getLevelManager().reloadLevel();
        setGameState(playing);
    }

    private void repositionButtons() {
        switch (gameState) {
            case playing:
                nextLevel.setVisible(false);
                resume.setVisible(false);
                restart.setVisible(false);
                exitGame.setVisible(false);
                break;
            case paused:
                nextLevel.setVisible(false);
                resume.setVisible(true);
                restart.setVisible(true);
                exitGame.setVisible(true);

                resume.setBounds(300, 240, 80, 50);
                restart.setBounds(420, 240, 80, 50);
                exitGame.setBounds(350, 180, 80, 50);
                break;
            case win:
                resume.setVisible(false);
                restart.setVisible(true);
                exitGame.setVisible(true);
                nextLevel.setVisible(true);

                nextLevel.setBounds(420, 240, 80, 50);
                restart.setBounds(300, 240, 80, 50);
                exitGame.setBounds(350, 180, 80, 50);
                getPlayer().particleEffect.setPosition(100000, 1000000); //hack
                break;
            case gameover:
                resume.setVisible(false);
                nextLevel.setVisible(false);
                restart.setVisible(true);
                exitGame.setVisible(true);

                restart.setBounds(420, 240, 80, 50);
                exitGame.setBounds(300, 240, 80, 50);
                break;
        }
    }

    public Player getPlayer() {
        return pluvia.getLevelManager().currentLevel.getPlayer();
    }
    public List<Ball> getBalls(){
        return pluvia.getLevelManager().currentLevel.getBalls();
    }
    public Shot getShot(){
        return pluvia.getLevelManager().currentLevel.getPlayer().getShot();
    }

    public Level getLevel(){
        return pluvia.getLevelManager().currentLevel;
    }
}