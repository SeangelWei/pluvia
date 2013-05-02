package view;

import com.badlogic.gdx.Gdx;
import controllers.GamescreenController;
import utils.Input;
import utils.MyScreen;
import utils.Pluvia;
import utils.WorldRenderer;

import static controllers.GamescreenController.gameStateDef.playing;


public class Gamescreen extends MyScreen {
    WorldRenderer worldRenderer;
    GamescreenController gsController;
    public final Input input;

    public Gamescreen(Pluvia pluvia) {
        super(pluvia);
        input = new Input();
        Gdx.input.setInputProcessor(input);
        gsController = new GamescreenController(pluvia, this);
        worldRenderer = new WorldRenderer(batch, gsController);
    }

    public void render() {
        gsController.update();
        worldRenderer.render();
        if(input.LEFT) {
            gsController.getPlayer().moveLeft();
        }
        if(input.RIGHT) {
            gsController.getPlayer().moveRight();
        }
        if(input.SPACE) {
            gsController.getPlayer().shot();
        }
        stage.draw();
        input.clear();
    }

    public void init(){
        gsController.setGameState(playing);
    }
    @Override
    public void resume() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void show() { }

    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void dispose() { }
}