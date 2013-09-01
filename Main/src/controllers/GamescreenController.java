package controllers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import managers.GameManager;
import models.Ball;
import models.Level;
import models.Player;
import models.Shot;
import utils.Assets;
import utils.Button;
import utils.Pluvia;
import view.GameScreen;

import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static controllers.GamescreenController.gameStateDef.paused;
import static controllers.GamescreenController.gameStateDef.playing;

public class GamescreenController {
    public enum gameStateDef {paused, playing, win, gameover, ready}

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
        gameScreen.stage.setViewport(GameManager.VIRTUAL_WIDTH, GameManager.VIRTUAL_HEIGHT, false);
        addButtonListeners();
    }

    public void update() {
        updateInput();
        updatePlaying();
    }

    private void updatePlaying() {
        if (gameState == playing) {
            pluvia.getLevelManager().currentLevel.update();
            if (getBalls().size() == 0) {
                setGameState(gameStateDef.win);
                pluvia.getProgressManager().saveLevelProgress(pluvia.getLevelManager().currentLevelNumber, getCalculatedPoints());
            }
            if (getPlayer().getLives() == 0) {
                setGameState(gameStateDef.gameover);
            }
            if (getShot() != null && getShot().position.y > 100) {
                getPlayer().setShot(null);
            }
            if (getLevel().timeBar.finished) {
                setGameState(gameStateDef.gameover);
            }
        }
    }

    private void updateInput() {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            if (getGameState() == playing) {
                gameScreen.inputManager.LEFT = gameScreen.inputManager.isTouched(arrow_left, 50, 10, 70, 50);
                gameScreen.inputManager.RIGHT = gameScreen.inputManager.isTouched(arrow_right, 10, 50, 70, 50);
                gameScreen.inputManager.SPACE = gameScreen.inputManager.isTouched(arrow_up, 50, 50, 70, 50);
                if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                    setGameState(paused);
                }
            }
        } else {
            if (getGameState() == playing) {
                gameScreen.inputManager.update();
            }
            if (gameScreen.inputManager.ESCAPE) {
                if (getGameState() == playing) {
                    setGameState(paused);
                } else if (getGameState() == paused) {
                    setGameState(playing);
                }
            }
        }
    }

    private int getCalculatedPoints() {
        int gainedStars;
        if (getLevel().timeBar.timeLeft_x > getLevel().timeBar.gold * getLevel().timeBar.bounds.width) {
            gainedStars = 3;
        } else if (getLevel().timeBar.timeLeft_x > getLevel().timeBar.silver * getLevel().timeBar.bounds.width) {
            gainedStars = 2;
        } else {
            gainedStars = 1;
        }
        getLevel().gainedStars = gainedStars;
        return gainedStars;
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
    }

    public gameStateDef getGameState() {
        return gameState;
    }

    public void setGameState(gameStateDef gameState) {
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

    //will be called up when changing states
    private void repositionButtons() {
        if (gameScreen.stage.getActors().size > 4) {
            if (gameScreen.stage.getRoot().findActor("star0") != null) {
                gameScreen.stage.getRoot().findActor("star0").remove();
            }
            if (gameScreen.stage.getRoot().findActor("star1") != null) {
                gameScreen.stage.getRoot().findActor("star1").remove();
            }
            if (gameScreen.stage.getRoot().findActor("star2") != null) {
                gameScreen.stage.getRoot().findActor("star2").remove();
            }
        }
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
                startStarAction();
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

    private void startStarAction() {
        Drawable splashDrawable = new TextureRegionDrawable(new TextureRegion(Assets.starFilled));
        for (int i = 0; i < getCalculatedPoints(); i++) {
            Image star = new Image(splashDrawable);
            star.setPosition(MathUtils.random(200, 600), MathUtils.random(120, 380));
            star.setOrigin(20, 20);
            star.getColor().a = 0f;
            star.setRotation(MathUtils.random(360));
            star.setName("star" + i);
            star.addAction(parallel(sequence(delay(0.3f * i), parallel(fadeIn(0.7f), moveTo(340 + (i * 45), 305, 0.5f), rotateTo(360, 0.5f)),
                    new Action() {
                        @Override
                        public boolean act(
                                float delta) {
                            Assets.starSound.setVolume(Assets.starSound.play(), GameManager.soundVolume);
                            return true;
                        }
                    })));
            gameScreen.stage.addActor(star);
        }
    }

    public Player getPlayer() {
        return pluvia.getLevelManager().currentLevel.getPlayer();
    }

    public List<Ball> getBalls() {
        return pluvia.getLevelManager().currentLevel.getBalls();
    }

    public Shot getShot() {
        return pluvia.getLevelManager().currentLevel.getPlayer().getShot();
    }

    public Level getLevel() {
        return pluvia.getLevelManager().currentLevel;
    }
}