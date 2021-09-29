package com.eternal.kidzero;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
        FbCore.getInstance();
    }

    public static Context getAppContext() {
        return App.context;
    }
}