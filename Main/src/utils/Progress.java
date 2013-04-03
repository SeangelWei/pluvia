package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class Progress {
    public ArrayList<XmlPair> completedLevels = new ArrayList<XmlPair>();
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
    }

    public void loadProgress() {
        try {
            if(fileHandler.exists()) {
                file = reader.parse(Gdx.files.external(filename));
                for (int i = 0; i < file.getChildCount();i++) {
                    XmlReader.Element completedLevel = file.getChild(i);
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

    public void saveProgress() {
        if(!fileHandler.exists()) {
            fileHandler.delete();
            writeDatas();
        } else {
            try {
                fileHandler.file().createNewFile();
                writeDatas();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeDatas() {
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("completedLevels");
            doc.appendChild(rootElement);
            for (XmlPair completedLevel : completedLevels) {
                Element completedLevelXml = doc.createElement("completedLevel");
                rootElement.appendChild(completedLevelXml);
                completedLevelXml.setAttribute("levelNumber", completedLevel.levelNumber().toString());
                completedLevelXml.setAttribute("reachedStars", completedLevel.reachedStars().toString());
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            String output = writer.toString();
            fileHandler.writeString(output, false);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
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
