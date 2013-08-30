package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import utils.Assets;
import utils.Game;
import utils.GameObject;

public class Shot extends GameObject {
    int speed;
    public ParticleEffect particleEffect;

    public Shot(float x) {
        super(x, -280);
        speed = 320;
        bounds.setWidth(Assets.shot.getWidth());
        bounds.setHeight(380);
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("effects/shot.p"),
                Gdx.files.internal("effects"));
    }

    @Override
    public void update() {
        position.y += Game.delta()*speed;
        particleEffect.setPosition(position.x+Assets.shot.getWidth()/2, position.y+bounds.getHeight());
    }
}
