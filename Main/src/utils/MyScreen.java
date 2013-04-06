package utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class MyScreen {
    protected SpriteBatch batch;
    protected ScreenManager screenManager;
    protected Progress progress;
    protected Stage stage;

    public MyScreen(Pluvia pluvia){
        this.batch = pluvia.getBatch();
        this.screenManager = pluvia.getScreenManager();
        this.progress = pluvia.getProgress();
        this.stage = new Stage();
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
