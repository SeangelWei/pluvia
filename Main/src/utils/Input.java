package utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Input {
    public boolean LEFT;
    public boolean RIGHT;
    public boolean SPACE;
    public boolean ESCAPE;

    public boolean isTouched(Rectangle rectangle, int marginLeft, int marginRight, int marginTop, int marginBottom) {
        for (int i = 0; i < 10; i++) {
            if(Gdx.input.isTouched(i)) {
                Vector2 newTouch = new Vector2(Gdx.input.getX(i)/Game.scale, (Gdx.graphics.getHeight() - Gdx.input.getY(i))/Game.scale);
                if(pointInRectangle(rectangle, newTouch, marginLeft, marginRight, marginTop, marginBottom)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pointInRectangle(Rectangle r, Vector2 touchInput, int marginLeft, int marginRight, int marginTop, int marginBottom) {
        return (touchInput.x >= r.x-marginLeft && touchInput.x <= r.x + marginRight + r.width && touchInput.y >= r.y - marginTop &&
                touchInput.y <= r.y + marginBottom + r.height);
    }

    public void update() {
        LEFT = Gdx.input.isKeyPressed(21);
        RIGHT = Gdx.input.isKeyPressed(22);
        SPACE = Gdx.input.isKeyPressed(62);
        ESCAPE = Gdx.input.isKeyPressed(131);
    }

    public void clear() {
        ESCAPE = false;
    }
}
