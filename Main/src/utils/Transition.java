package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Transition {
    public float brightness = 1;
    public boolean isFadingOut = true;
    public boolean finished = false;
    public boolean canChange = false;
    public String nextScreen;
    ShapeRenderer shapeRenderer;

    public Transition() {
        this.shapeRenderer = new ShapeRenderer();
    }

    public void update() {
        // 0 ist gleich transparent
        if(isFadingOut){
            if(brightness < 1){
                brightness+=0.03;
            } else {
                canChange = true;
            }
        } else {
            if(brightness > 0){
                brightness-=0.03;
            } else {
                finished = true;
            }
        }
        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, brightness);
        shapeRenderer.rect(0, 0, Game.VIRTUAL_WIDTH, Game.VIRTUAL_HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL10.GL_BLEND);
    }
}
