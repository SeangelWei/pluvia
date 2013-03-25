package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import utils.Assets;
import utils.Input;
import utils.MyScreen;
import utils.Pluvia;
import utils.Progress;
import utils.ScreenManager;

public class Menuscreen extends MyScreen {
    Rectangle start;
    Rectangle exit;

    public Menuscreen(Pluvia pluvia) {
        super(pluvia.getBatch(), pluvia.getScreenManager(), pluvia.getProgress());
        start = new Rectangle(65, 350, 145, 50);
        exit = new Rectangle(65, 280, 145, 50);
    }

    @Override
    public void render(Input input) {
        processInput(input);
        draw();
    }

    private void processInput(Input input) {
        if(pointInRectangle(start, input.TOUCH)){
            screenManager.changeTo("LevelScreen");
        }
        if(pointInRectangle(exit, input.TOUCH)){
            Gdx.app.exit();
        }
        if(input.BACK){
            Gdx.app.exit();
        }
    }

    private void draw() {
        batch.begin();
        batch.draw(Assets.menu_bg, 0, 0);
        batch.draw(Assets.menu_start, start.x, start.y, start.getWidth(), start.getHeight());
        batch.draw(Assets.menu_exit, exit.x, exit.y, exit.getWidth(), exit.getHeight());
        batch.end();
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
