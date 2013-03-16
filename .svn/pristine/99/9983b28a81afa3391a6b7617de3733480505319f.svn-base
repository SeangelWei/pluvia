package utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    public final Vector2 position;
    public final Rectangle bounds;

    public GameObject(float x, float y) {
        position = new Vector2(x, y);
        bounds = new Rectangle();
        bounds.setX(x);
        bounds.setY(y);
    }

    public abstract void update();
}
