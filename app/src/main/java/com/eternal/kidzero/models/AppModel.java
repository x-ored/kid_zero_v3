package com.eternal.kidzero.models;

import android.graphics.drawable.Drawable;

public class AppModel {

    public String name;
    public String packageName;
    public Drawable drawable;

    public AppModel(String name, String packageName, Drawable drawable) {
        this.name = name;
        this.drawable = drawable;
        this.packageName = packageName;
    }
}
