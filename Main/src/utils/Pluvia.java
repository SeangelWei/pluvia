package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import managers.LevelManager;
import managers.ProgressManager;
import managers.ScreenManager;
import view.*;

public class Pluvia {
    SpriteBatch batch;
    ScreenManager screenManager;
    LevelManager levelManager;
    ProgressManager progressManager;

    public Pluvia(OrthographicCamera camera) {
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        Gdx.input.setCatchBackKey(true);
        Assets.loadObjects();
        Assets.loadGuiElements();
        Assets.loadSounds();
        Assets.loadEffects();
        System.out.println("Assets loaded");
        progressManager = new ProgressManager();
        progressManager.loadProgress();
        screenManager = new ScreenManager();
        screenManager.add("MenuScreen", new MenuScreen(this));
        screenManager.add("GameScreen", new GameScreen(this));
        screenManager.add("LevelScreen", new LevelScreen(this));
        screenManager.add("TestScreen", new TestScreen(this));
        screenManager.add("IntroScreen", new IntroScreen(this));
        screenManager.add("CreditScreen", new CreditScreen(this));
        screenManager.add("LevelScreen2", new LevelScreen2(this));
        screenManager.changeTo("MenuScreen");
        levelManager = new LevelManager(this);
    }

    public void render() {
        if (screenManager.getCurrScreen() != null) {
            screenManager.getCurrScreen().render();
        }
        screenManager.updateTransition();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ProgressManager getProgressManager() {
        return progressManager;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
