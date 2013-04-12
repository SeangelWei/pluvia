package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import view.Gamescreen;
import view.LevelScreen;
import view.Menuscreen;
import view.Testscreen;

public class Pluvia {
    SpriteBatch batch;
    ScreenManager screenManager;
    LevelManager levelManager;
    Progress progress;
    FPSLogger fpsLogger;

    public Pluvia(OrthographicCamera camera) {
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        Gdx.input.setCatchBackKey(true);
        Assets.loadObjects();
        Assets.loadGuiElements();
        System.out.println("Assets loaded");
        levelManager = new LevelManager();
        progress = new Progress();
        progress.loadProgress();
        screenManager = new ScreenManager();
        screenManager.add("MenuScreen", new Menuscreen(this));
        screenManager.add("GameScreen", new Gamescreen(this));
        screenManager.add("LevelScreen", new LevelScreen(this));
        screenManager.add("TestScreen", new Testscreen(this));
        screenManager.changeTo("TestScreen");
    }

    public void render() {
        screenManager.getCurrScreen().render();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public Progress getProgress() {
        return progress;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
