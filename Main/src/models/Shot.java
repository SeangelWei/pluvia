package models;

import com.badlogic.gdx.Gdx;
import managers.GameManager;
import utils.Assets;
import utils.GameObject;

public class Shot extends GameObject {
    int speed;

    public Shot(float x) {
        super(x, -280);
        speed = 350;
        bounds.setWidth(Assets.shot.getWidth());
        bounds.setHeight(380);
        Assets.shotEffect.load(Gdx.files.internal("effects/shot.p"),
                Gdx.files.internal("effects"));
    }

    @Override
    public void update() {
        position.y += GameManager.delta() * speed;
        Assets.shotEffect.setPosition(position.x + Assets.shot.getWidth() / 2, position.y + bounds.getHeight());
    }
}
