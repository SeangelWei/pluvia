package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    public static Texture playerLeft;
    public static Texture playerRight;
    public static Texture shot;
    public static Texture ball_smaller;
    public static Texture ball_small;
    public static Texture ball_middle;
    public static Texture ball_big;
    // ---- GUI ----
    public static Texture game_bg;
    public static Texture gs_bar;
    public static Texture moon;
    public static Texture arrow_left;
    public static Texture arrow_right;
    public static Texture arrow_up;
    public static Texture menu_bg;
    public static Texture menu_start;
    public static Texture menu_exit;
    public static Texture test;
    public static Texture pauseState;
    public static Texture winState;
    public static Texture gameoverState;
    public static Texture readyState;
    public static Texture readyBlink;
    public static Texture transparent;
    public static Texture resumeButton;
    public static Texture exitGameButton;
    public static Texture restartButton;
    public static Texture nextLevelButton;
    public static Texture lifeFilled;
    public static Texture lifeEmpty;
    public static Texture starEmpty;
    public static Texture starFilled;
    public static Texture levelDisabled;
    // ---- Levels ----
    public static Texture levelIcon;

    public static void loadObjects(){
        playerLeft = new Texture(Gdx.files.internal("objects/player_left.png"));
        playerRight = new Texture(Gdx.files.internal("objects/player_right.png"));
        shot = new Texture(Gdx.files.internal("objects/shot2.png"));
        ball_smaller = new Texture(Gdx.files.internal("objects/ball.png"));
        ball_small = new Texture(Gdx.files.internal("objects/ball.png"));
        ball_middle = new Texture(Gdx.files.internal("objects/ball.png"));
        ball_big = new Texture(Gdx.files.internal("objects/ball.png"));
    }

    public static void loadGuiElements(){
        gs_bar = new Texture(Gdx.files.internal("gui/gs_bar.png"));
        moon = new Texture(Gdx.files.internal("gui/moon.png"));
        arrow_left = new Texture(Gdx.files.internal("gui/arrow_left.png"));
        arrow_right = new Texture(Gdx.files.internal("gui/arrow_right.png"));
        arrow_up = new Texture(Gdx.files.internal("gui/arrow_up.png"));
        menu_bg = new Texture(Gdx.files.internal("gui/menu_bg.png"));
        menu_start = new Texture(Gdx.files.internal("gui/menu_start.png"));
        menu_exit = new Texture(Gdx.files.internal("gui/menu_exit.png"));
        test = new Texture(Gdx.files.internal("gui/test.png"));
        pauseState = new Texture(Gdx.files.internal("gui/pauseState.png"));
        winState = new Texture(Gdx.files.internal("gui/winState.png"));
        gameoverState = new Texture(Gdx.files.internal("gui/gameoverState.png"));
        transparent = new Texture(Gdx.files.internal("gui/transparent.png"));
        resumeButton = new Texture(Gdx.files.internal("gui/resumeButton.png"));
        restartButton = new Texture(Gdx.files.internal("gui/restartButton.png"));
        exitGameButton = new Texture(Gdx.files.internal("gui/exitGameButton.png"));
        nextLevelButton = new Texture(Gdx.files.internal("gui/nextLevelButton.png"));
        levelIcon = new Texture(Gdx.files.internal("gui/levelIcon.png"));
        readyState = new Texture(Gdx.files.internal("gui/readyState.png"));
        readyBlink = new Texture(Gdx.files.internal("gui/readyBlink.png"));
        game_bg = new Texture(Gdx.files.internal("gui/bg.jpg"));
        lifeFilled = new Texture(Gdx.files.internal("gui/life_full.png"));
        lifeEmpty = new Texture(Gdx.files.internal("gui/life_empty.png"));
        starEmpty = new Texture(Gdx.files.internal("gui/star_empty.png"));
        starFilled = new Texture(Gdx.files.internal("gui/star_full.png"));
        levelDisabled = new Texture(Gdx.files.internal("gui/levelDisabled.png"));
    }
}