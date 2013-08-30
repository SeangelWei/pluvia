package view;

import controllers.GamescreenController;
import managers.InputManager;
import models.Player;
import utils.*;

import static controllers.GamescreenController.gameStateDef.playing;
import static controllers.GamescreenController.gameStateDef.win;


public class GameScreen extends MyScreen {
    WorldRenderer worldRenderer;
    GamescreenController gsController;
    public final InputManager inputManager;

    public GameScreen(Pluvia pluvia) {
        super(pluvia);
        gsController = new GamescreenController(pluvia, this);
        worldRenderer = new WorldRenderer(batch, gsController);
        inputManager = new InputManager();
    }

    public void render() {
        worldRenderer.render();
        gsController.update();
        if(inputManager.LEFT) {
            gsController.getPlayer().moveLeft();
        } else {
            if(!inputManager.RIGHT) {
                gsController.getPlayer().state = Player.State.IDLE;
            }
        }
        if(inputManager.RIGHT) {
            gsController.getPlayer().moveRight();
        } else {
            if(!inputManager.LEFT) {
                gsController.getPlayer().state = Player.State.IDLE;
            }
        }
        if(inputManager.SPACE) {
            gsController.getPlayer().shot();
        }
        if(gsController.getGameState() != playing) {
            stage.draw();
        }
        inputManager.clear();
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
    public void hide() {
        pluvia.getProgressManager().saveProgress();
    }

    @Override
    public void pause() {
        pluvia.getProgressManager().saveProgress();
    }

    @Override
    public void dispose() {
        pluvia.getProgressManager().saveProgress();
    }
}