package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import utils.Assets;
import utils.Button;
import utils.MyScreen;
import utils.Pluvia;

public class Menuscreen extends MyScreen {
    Button startButton;
    Button exitButton;

    public Menuscreen(Pluvia pluvia) {
        super(pluvia);
        exitButton = new Button(70, 260, Assets.menu_exit);
        startButton = new Button(70, 330, Assets.menu_start);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenManager.changeTo("LevelScreen");
            }
        });
        stage.addActor(startButton);
        stage.addActor(exitButton);
        stage.addListener(new InputListener(){
            @Override
            public boolean keyDown (InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK) {
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
    public void init() { }
}
