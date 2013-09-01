package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import managers.GameManager;
import utils.*;

public class MenuScreen extends MyScreen {
    Button startButton;
    Button exitButton;
    Actor soundButton;
    boolean sound;

    public MenuScreen(final Pluvia pluvia) {
        super(pluvia);
        stage.setViewport(GameManager.VIRTUAL_WIDTH, GameManager.VIRTUAL_HEIGHT, false);
        startButton = new Button(70, 180, Assets.menu_play);
        exitButton = new Button(620, 280, Assets.menu_exit);
        soundButton = new Actor();
        soundButton.setBounds(470, 140, Assets.soundOff.getWidth(), Assets.soundOff.getHeight());
        sound = true;
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.click.play(GameManager.soundVolume);
                pluvia.getScreenManager().changeTo("LevelScreen");
            }
        });
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(sound) {
                    sound = false;
                    GameManager.musicVolume = 0;
                    GameManager.soundVolume = 0;
                    Assets.music.setVolume(GameManager.musicVolume);
                } else {
                    sound = true;
                    GameManager.musicVolume = GameManager.standardMusicVolume;
                    GameManager.soundVolume = GameManager.standardSoundVolume;
                    Assets.music.setVolume(GameManager.musicVolume);
                }
            }
        });
        stage.addActor(startButton);
        stage.addActor(exitButton);
        stage.addActor(soundButton);
        stage.addListener(new InputListener(){
            @Override
            public boolean keyTyped (InputEvent event, char character) {
                if(event.getKeyCode() == com.badlogic.gdx.Input.Keys.BACK) {
                    Gdx.app.exit();
                }
                return false;
            }
        });
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(Assets.menu_bg, 0, 0);
        if(sound) {
            batch.draw(Assets.soundOn, soundButton.getX(), soundButton.getY());
        } else {
            batch.draw(Assets.soundOff, soundButton.getX(), soundButton.getY());
        }
        batch.end();
        stage.draw();
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
    public void dispose() { }

    @Override
    public void init() {
        Gdx.input.setInputProcessor(stage);
    }
}
