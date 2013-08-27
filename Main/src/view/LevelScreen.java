package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import utils.*;

import java.util.ArrayList;
import java.util.List;

public class LevelScreen extends MyScreen {
    List<LevelIcon> levelIcons = new ArrayList<LevelIcon>();
    Button backButton;
    BitmapFont font;

    public LevelScreen(Pluvia pluvia) {
        super(pluvia);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PipeDream.ttf"));
        font = generator.generateFont(20);
        generator.dispose();
    }

    @Override
    public void init(){
        Gdx.input.setInputProcessor(stage);
        levelIcons.clear();
        initialBlocks();
        synchronize();
        for (final LevelIcon levelIcon : levelIcons) {
            levelIcon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(levelIcon.isEnabled) {
                        Assets.click.play(Game.soundVolume);
                        pluvia.getLevelManager().loadLevel(levelIcon.level, levelIcon.fileName);
                        pluvia.getScreenManager().changeTo("GameScreen");
                    }
                }
            });
            stage.addActor(levelIcon);
            stage.setViewport(Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT, true);
        }
        backButton = new Button(60, 380, Assets.arrow_left);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.click.play(Game.soundVolume);
                pluvia.getScreenManager().changeTo("MenuScreen");
            }
        });
        stage.addActor(backButton);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyTyped (InputEvent event, char character) {
                if(event.getKeyCode() == com.badlogic.gdx.Input.Keys.BACK) {
                    Assets.click.play(Game.soundVolume);
                    pluvia.getScreenManager().changeTo("MenuScreen");
                }
                return false;
            }
        });
        levelIcons.get(0).isEnabled = true; // first level is always enabled
    }

    private void synchronize() {
        int reachedLevels = pluvia.getProgress().completedLevels.size();
        for (int i = 0; i < reachedLevels; i++) {
            levelIcons.get(i).isEnabled = true;
        }
        if (reachedLevels > 0) {
            levelIcons.get(reachedLevels).isEnabled = true;
        }
    }

    @Override
    public void render() {
        draw();
        stage.draw();
    }

    private void initialBlocks() {
        int marginTop = -200; //creepy...
        int marginLeft = 120;
        int anzahlProReihe = 5;
        int abstandX = 60;
        int abstandY = 35;
        int blockWidth = 65;
        int blocks = Gdx.files.internal("levels").list().length;

        int levelCounter = 0;
        for ( int col = 0;  col <= blocks/anzahlProReihe;  col++ ) {
            for ( int row = 0;  row < blocks-(col*anzahlProReihe);  row++ ) {
                int xa = (blockWidth+abstandX)*row;
                int ya = Game.VIRTUAL_HEIGHT-((blockWidth+abstandY)*col);
                if(row < anzahlProReihe){
                    levelIcons.add(new LevelIcon(xa + marginLeft, ya + marginTop, blockWidth, levelCounter));
                    levelCounter++;
                }
            }
        }
    }

    private void draw() {
        batch.begin();
        batch.draw(Assets.levelscreen_bg, 0, 0);
        for (LevelIcon levelIcon : levelIcons) {
            batch.draw(Assets.levelIcon, levelIcon.x, levelIcon.y);
            Integer level = levelIcon.level+1;
            if(level < 10) {
                font.draw(batch, level.toString(), levelIcon.x+(levelIcon.blockWidth/2-5), levelIcon.y+(levelIcon.blockWidth/2+5));
            } else {
                font.draw(batch, level.toString(), levelIcon.x+(levelIcon.blockWidth/2-10), levelIcon.y+(levelIcon.blockWidth/2+10));
            }
            if(levelIcon.isEnabled) {
                int reachedStars = pluvia.getProgress().getReachedStars(levelIcon.level);
                if (reachedStars != 0) {
                    for (int i = 0; i < reachedStars; i++) {
                        batch.draw(Assets.starFilled, (levelIcon.x+(levelIcon.blockWidth/2)-45)+i*30, levelIcon.y+(levelIcon.blockWidth/2)-50, 35, 35); // well, this is ugly
                    }
                }
            } else {
                batch.draw(Assets.levelDisabled, levelIcon.x, levelIcon.y);
            }
        }
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
