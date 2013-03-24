package utils;

public class XmlPair {
    private final Integer levelNumber;
    private final Integer reachedStars;

    public XmlPair(Integer aKey, Integer aValue)
    {
        levelNumber = aKey;
        reachedStars = aValue;
    }

    public Integer levelNumber()   { return levelNumber; }
    public Integer reachedStars() { return reachedStars; }
}
