package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Transition {
    public float brightness = 0;
    public boolean isFadingOut = true;
    public boolean finished = false;
    public boolean canChange = false;
    public String nextScreen;
    ShapeRenderer shapeRenderer;

    public Transition(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public void update() { // not correct
        if(isFadingOut){
            if(brightness < 255){
                brightness+=30;
            } else {
                canChange = true;
            }
        } else {
            if(brightness > 0){
                brightness-=30;
            } else {
                finished = true;
            }
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
        shapeRenderer.setColor(0, 0, 0, brightness);
        shapeRenderer.filledRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
    }
}
