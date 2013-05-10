package utils;

public class TimeBar extends GameObject {
    int speed;
    public boolean finished;
    public float gold, silver, bronze;
    public float timeLeft_x;

    public TimeBar(float x, float y, int speed, float[] medals) {
        super(x, y);
        this.speed = speed;
        this.bounds.width = 480;
        timeLeft_x = 480;
        bronze = medals[0];
        silver = medals[1];
        gold = medals[2];
    }

    @Override
    public void update() {
        if(timeLeft_x > -1) {
            timeLeft_x -= Game.delta*speed;
        } else {
            finished = true;
        }
    }
}
