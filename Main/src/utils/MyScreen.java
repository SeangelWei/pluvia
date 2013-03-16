package utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class MyScreen {
    protected SpriteBatch batch;
    protected ScreenManager screenManager;

    public MyScreen(SpriteBatch batch, ScreenManager screenManager){
        this.batch = batch;
        this.screenManager = screenManager;
    }

    public abstract void render (Input input);

    public abstract void resize (int width, int height);

    public abstract void show ();

    public abstract void hide ();

    public abstract void pause ();

    public abstract void resume ();

    public abstract void dispose ();

    public abstract void init ();

    public static boolean pointInRectangle (Rectangle r, Vector2 touchInput) {
        return (touchInput.x >= r.x && touchInput.x <= r.x + r.width && touchInput.y >= r.y && touchInput.y <= r.y + r.height);
    }
}
