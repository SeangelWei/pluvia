package utils;

public class XmlPair {
    private final Integer levelNumber;
    private final Integer reachedStars;

    public XmlPair(Integer levelNumber, Integer reachedStars)
    {
        this.levelNumber = levelNumber;
        this.reachedStars = reachedStars;
    }

    public Integer levelNumber()   { return levelNumber; }
    public Integer reachedStars() { return reachedStars; }
}
