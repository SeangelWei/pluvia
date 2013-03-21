package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;

public class Progress {
    int reachedLevels;
    HashMap<Integer, Integer> levelProgress = new HashMap<Integer, Integer>();
    String file = ".pluvia";

    public Progress () {
        loadProgress();
    }

    public void saveProgress() {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external(file).write(false)));
            //out.write(Boolean.toString(soundEnabled));
            for (int i = 0; i < 5; i++) {
                //out.write(Integer.toString(highscores[i]));
            }
        } catch (Throwable ignored) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    public void loadProgress() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(Gdx.files.external(file).read()));
            //soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                //highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (Throwable e) {
            // :( It's ok we have defaults
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    public void saveLevelProgress (int level, int reachedStars) {

    }
}
