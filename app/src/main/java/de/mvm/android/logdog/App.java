package de.mvm.android.logdog;

import android.app.Application;

public class App extends Application {

    private static App instance;

    /*
    * Singleton to get an instance of App
    * */
    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

}
