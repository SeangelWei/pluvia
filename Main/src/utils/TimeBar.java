package utils;

public class TimeBar extends GameObject {
    int speed;
    public boolean finished;

    public TimeBar(float x, float y, int speed) {
        super(x, y);
        this.speed = speed;
        this.bounds.width = 480;
    }

    @Override
    public void update() {
        if(bounds.width > -1) {
            bounds.width -= Pluvia.delta*speed;
        } else {
            finished = true;
        }
    }
}
