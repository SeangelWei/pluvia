package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utils.MyScreen;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private MyScreen currScreen;
    private String screenName;
    private Map<String, MyScreen> screens = new HashMap<String, MyScreen>();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    float brightness = 1;
    float changeSpeed = 0.07f;
    boolean changeTransition = false;
    boolean fadeOutCompleted = false;
    boolean hasChanged = false;
    boolean finished = false;

    public void add(String screenName, MyScreen screenToAdd) {
        screens.put(screenName, screenToAdd);
    }

    public void changeTo(String screenName) {
        if (!fadeOutCompleted) {
            finished = false;
            this.screenName = screenName;
            changeTransition = true;
        } else {
            beginScreenChange(screenName);
            hasChanged = true;
            changeTransition = false;
        }
    }

    public void beginScreenChange(String screenName) {
        if (screens.get(screenName) != null && screens.containsKey(screenName)) {
            if (currScreen != null) {
                currScreen.hide();
            }
            setCurrScreen(screens.get(screenName));
            currScreen.init();
            Gdx.input.setInputProcessor(currScreen.stage);
        } else {
            System.err.println("Screen doesnt exist");
        }
    }

    public MyScreen getCurrScreen() {
        return currScreen;
    }

    public void setCurrScreen(MyScreen currScreen) {
        this.currScreen = currScreen;
    }

    public void updateTransition() {
        if (!finished) {
            if (changeTransition) {
                if (brightness < 1) {
                    brightness += changeSpeed;
                } else {
                    fadeOutCompleted = true;
                    changeTo(screenName);
                }
            } else if (hasChanged) {
                if (brightness > 0) {
                    brightness -= changeSpeed;
                } else {
                    finished = true;
                    changeTransition = false;
                    fadeOutCompleted = false;
                    hasChanged = false;
                }
            }

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, brightness);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
        }
    }
}
