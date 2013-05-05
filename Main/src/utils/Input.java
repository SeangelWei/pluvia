package utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Input {
    public boolean LEFT;
    public boolean RIGHT;
    public boolean SPACE;
    public boolean ESCAPE;

    public boolean isTouched(Rectangle rectangle, int margin) {
        for (int i = 0; i < 10; i++) {
            if(Gdx.input.isTouched(i)) {
                Vector2 touch = new Vector2(Gdx.input.getX(i), Gdx.graphics.getHeight()-Gdx.input.getY(i));
                if(pointInRectangle(rectangle, touch, margin)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pointInRectangle(Rectangle r, Vector2 touchInput, int margin) {
        return (touchInput.x >= r.x-margin && touchInput.x <= r.x + margin + r.width && touchInput.y >= r.y - margin &&
                touchInput.y <= r.y + margin + r.height);
    }

    public void update() {
        LEFT = Gdx.input.isKeyPressed(21);
        RIGHT = Gdx.input.isKeyPressed(22);
        SPACE = Gdx.input.isKeyPressed(62);
        ESCAPE = Gdx.input.isKeyPressed(131);
    }
}
