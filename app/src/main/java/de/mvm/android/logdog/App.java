package de.mvm.android.logdog;

import android.app.Application;

public class App extends Application {

    private static App instance = null;

    private App() {
        super();
    }

    /*
    * Singleton to get an instance of App
    * */
    public static App getInstance() {
        if(instance == null) {
            instance = new App();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
