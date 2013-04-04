package controllers;

import com.badlogic.gdx.math.Rectangle;
import model.Ball;
import model.Level;
import model.Player;
import model.Shot;
import utils.Pluvia;

import java.util.List;

import static controllers.GamescreenController.gameStateDef.playing;

public class GamescreenController {
    public enum gameStateDef { paused, playing, win, gameover, ready }
    gameStateDef gameState;
    Pluvia pluvia;
    public Rectangle arrow_right;
    public Rectangle arrow_left;
    public Rectangle arrow_up;
    public Rectangle resume;
    public Rectangle restart;
    public Rectangle exitGame;
    public Rectangle nextLevel;

    public GamescreenController(Pluvia pluvia) {
        arrow_left = new Rectangle(40, 8, 80, 80);
        arrow_right = new Rectangle(140, 8, 80, 80);
        arrow_up = new Rectangle(680, 0, 80, 80);
        resume = new Rectangle();
        restart = new Rectangle();
        exitGame = new Rectangle();
        nextLevel = new Rectangle();
        this.pluvia = pluvia;
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
                resume.set(300, 240, 80, 50);
                restart.set(420, 240, 80, 50);
                exitGame.set(350, 180, 80, 50);
                break;
            case win:
                nextLevel.set(420, 240, 80, 50);
                restart.set(300, 240, 80, 50);
                exitGame.set(350, 180, 80, 50);
                break;
            case gameover:
                restart.set(420, 240, 80, 50);
                exitGame.set(300, 240, 80, 50);
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