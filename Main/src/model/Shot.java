package model;

import utils.Assets;
import utils.GameObject;
import utils.Pluvia;

public class Shot extends GameObject {
    int speed;

    public Shot(float x) {
        super(x, -280);
        speed = 300;
        bounds.setWidth(Assets.shot.getWidth());
        bounds.setHeight(380);
    }

    @Override
    public void update() {
        position.y += Pluvia.delta*speed;
    }
}
