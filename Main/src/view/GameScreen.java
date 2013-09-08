package view;

import controllers.GamescreenController;
import managers.GameManager;
import managers.InputManager;
import models.Player;
import utils.MyScreen;
import utils.Pluvia;
import utils.WorldRenderer;

import static controllers.GamescreenController.gameStateDef.paused;
import static controllers.GamescreenController.gameStateDef.playing;


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
        if (inputManager.LEFT) {
            gsController.getPlayer().moveLeft();
        } else {
            if (!inputManager.RIGHT) {
                gsController.getPlayer().state = Player.State.IDLE;
            }
        }
        if (inputManager.RIGHT) {
            gsController.getPlayer().moveRight();
        } else {
            if (!inputManager.LEFT) {
                gsController.getPlayer().state = Player.State.IDLE;
            }
        }
        if (inputManager.SPACE) {
            gsController.getPlayer().shot();
        }
        if (gsController.getGameState() != playing) {
            stage.act(GameManager.delta());
            stage.draw();
        }
        inputManager.clear();
    }

    public void init() {
        gsController.setGameState(playing);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        gsController.setGameState(paused);
    }
}