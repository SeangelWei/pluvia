package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class Assets {
    public static Texture shot;
    public static Texture ball_smaller;
    public static Texture ball_small;
    public static Texture ball_middle;
    public static Texture ball_big;

    public static Texture powerUp_immortal;
    public static Texture powerUp_speed;
    public static Texture powerUp_time;
    // ---- GUI ----
    public static Texture gs_bar;
    public static Texture arrow_left;
    public static Texture arrow_right;
    public static Texture arrow_up;
    public static Texture menu_bg;
    public static Texture menu_play;
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
    public static Texture levelscreen_bg;
    public static Texture logo;
    // ---- Levels ----
    public static Texture levelIcon;
    public static List<Texture> levelBackgrounds = new ArrayList<Texture>();
    public static Animation walkLeftAnimation;
    public static Animation walkRightAnimation;
    public static Animation ballPoppingAnimation;
    public static TextureRegion playerIdleLeft;
    public static TextureRegion playerIdleRight;
    // ---- Sounds ----
    public static Sound powerup1;
    public static Sound powerup2;
    public static Sound powerup3;
    public static Sound explosion1;
    public static Sound explosion2;
    public static Sound explosion3;
    public static Sound click;
    // ---- Music ----
    public static Music music;

    public static void loadObjects() {
        powerUp_immortal = new Texture(Gdx.files.internal("objects/power_immortal.png"));
        powerUp_speed = new Texture(Gdx.files.internal("objects/power_speed.png"));
        powerUp_time = new Texture(Gdx.files.internal("objects/power_time.png"));
        shot = new Texture(Gdx.files.internal("objects/shot2.png"));
        ball_smaller = new Texture(Gdx.files.internal("objects/ball.png"));
        ball_small = new Texture(Gdx.files.internal("objects/ball.png"));
        ball_middle = new Texture(Gdx.files.internal("objects/ball.png"));
        ball_big = new Texture(Gdx.files.internal("objects/ball.png"));
        TextureRegion[] walkLeftFrames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            walkLeftFrames[i] = new TextureRegion(new Texture(Gdx.files.internal("animationExample/bob_0" + i + ".png")));
        }
        walkLeftAnimation = new Animation(0.06f, walkLeftFrames);

        TextureRegion[] walkRightFrames = new TextureRegion[5];

        for (int i = 0; i < 5; i++) {
            walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
            walkRightFrames[i].flip(true, false);
        }
        walkRightAnimation = new Animation(0.06f, walkRightFrames);
        playerIdleLeft = new TextureRegion(walkLeftFrames[0]);
        playerIdleRight = new TextureRegion(walkRightFrames[0]);

        TextureRegion[] ballPoppingFrames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            ballPoppingFrames[i] = new TextureRegion(new Texture(Gdx.files.internal("poppingAnimation/pop_0" + i + ".png")));
        }
        ballPoppingAnimation = new Animation(0.03f, ballPoppingFrames);
    }

    public static void loadGuiElements() {
        gs_bar = new Texture(Gdx.files.internal("gui/gs_bar.png"));
        arrow_left = new Texture(Gdx.files.internal("gui/arrow_left.png"));
        arrow_right = new Texture(Gdx.files.internal("gui/arrow_right.png"));
        arrow_up = new Texture(Gdx.files.internal("gui/arrow_up.png"));
        menu_bg = new Texture(Gdx.files.internal("gui/menu_bg1.png"));
        menu_play = new Texture(Gdx.files.internal("gui/play_button.png"));
        menu_exit = new Texture(Gdx.files.internal("gui/exit_button.png"));
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
        lifeFilled = new Texture(Gdx.files.internal("gui/life_full.png"));
        lifeEmpty = new Texture(Gdx.files.internal("gui/life_empty.png"));
        starEmpty = new Texture(Gdx.files.internal("gui/star_empty.png"));
        starFilled = new Texture(Gdx.files.internal("gui/star_full.png"));
        levelDisabled = new Texture(Gdx.files.internal("gui/levelDisabled.png"));
        levelscreen_bg = new Texture(Gdx.files.internal("gui/levelscreen_bg.png"));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level0.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level1.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level2.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level3.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level4.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level5.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level6.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level7.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level8.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level9.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level10.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level11.png")));
        levelBackgrounds.add(new Texture(Gdx.files.internal("levelBackgrounds/level12.png")));
        logo = new Texture(Gdx.files.internal("gui/logo.png"));
    }

    public static void loadSounds() {
        powerup1 = Gdx.audio.newSound(Gdx.files.internal("sounds/powerup/powerup1.wav"));
        powerup2 = Gdx.audio.newSound(Gdx.files.internal("sounds/powerup/powerup2.wav"));
        powerup3 = Gdx.audio.newSound(Gdx.files.internal("sounds/powerup/powerup3.wav"));
        explosion1 = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion/explosion1.wav"));
        explosion2 = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion/explosion2.wav"));
        explosion3 = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion/explosion3.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
    }
}