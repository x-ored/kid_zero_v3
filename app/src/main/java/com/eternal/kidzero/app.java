package com.eternal.kidzero;

import android.app.Application;
import android.content.Context;

public class app extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        app.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return app.context;
    }
}