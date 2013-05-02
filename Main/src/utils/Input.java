package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class Input implements InputProcessor {
    public boolean LEFT;
    public boolean RIGHT;
    public boolean SPACE;
    public boolean ESCAPE;
    public boolean BACK;
    public HashMap<Integer, Vector2> fingers = new HashMap<Integer, Vector2>();

    public void clear() {
        ESCAPE = false;
        SPACE = false;
        BACK = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == 21) LEFT = true;
        if(keycode == 22) RIGHT = true;
        if(keycode == 62) SPACE = true;
        if(keycode == 131) ESCAPE = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == 21) LEFT = false;
        if(keycode == 22) RIGHT = false;
        if(keycode == com.badlogic.gdx.Input.Keys.BACK){
            BACK = true;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        fingers.put(pointer, new Vector2(screenX, Gdx.graphics.getHeight() - screenY));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        fingers.remove(pointer);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        fingers.get(pointer).set(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isPressed(Rectangle rectangle) {
        for (int i = 0; i < fingers.size(); i++) {
            Vector2 touchVector = fingers.get(i);
            if(pointInRectangle(rectangle, touchVector)) {
                return true;
            }
        }
        return false;
    }

    private boolean pointInRectangle (Rectangle r, Vector2 touchInput) {
        return (touchInput.x >= r.x && touchInput.x <= r.x + r.width && touchInput.y >= r.y && touchInput.y <= r.y + r.height);
    }
}
