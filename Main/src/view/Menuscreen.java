package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import utils.*;

public class Menuscreen extends MyScreen {
    Button startButton;
    Button exitButton;

    public Menuscreen(Pluvia pluvia) {
        super(pluvia);
        Gdx.input.setInputProcessor(stage);
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
    }

    @Override
    public void render(Input input) {
        if(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.BACK)) {
            Gdx.app.exit();
        }
        draw();
    }

    private void draw() {
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
