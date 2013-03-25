package ch.rainbowgames;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import utils.Game;
import utils.Pluvia;

public class PluviaStarter extends AndroidApplication
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useWakelock = true;
        cfg.useGL20 = true;
        initialize(new Game(), cfg);
    }
}