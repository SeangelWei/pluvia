package controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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

import static controllers.GamescreenController.gameStateDef.playing;

public class GamescreenController {
    public enum gameStateDef { paused, playing, win, gameover, ready }
    gameStateDef gameState;
    Pluvia pluvia;
    Gamescreen gamescreen;
    public Button arrow_right;
    public Button arrow_left;
    public Button arrow_up;
    public Button resume;
    public Button restart;
    public Button exitGame;
    public Button nextLevel;

    public GamescreenController(Pluvia pluvia, Gamescreen gamescreen) {
        arrow_left = new Button(40, 8, Assets.arrow_left);
        arrow_right = new Button(140, 8, Assets.arrow_right);
        arrow_up = new Button(680, 0, Assets.arrow_up);
        resume = new Button(0, 0, Assets.resumeButton);
        restart = new Button(0, 0, Assets.restartButton);
        exitGame = new Button(0, 0, Assets.exitGameButton);
        nextLevel = new Button(0, 0, Assets.nextLevelButton);
        this.pluvia = pluvia;
        this.gamescreen = gamescreen;
        addButtonListeners();
    }

    public void update(){
        switch (gameState) {
            case playing:
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
                break;
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
        arrow_left.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                gamescreen.LEFT = true;
                return false;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                gamescreen.LEFT = false;
            }
        });
        arrow_right.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(gameState == gameStateDef.playing) {
                    gamescreen.RIGHT = true;
                }
                return false;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(gameState == gameStateDef.playing) {
                    gamescreen.RIGHT = false;
                }
            }
        });
        arrow_right.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(gameState == gameStateDef.playing) {
                    gamescreen.UP = true;
                }
                return false;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(gameState == gameStateDef.playing) {
                    gamescreen.UP = false;
                }
            }
        });
        gamescreen.stage.addActor(resume);
        gamescreen.stage.addActor(restart);
        gamescreen.stage.addActor(exitGame);
        gamescreen.stage.addActor(nextLevel);
        gamescreen.stage.addActor(arrow_left);
        gamescreen.stage.addActor(arrow_right);
        gamescreen.stage.addActor(arrow_up);
    }

    public gameStateDef getGameState(){
        return gameState;
    }
    public void setGameState(gameStateDef gameState){
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
                break;
            case gameover:
                resume.setVisible(false);
                nextLevel.setVisible(true);
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