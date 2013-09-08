package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import managers.GameManager;
import utils.Assets;
import utils.Button;
import utils.MyScreen;
import utils.Pluvia;

import static com.badlogic.gdx.math.MathUtils.sin;

public class MenuScreen extends MyScreen {
    Button startButton;
    Button exitButton;
    Actor soundButton;
    boolean sound;
    Vector2 startPosition;
    Vector2 startVelocity;
    float startSin = 0;
    Vector2 exitPosition;
    Vector2 exitVelocity;
    float exitSin = 0;

    public MenuScreen(final Pluvia pluvia) {
        super(pluvia);
        stage.setViewport(GameManager.VIRTUAL_WIDTH, GameManager.VIRTUAL_HEIGHT, false);
        startPosition = new Vector2(70, 180);
        exitPosition = new Vector2(620, 280);
        startVelocity = new Vector2();
        exitVelocity = new Vector2();
        startButton = new Button(startPosition.x, startPosition.y, Assets.menu_play);
        exitButton = new Button(exitPosition.x, exitPosition.y, Assets.menu_exit);
        soundButton = new Actor();
        soundButton.setBounds(20, 20, Assets.soundOff.getWidth(), Assets.soundOff.getHeight());
        sound = GameManager.prefs.getBoolean("sound");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
                if (sound) {
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
        stage.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (event.getKeyCode() == com.badlogic.gdx.Input.Keys.BACK) {
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
        if (sound) {
            GameManager.musicVolume = GameManager.standardMusicVolume;
            GameManager.soundVolume = GameManager.standardSoundVolume;
            Assets.music.setVolume(GameManager.musicVolume);
            batch.draw(Assets.soundOn, soundButton.getX(), soundButton.getY());
        } else {
            GameManager.musicVolume = 0;
            GameManager.soundVolume = 0;
            Assets.music.setVolume(GameManager.musicVolume);
            batch.draw(Assets.soundOff, soundButton.getX(), soundButton.getY());
        }
        GameManager.prefs.putBoolean("sound", sound);
        batch.end();
        startSin -= 0.06;
        exitSin += 0.06;
        exitVelocity.set(sin(exitSin), sin(exitSin + 5));
        startVelocity.set(sin(startSin), sin(startSin + 5));
        exitVelocity.limit(0.4f);
        startVelocity.limit(0.6f);
        exitPosition.add(exitVelocity);
        startPosition.add(startVelocity);
        exitButton.setPosition(exitPosition.x, exitPosition.y);
        startButton.setPosition(startPosition.x, startPosition.y);
        stage.draw();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void init() {
        Gdx.input.setInputProcessor(stage);
    }
}
