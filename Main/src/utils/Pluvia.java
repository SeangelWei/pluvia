package utils;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import view.Gamescreen;
import view.LevelScreen;
import view.Menuscreen;

public class Pluvia implements ApplicationListener {
    OrthographicCamera camera;
    SpriteBatch batch;
    ScreenManager screenManager;
    LevelManager levelManager;
    private final Input input = new Input();
    public static double delta;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        Gdx.input.setInputProcessor(input);
        Gdx.input.setCatchBackKey(true);
        Assets.loadObjects();
        Assets.loadGuiElements();
        levelManager = new LevelManager();
        levelManager.loadSettings();
        screenManager = new ScreenManager();
        screenManager.add("MenuScreen", new Menuscreen(batch, screenManager));
        screenManager.add("GameScreen", new Gamescreen(batch, screenManager, levelManager));
        screenManager.add("LevelScreen", new LevelScreen(batch, screenManager, levelManager));
        screenManager.changeTo("LevelScreen");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        delta = Gdx.graphics.getDeltaTime();
        screenManager.getCurrScreen().render(input);
        input.clear();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() { }
}
