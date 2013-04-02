package utils;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private MyScreen currScreen;
    private Map<String, MyScreen> screens = new HashMap<String, MyScreen>();

    public void add(String screenName, MyScreen screenToAdd){
        screens.put(screenName, screenToAdd);
    }

    public void changeTo(String screenName){
        if(screens.get(screenName) != null && screens.containsKey(screenName)){
            setCurrScreen(screens.get(screenName));
            currScreen.init();
        } else {
            System.err.println("Screen doesnt exist");
        }
    }

    public MyScreen getCurrScreen() {
        return currScreen;
    }

    public void setCurrScreen(MyScreen currScreen) {
        this.currScreen = currScreen;
    }
}
