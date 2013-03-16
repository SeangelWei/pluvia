package controllers;

import com.badlogic.gdx.math.Rectangle;
import model.Ball;
import model.Level;
import model.Player;
import model.Shot;
import utils.LevelManager;

import java.util.List;

public class GamescreenController {
    public enum gameStateDef { paused, playing, win, gameover, ready }
    gameStateDef gameState;
    public LevelManager levelManager;
    public Rectangle arrow_right;
    public Rectangle arrow_left;
    public Rectangle arrow_up;
    public Rectangle resume;
    public Rectangle restart;
    public Rectangle exitGame;
    public Rectangle nextLevel;

    public GamescreenController(LevelManager levelManager) {
        arrow_left = new Rectangle(40, 8, 80, 80);
        arrow_right = new Rectangle(140, 8, 80, 80);
        arrow_up = new Rectangle(680, 0, 80, 80);
        resume = new Rectangle();
        restart = new Rectangle();
        exitGame = new Rectangle();
        nextLevel = new Rectangle();
        this.levelManager = levelManager;
    }

    public void update(){
        switch (gameState) {
            case playing:
                levelManager.currentLevel.update();
                if(getBalls().size() == 0){
                    setGameState(gameStateDef.win);
                    // write infos to filesystem
                }
                if (getPlayer().getLives() == 0) {
                    setGameState(gameStateDef.gameover);
                    // calculate points or something
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
    public gameStateDef getGameState(){
        return gameState;
    }
    public void setGameState(gameStateDef gameState){
        this.gameState = gameState;
        repositionButtons();
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
        return levelManager.currentLevel.getPlayer();
    }
    public List<Ball> getBalls(){
        return levelManager.currentLevel.getBalls();
    }
    public Shot getShot(){
        return levelManager.currentLevel.getPlayer().getShot();
    }

    public Level getLevel(){
        return levelManager.currentLevel;
    }
}