package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import utils.Assets;
import managers.GameManager;
import utils.MyScreen;
import utils.Pluvia;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * This screen is just for testing
 */
public class TestScreen extends MyScreen {
    ShapeRenderer shapeRenderer;
    float opaque;
    BitmapFont font;
    public ParticleEffect walkParticle;

    public TestScreen(Pluvia pluvia) {
        super(pluvia);
        shapeRenderer = new ShapeRenderer();
        opaque = 1;
        walkParticle = new ParticleEffect();
        walkParticle.load(Gdx.files.internal("effects/smoke.p"),
                Gdx.files.internal("effects"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PipeDream.ttf"));
        font = generator.generateFont(30);
        generator.dispose();
    }

    @Override
    public void render() {
        batch.begin();
        walkParticle.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        walkParticle.draw(batch, GameManager.delta());
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, opaque);
        shapeRenderer.rect(0, 50, 50, 50);
        shapeRenderer.end();
        opaque-=0.01;
        stage.act(GameManager.delta());
        stage.draw();
    }

    @Override
    public void init() {
        walkParticle.getEmitters().get(0).duration = 10;
        stage.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                walkParticle.reset();
                return false;
            }
        });
        Drawable splashDrawable = new TextureRegionDrawable(new TextureRegion(Assets.arrow_up));
        Image star = new Image(splashDrawable, Scaling.none);
        star.setPosition(100, 100);
        star.setOrigin(40, 40);
        star.addAction(forever(sequence(rotateBy(360, 1),
                new Action() {
                    @Override
                    public boolean act(
                            float delta) {
                        return true;
                    }
                })));
        stage.addActor(star);
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
