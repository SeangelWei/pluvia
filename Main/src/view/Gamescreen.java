package view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import controllers.GamescreenController;
import utils.*;


public class Gamescreen extends MyScreen {
    WorldRenderer worldRenderer;
    GamescreenController gsController;
    LevelManager levelManager;

    public Gamescreen(SpriteBatch batch, ScreenManager screenManager, LevelManager levelManager) {
        super(batch, screenManager);
        gsController = new GamescreenController(levelManager);
        worldRenderer = new WorldRenderer(batch, gsController);
        this.levelManager = levelManager;
    }

    public void render(Input input) {
        switch (gsController.getGameState()) {
            case paused:
                processPause(input);
                break;
            case playing:
                processPlaying(input);
                break;
            case win:
                processWin(input);
                break;
            case gameover:
                processGameover(input);
                break;
            case ready:
                processReady(input);
                break;
        }
        gsController.update();
        worldRenderer.render();
    }

    private void processReady(Input input) {
        if(input.TOUCH.x > 0){
            gsController.setGameState(GamescreenController.gameStateDef.playing);
        }
    }

    private void processGameover(Input input) {
        if(pointInRectangle(gsController.restart, input.TOUCH)){
            init();
        }
        if(pointInRectangle(gsController.exitGame, input.TOUCH)){
            screenManager.changeTo("MenuScreen");
        }
    }

    private void processWin(Input input) {
        if(pointInRectangle(gsController.nextLevel, input.TOUCH)){
            levelManager.loadNextLevel();
            gsController.setGameState(GamescreenController.gameStateDef.playing);
        }
        if(pointInRectangle(gsController.restart, input.TOUCH)){
            init();
        }
        if(pointInRectangle(gsController.exitGame, input.TOUCH)){
           screenManager.changeTo("LevelScreen");
        }
    }

    private void processPause(Input input) {
        if(input.ESCAPE){
            gsController.setGameState(GamescreenController.gameStateDef.playing);
        }
        if(pointInRectangle(gsController.resume, input.TOUCH)){
            gsController.setGameState(GamescreenController.gameStateDef.playing);
        }
        if(pointInRectangle(gsController.restart, input.TOUCH)){
            init();
        }
        if(pointInRectangle(gsController.exitGame, input.TOUCH)){
            screenManager.changeTo("LevelScreen");
        }
    }

    private void processPlaying(Input input) {
        if(input.LEFT){
            gsController.getPlayer().moveLeft();
        }
        if(input.RIGHT){
            gsController.getPlayer().moveRight();
        }
        if(input.SPACE){
            gsController.getPlayer().shot();
        }
        if(input.ESCAPE){
            gsController.setGameState(GamescreenController.gameStateDef.paused);
        }
        if(pointInRectangle(gsController.arrow_left, input.TOUCH)){
            gsController.getPlayer().moveLeft();
        }
        if(pointInRectangle(gsController.arrow_right, input.TOUCH)){
            gsController.getPlayer().moveRight();
        }
        if(pointInRectangle(gsController.arrow_up, input.TOUCH)){
            gsController.getPlayer().shot();
        }
    }

    public void init(){
        gsController.setGameState(GamescreenController.gameStateDef.ready);
        levelManager.reloadLevel();
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