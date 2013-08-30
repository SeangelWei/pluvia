package managers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import utils.Assets;
import utils.Pluvia;

public class GameManager implements ApplicationListener {
    Pluvia pluvia;
    static OrthographicCamera camera;
    static float delta;
    public static final int VIRTUAL_WIDTH = 800;
    public static final int VIRTUAL_HEIGHT = 480;
    public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
    private Rectangle viewport;
    public static float scale;
    public static float standardMusicVolume = 0.3f;
    public static float standardSoundVolume = 0.3f;
    public static float musicVolume = standardMusicVolume;
    public static float soundVolume = standardSoundVolume;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        pluvia = new Pluvia(camera);
        Assets.music.setVolume(musicVolume);
        Assets.music.setLooping(true);
        Assets.music.play();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float)width/(float)height;
        Vector2 crop = new Vector2(0f, 0f);
        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }
        float w = (float)VIRTUAL_WIDTH*scale;
        float h = (float)VIRTUAL_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
    }

    @Override
    public void render() {
        camera.update();
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                (int) viewport.width, (int) viewport.height);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        delta = Gdx.graphics.getDeltaTime();
        pluvia.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public static float delta() {
        return delta;
    }

    public static OrthographicCamera getCamera() {
        return camera;
    }
}