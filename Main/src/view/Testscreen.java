package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import utils.Assets;
import utils.MyScreen;
import utils.Pluvia;

/**
 * This screen is just for testing
 */
public class Testscreen extends MyScreen {
    ShapeRenderer shapeRenderer;
    float opaque;
    BitmapFont font;

    public Testscreen(Pluvia pluvia) {
        super(pluvia);
        shapeRenderer = new ShapeRenderer();
        opaque = 1;
        font = new BitmapFont(Gdx.files.internal("gui/arial-15.fnt"),
                Gdx.files.internal("gui/arial-15.png"), false);
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(Assets.levelscreen_bg, 0, 0);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
        shapeRenderer.setColor(0, 0, 0, opaque);
        shapeRenderer.filledRect(0, 0, 50, 50);
        shapeRenderer.end();
        opaque-=0.01;

        stage.draw();
    }

    @Override
    public void init() {
        TextButton.TextButtonStyle asdf;
        asdf = new TextButton.TextButtonStyle();
        asdf.font = font;
        TextButton thatButton = new TextButton("Le Button", asdf);
        thatButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("just touchedUP that button");
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("touched down that button");
                return false;
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("yooouuuuu...you just pressed thaButton");
            }
        });
        thatButton.setX(300);
        thatButton.setY(200);
        stage.addActor(thatButton);

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("its a touch Down");
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("and that is a touch up");
            }
        });
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void show() { }

    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() {  }
}
