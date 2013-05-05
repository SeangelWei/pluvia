package controllers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import model.Ball;
import model.Level;
import model.Player;
import model.Shot;
import utils.Assets;
import utils.Button;
import utils.Pluvia;
import view.Gamescreen;

import java.util.List;

import static controllers.GamescreenController.gameStateDef.paused;
import static controllers.GamescreenController.gameStateDef.playing;

public class GamescreenController {
    public enum gameStateDef { paused, playing, win, gameover, ready }
    gameStateDef gameState;
    Pluvia pluvia;
    Gamescreen gamescreen;
    public Rectangle arrow_right;
    public Rectangle arrow_left;
    public Rectangle arrow_up;
    public Button resume;
    public Button restart;
    public Button exitGame;
    public Button nextLevel;

    public GamescreenController(Pluvia pluvia, Gamescreen gamescreen) {
        arrow_left = new Rectangle(50, 8, 80, 80);
        arrow_right = new Rectangle(170, 8, 80, 80);
        arrow_up = new Rectangle(680, 0, 80, 80);
        resume = new Button(0, 0, Assets.resumeButton);
        restart = new Button(0, 0, Assets.restartButton);
        exitGame = new Button(0, 0, Assets.exitGameButton);
        nextLevel = new Button(0, 0, Assets.nextLevelButton);
        this.pluvia = pluvia;
        this.gamescreen = gamescreen;
        addButtonListeners();
    }

    public void update(){
        if(gameState == playing) {
            updateInput();
            pluvia.getLevelManager().currentLevel.update();
            if(getBalls().size() == 0){
                setGameState(gameStateDef.win);
                pluvia.getProgress().saveLevelProgress(pluvia.getLevelManager().currentLevelNumber, getCalculatedPoints());
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
            gamescreen.input.LEFT = gamescreen.input.isTouched(arrow_left, 50);
            gamescreen.input.RIGHT = gamescreen.input.isTouched(arrow_right, 50);
            gamescreen.input.SPACE = gamescreen.input.isTouched(arrow_up, 50);
        } else {
            gamescreen.input.update();
            if(gamescreen.input.ESCAPE) {
                if(getGameState() == playing) {
                    setGameState(paused);
                }
                if(getGameState() == paused) {
                    setGameState(playing);
                }
            }
        }
    }

    private int getCalculatedPoints() {
        return ((int) (Math.random() * 3))+1;
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
        gamescreen.stage.addActor(resume);
        gamescreen.stage.addActor(restart);
        gamescreen.stage.addActor(exitGame);
        gamescreen.stage.addActor(nextLevel);
    }

    public gameStateDef getGameState(){
        return gameState;
    }
    public void setGameState(gameStateDef gameState){
        gamescreen.input.LEFT = false;
        gamescreen.input.RIGHT = false;
        gamescreen.input.SPACE = false;
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
                Gdx.input.setInputProcessor(gamescreen.stage);
                nextLevel.setVisible(false);
                resume.setVisible(true);
                restart.setVisible(true);
                exitGame.setVisible(true);

                resume.setBounds(300, 240, 80, 50);
                restart.setBounds(420, 240, 80, 50);
                exitGame.setBounds(350, 180, 80, 50);
                break;
            case win:
                Gdx.input.setInputProcessor(gamescreen.stage);
                resume.setVisible(false);
                restart.setVisible(true);
                exitGame.setVisible(true);
                nextLevel.setVisible(true);

                nextLevel.setBounds(420, 240, 80, 50);
                restart.setBounds(300, 240, 80, 50);
                exitGame.setBounds(350, 180, 80, 50);
                break;
            case gameover:
                Gdx.input.setInputProcessor(gamescreen.stage);
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