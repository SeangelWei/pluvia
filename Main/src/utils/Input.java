package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class Input implements InputProcessor {
    public boolean LEFT;
    public boolean RIGHT;
    public boolean SPACE;
    public Vector2 TOUCH;
    public boolean ESCAPE;

    public Input(){
        TOUCH = new Vector2();
    }

    public void clear() {
        ESCAPE = false;
        SPACE = false;
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
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        TOUCH.x = screenX;
        TOUCH.y = Gdx.graphics.getHeight()-screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        TOUCH.x = 0;
        TOUCH.y = 0;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
}
