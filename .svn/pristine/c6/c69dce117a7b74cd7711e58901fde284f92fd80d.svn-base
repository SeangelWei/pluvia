package utils;

import model.Ball;
import model.Level;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    public Level currentLevel;
    Player player;
    List<Ball> balls = new ArrayList<Ball>();

    public void loadLevel(int level, String fileName){
        // Open File and fill values

        int playerX=500;
        Player player = new Player(playerX);
        List<Ball> balls = new ArrayList<Ball>();
        balls.add(new Ball(50, 300, Ball.sizeDef.BIG, 6f));
        currentLevel = null;
        currentLevel = new Level(0,0);
        currentLevel.init(player, balls, 25);
    }

    public void loadSettings() {
        //load playersettings from external(?) filesystem
        //these can be how many levels he finished...
    }

    public void loadNextLevel() {
        //get current Levelnumber
        //load the next level
        reloadLevel();
    }

    public void reloadLevel() {
        int playerX=500;
        Player player = new Player(playerX);
        List<Ball> balls = new ArrayList<Ball>();
        balls.add(new Ball(50, 300, Ball.sizeDef.BIG, 6f));
        currentLevel = null;
        currentLevel = new Level(0,0);
        currentLevel.init(player, balls, 25);
    }
}