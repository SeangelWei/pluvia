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

    public abstract void resize(int width, int height);

    public abstract void show();

    public abstract void hide();

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public abstract void init();
}
