package utils;

public class LevelIcon {
    public Integer level;
    public String fileName;
    public int x, y;
    public int blockWidth;

    public LevelIcon(int x, int y, int blockWidth, int levelCounter) {
        this.x = x;
        this.y = y;
        this.blockWidth = blockWidth;
        this.level = levelCounter;
        fileName = "level"+level+".xml";
    }
}
