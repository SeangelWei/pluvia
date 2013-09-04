package view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import utils.MyScreen;
import utils.Pluvia;

public class LevelScreen2 extends MyScreen {
    public LevelScreen2(Pluvia pluvia) {
        super(pluvia);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("DOWN x: "+x+"y: "+y);
                return true;
            }
            @Override
            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                System.out.println("dragged");
            }
        });
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

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

    @Override
    public void init() {

    }
}
