package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import view.*;

public class Pluvia {
    SpriteBatch batch;
    ScreenManager screenManager;
    LevelManager levelManager;
    Progress progress;

    public Pluvia(OrthographicCamera camera) {
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        Gdx.input.setCatchBackKey(true);
        Assets.loadObjects();
        Assets.loadGuiElements();
        Assets.loadSounds();
        System.out.println("Assets loaded");
        progress = new Progress();
        progress.loadProgress();
        screenManager = new ScreenManager();
        screenManager.add("MenuScreen", new MenuScreen(this));
        screenManager.add("GameScreen", new GameScreen(this));
        screenManager.add("LevelScreen", new LevelScreen(this));
        screenManager.add("TestScreen", new TestScreen(this));
        screenManager.add("IntroScreen", new IntroScreen(this));
        screenManager.add("CreditScreen", new CreditScreen(this));
        screenManager.changeTo("LevelScreen");
        levelManager = new LevelManager(this);
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
