package utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class MyScreen {
    protected SpriteBatch batch;
    protected Pluvia pluvia;
    public Stage stage;

    public MyScreen(Pluvia pluvia) {
        this.stage = new Stage();
        this.batch = pluvia.getBatch();
        this.pluvia = pluvia;
    }

    public abstract void render();

    /**
     * will be called when changing to another screen
     */
    public abstract void hide();

    /**
     * will be called when the application will be pushed to background.
     * for example an incoming call
     */
    public abstract void pause();

    /**
     * will be called when changed to this screen
     */
    public abstract void init();
}
