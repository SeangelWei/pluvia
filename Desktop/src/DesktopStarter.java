import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import managers.GameManager;

public class DesktopStarter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Pluvia";
        cfg.useGL20 = true;
        //Galaxy Note (800x1280)
//        cfg.height = 800;
//        cfg.width = 1280;
        //Galaxy Note II (720x1280)
        cfg.height = 720;
        cfg.width = 1280;
        //Galaxy S I, Galaxy S II, Galaxy SIII (480x800)
//        cfg.height = 480;
//        cfg.width = 800;
        new LwjglApplication(new GameManager(), cfg);
    }
}