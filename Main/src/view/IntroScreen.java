package view;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import managers.GameManager;
import utils.Assets;
import utils.MyScreen;
import utils.Pluvia;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class IntroScreen extends MyScreen {

    public IntroScreen(Pluvia pluvia) {
        super(pluvia);
    }

    @Override
    public void render() {
        stage.act(GameManager.delta());
        stage.draw();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void init() {
        Drawable splashDrawable = new TextureRegionDrawable(new TextureRegion(Assets.logo));
        Image logo = new Image(splashDrawable, Scaling.stretch);
        logo.setFillParent(true);

        // this is needed for the fade-in effect to work correctly; we're just
        // making the image completely transparent
        logo.getColor().a = 0f;

        // configure the fade-in/out effect on the splash image
        logo.addAction(sequence(fadeIn(0.75f), delay(1.75f), fadeOut(0.75f),
                new Action() {
                    @Override
                    public boolean act(
                            float delta) {
                        // the last action will move to the next screen
                        pluvia.getScreenManager().changeTo("MenuScreen");
                        return true;
                    }
                }));

        // and finally we add the actor to the stage
        stage.addActor(logo);
    }
}
