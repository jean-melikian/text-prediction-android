package fr.esgi.textprediction;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jean-Christophe Melikian on 07/06/2018.
 */
public class App extends Application {

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
