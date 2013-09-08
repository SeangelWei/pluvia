package utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelIcon extends Actor {
    public Integer level;
    public String fileName;
    public int x, y;
    public int blockWidth;
    public boolean isEnabled;
    public Vector2 disabledPosition;
    public Vector2 disabledVelocity;
    public float disabledSin = 0;

    public LevelIcon(int x, int y, int blockWidth, int levelCounter) {
        super.setBounds(x, y, blockWidth, blockWidth);
        this.x = x;
        this.y = y;
        this.blockWidth = blockWidth;
        this.level = levelCounter;
        fileName = "level" + level + ".xml";
        isEnabled = false;
        disabledPosition = new Vector2(x, y);
        disabledVelocity = new Vector2();
    }
}
