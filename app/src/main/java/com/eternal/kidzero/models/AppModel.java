package com.eternal.kidzero.models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class AppModel implements Serializable {

    public String name;
    public String packageName;
    public Drawable drawable;

    public int cost = 0;
    public boolean isBlocked = false;
    public long timeToBlock = 0;

    public AppModel(String name, String packageName, Drawable drawable) {
        this.name = name;
        this.drawable = drawable;
        this.packageName = packageName;
    }

    public AppModel() { }
}
