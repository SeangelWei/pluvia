package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import managers.GameManager;
import utils.*;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.sin;

public class LevelScreen extends MyScreen {
    List<LevelIcon> levelIcons = new ArrayList<LevelIcon>();
    Button backButton;
    BitmapFont font;
    Vector2 backPosition;
    Vector2 backVelocity;
    float backSin = 0;

    public LevelScreen(final Pluvia pluvia) {
        super(pluvia);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PipeDream.ttf"));
        font = generator.generateFont(20);
        generator.dispose();
        stage.setViewport(GameManager.VIRTUAL_WIDTH, GameManager.VIRTUAL_HEIGHT, false);
        Gdx.input.setInputProcessor(stage);
        initialBlocks();
        synchronize();
        for (final LevelIcon levelIcon : levelIcons) {
            levelIcon.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (levelIcon.isEnabled) {
                        Assets.click.play(GameManager.soundVolume);
                        pluvia.getLevelManager().loadLevel(levelIcon.level, levelIcon.fileName);
                        pluvia.getScreenManager().changeTo("GameScreen");
                    }
                }
            });
            stage.addActor(levelIcon);
        }
        backPosition = new Vector2(60, 380);
        backVelocity = new Vector2();
        backButton = new Button(backPosition.x, backPosition.y, Assets.arrow_left);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.click.play(GameManager.soundVolume);
                pluvia.getScreenManager().changeTo("MenuScreen");
            }
        });
        stage.addActor(backButton);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (event.getKeyCode() == com.badlogic.gdx.Input.Keys.BACK) {
                    Assets.click.play(GameManager.soundVolume);
                    pluvia.getScreenManager().changeTo("MenuScreen");
                }
                return false;
            }
        });
        levelIcons.get(0).isEnabled = true; // first level is always enabled
    }

    @Override
    public void init() {

    }

    private void synchronize() {
        int reachedLevels = pluvia.getProgressManager().completedLevels.size();
        for (int i = 0; i < reachedLevels; i++) {
            levelIcons.get(i).isEnabled = true;
        }
        if (reachedLevels > 0) {
            levelIcons.get(reachedLevels).isEnabled = true;
        }
    }

    @Override
    public void render() {
        backSin -= 0.2;
        backVelocity.set(sin(backSin), 0);
        backVelocity.limit(0.4f);
        backPosition.add(backVelocity);
        backButton.setPosition(backPosition.x, backPosition.y);
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
        for (int col = 0; col <= blocks / anzahlProReihe; col++) {
            for (int row = 0; row < blocks - (col * anzahlProReihe); row++) {
                int xa = (blockWidth + abstandX) * row;
                int ya = GameManager.VIRTUAL_HEIGHT - ((blockWidth + abstandY) * col);
                if (row < anzahlProReihe) {
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
            Integer level = levelIcon.level + 1;
            if (level < 10) {
                font.draw(batch, level.toString(), levelIcon.x + (levelIcon.blockWidth / 2 - 5), levelIcon.y + (levelIcon.blockWidth / 2 + 5));
            } else {
                font.draw(batch, level.toString(), levelIcon.x + (levelIcon.blockWidth / 2 - 10), levelIcon.y + (levelIcon.blockWidth / 2 + 6));
            }
            if (levelIcon.isEnabled) {
                int reachedStars = pluvia.getProgressManager().getReachedStars(levelIcon.level);
                if (reachedStars != 0) {
                    for (int i = 0; i < reachedStars; i++) {
                        batch.draw(Assets.starFilled, levelIcon.x - 10 + (i * 30), levelIcon.y - 20, 25, 25);
                    }
                }
            } else {
                levelIcon.disabledSin -= 0.1;
                levelIcon.disabledVelocity.set(0, sin(levelIcon.disabledSin + 5));
                levelIcon.disabledVelocity.limit(0.12f);
                levelIcon.disabledPosition.add(levelIcon.disabledVelocity);
                levelIcon.setPosition(levelIcon.disabledPosition.x, levelIcon.disabledPosition.y);
                batch.draw(Assets.levelDisabled, levelIcon.disabledPosition.x, levelIcon.disabledPosition.y);
            }
        }
        batch.end();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }
}
