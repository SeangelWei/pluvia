package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utils.Assets;
import utils.Input;
import utils.MyScreen;
import utils.Pluvia;

/**
 * This screen is just for testing
 */
public class Testscreen extends MyScreen {
    ShapeRenderer shapeRenderer;
    float opaque;

    public Testscreen(Pluvia pluvia) {
        super(pluvia);
        shapeRenderer = new ShapeRenderer();
        opaque = 1;
    }

    @Override
    public void render(Input input) {
        batch.begin();
        batch.draw(Assets.levelscreen_bg, 0, 0);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.FilledCircle);
        shapeRenderer.setColor(0, 0, 0, opaque);
        shapeRenderer.filledCircle(200, 200, 80);
        shapeRenderer.end();
        opaque-=0.01;

        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.FilledCircle);
        shapeRenderer.setColor(255, 0, 0, opaque);
        shapeRenderer.filledCircle(500, 200, 80);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void resize(int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void show() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void hide() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
