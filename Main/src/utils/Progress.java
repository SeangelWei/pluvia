package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class Progress {
    ArrayList<XmlPair> completedLevels = new ArrayList<XmlPair>();
    XmlReader reader;
    XmlReader.Element file;
    StringWriter stringWriter;
    XmlWriter writer;
    String filename = ".pluvia.xml";
    FileHandle fileHandler;

    public Progress () {
        fileHandler = Gdx.files.external(filename);
        stringWriter = new StringWriter();
        writer = new XmlWriter(stringWriter);
        reader = new XmlReader();
        loadProgress();
    }

    public void saveProgress() {
        if(fileHandler.exists()) {
            for (XmlPair completedLevel : completedLevels) {
                try {
                    writer.element("completedLevel").attribute("levelNumber", completedLevel.levelNumber())
                            .attribute("reachedStars", completedLevel.reachedStars()).pop();
                } catch (IOException e) {
                    System.out.println("could not save Progress");
                    e.printStackTrace();
                }
            }
        } else {
            try {
                fileHandler.file().createNewFile();
                for (XmlPair completedLevel : completedLevels) {
                    try {
                        writer.element("completedLevel").attribute("levelNumber", completedLevel.levelNumber())
                                .attribute("reachedStars", completedLevel.reachedStars()).pop();
                    } catch (IOException e) {
                        System.out.println("could not save Progress");
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.out.println("error trying to create File");
                e.printStackTrace();
            }
        }
    }

    public void loadProgress() {
        try {
            if(fileHandler.exists()) {
                file = reader.parse(Gdx.files.external(filename));
                for (int i = 0; i < file.getChildByName("completedLevels").getChildCount() ; i++) {
                    XmlReader.Element completedLevel = file.getChildByName("completedLevel").getChild(i);
                    int levelNumber = Integer.parseInt(completedLevel.getAttribute("levelNumber"));
                    int reachedStars = Integer.parseInt(completedLevel.getAttribute("reachedStars"));
                    completedLevels.add(new XmlPair(levelNumber, reachedStars));
                }
            }
        } catch (IOException e) {
            System.out.println("could not load Progress");
            e.printStackTrace();
        }
    }

    public void saveLevelProgress (int level, int reachedStars) {
        completedLevels.add(new XmlPair(level, reachedStars));
    }

    public int getReachedStars (int level) {
        if(completedLevels.isEmpty()) {
            return 0;
        } else {
            Integer reachedStars = completedLevels.get(level-1).reachedStars();
            if(reachedStars  == null) {
                return 0;
            } else {
                return reachedStars;
            }
        }
    }
}
