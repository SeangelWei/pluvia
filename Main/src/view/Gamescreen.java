package view;

import controllers.GamescreenController;
import utils.MyScreen;
import utils.Pluvia;
import utils.WorldRenderer;

import static controllers.GamescreenController.gameStateDef.playing;


public class Gamescreen extends MyScreen {
    WorldRenderer worldRenderer;
    GamescreenController gsController;
    public boolean LEFT;
    public boolean RIGHT;
    public boolean UP;

    public Gamescreen(Pluvia pluvia) {
        super(pluvia);
        gsController = new GamescreenController(pluvia, this);
        worldRenderer = new WorldRenderer(batch, gsController);
    }

    public void render() {
        stage.draw();
        gsController.update();
        worldRenderer.render();
        if(LEFT) {
            gsController.getPlayer().moveLeft();
        }
        if(RIGHT) {
            gsController.getPlayer().moveRight();
        }
        if(UP) {
            gsController.getPlayer().shot();
        }
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