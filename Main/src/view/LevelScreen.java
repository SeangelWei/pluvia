package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelScreen extends MyScreen {
    List<LevelIcon> levelIcons = new ArrayList<LevelIcon>();
    Rectangle backButton;
    LevelManager levelManager;
    BitmapFont font;
    Preferences gamePrefs;

    public LevelScreen(SpriteBatch spriteBatch, ScreenManager screenManager, LevelManager levelManager, Preferences gamePrefs) {
        super(spriteBatch, screenManager);
        this.levelManager = levelManager;
        font = new BitmapFont(Gdx.files.internal("gui/arial-15.fnt"),
                Gdx.files.internal("gui/arial-15.png"), false);
        this.gamePrefs = gamePrefs;
    }

    @Override
    public void init(){
        int marginTop = -180; //creepy...
        int marginLeft = 120;
        int anzahlProReihe = 5;
        int abstandX = 60;
        int abstandY = 40;
        int blockWidth = 65;
        int blocks = Gdx.files.internal("levels").list().length;

        int levelCounter = 1;
        for ( int col = 0;  col <= blocks/anzahlProReihe;  col++ ) {
            for ( int row = 0;  row < blocks-(col*anzahlProReihe);  row++ ) {
                int xa = (blockWidth+abstandX)*row;
                int ya = Gdx.graphics.getHeight()-((blockWidth+abstandY)*col);
                if(row < anzahlProReihe){
                    levelIcons.add(new LevelIcon(xa + marginLeft, ya + marginTop, blockWidth, levelCounter));
                    levelCounter++;
                }
            }
        }
        backButton = new Rectangle(700, 400, 80, 80);
    }

    @Override
    public void render(Input input) {
        processInput(input);
        draw();
    }

    private void processInput(Input input) {
        if(pointInRectangle(backButton, input.TOUCH)){
            screenManager.changeTo("MenuScreen");
        }
        if(input.BACK){
            screenManager.changeTo("MenuScreen");
        }
        for (LevelIcon levelIcon : levelIcons) {
            if(pointInRectangle(new Rectangle(levelIcon.x, levelIcon.y, levelIcon.blockWidth, levelIcon.blockWidth), input.TOUCH) &&
                    levelIcon.isEnabled){
                levelManager.loadLevel(levelIcon.level, levelIcon.fileName);
                screenManager.changeTo("GameScreen");
            }
        }
    }

    private void draw() {
        batch.begin();
        for (LevelIcon levelIcon : levelIcons) {
            batch.draw(Assets.levelIcon, levelIcon.x, levelIcon.y);
            font.draw(batch, levelIcon.level.toString(), levelIcon.x+(levelIcon.blockWidth/2), levelIcon.y+(levelIcon.blockWidth/2));
            if(levelIcon.isEnabled) {
                for (int i = 0; i < 3; i++) {
                    batch.draw(Assets.starFilled, (levelIcon.x+(levelIcon.blockWidth/2)-45)+i*30, levelIcon.y+(levelIcon.blockWidth/2)-50, 35, 35); // well, this is ugly
                }
            }
        }
        batch.draw(Assets.arrow_left, backButton.x, backButton.y);
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
}
