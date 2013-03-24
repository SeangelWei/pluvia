package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import model.Ball;
import model.Level;
import model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    public Level currentLevel;
    Player player;
    List<Ball> balls = new ArrayList<Ball>();

    /**
     * can be passed either level or fileName
     * if fileName is empty -> "" then it will be taken the levelNumber
     */
    public void loadLevel(int level, String fileName){
        XmlReader reader = new XmlReader();
        XmlReader.Element file;
        try {
            if(fileName.equals("")) {
                file = reader.parse(Gdx.files.internal("levels/level"+level+".xml"));
            } else {
                file = reader.parse(Gdx.files.internal("levels/"+fileName));
            }
            int playerX = Integer.parseInt(file.getChildByName("player").getAttribute("x"));
            int levelSpeed = Integer.parseInt(file.getChildByName("levelSpeed").getAttribute("speed"));
            for (int i = 0; i < file.getChildByName("balls").getChildCount() ; i++) {
                XmlReader.Element ball = file.getChildByName("balls").getChild(i);
                int ballX = Integer.parseInt(ball.getAttribute("x"));
                int ballY = Integer.parseInt(ball.getAttribute("y"));
                int vectorX = Integer.parseInt(ball.getAttribute("xVector"));
                String size = ball.getAttribute("size");
                Ball.sizeDef[] allSizes = Ball.sizeDef.values();
                for (Ball.sizeDef sizeDef : allSizes) {
                    if(size.equals(sizeDef.toString())){
                        balls.add(new Ball(ballX, ballY, sizeDef, vectorX));
                    }
                }
                player = new Player(playerX);
                currentLevel = null;
                currentLevel = new Level(0, 0);
                currentLevel.init(player, balls, levelSpeed);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadNextLevel() {
        //get current Levelnumber
        //load the next level
    }

    public void reloadLevel() {
        currentLevel = null;
        currentLevel = new Level(0,0);
        currentLevel.init(player, balls, 25);
    }
}