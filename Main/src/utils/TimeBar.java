package utils;

public class TimeBar extends GameObject {
    int speed;
    public boolean finished;
    public float gold, silver;
    public float timeLeft_x;

    public TimeBar(float x, float y, int speed, float[] medals) {
        super(x, y);
        this.speed = speed;
        this.bounds.width = 480;
        timeLeft_x = 480;
        silver = medals[0];
        gold = medals[1];
    }

    @Override
    public void update() {
        if(timeLeft_x > -1) {
            timeLeft_x -= Game.delta * speed;
        } else {
            finished = true;
        }
    }
}
