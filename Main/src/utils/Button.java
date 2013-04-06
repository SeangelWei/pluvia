package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button extends Actor {
    Texture texture;

    public Button(int x, int y, Texture texture) {
        super.setBounds(x, y, texture.getWidth(), texture.getHeight());
        this.texture = texture;
    }

    public void draw (SpriteBatch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
