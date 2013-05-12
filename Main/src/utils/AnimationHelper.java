package utils;

import com.badlogic.gdx.math.Vector2;

public class AnimationHelper {
    public float stateTime = 0;
    public boolean removeIt = false;
    public Vector2 vector2 = new Vector2();
    public AnimationHelper(Vector2 vector) {
        vector2 = vector;
    }
}
