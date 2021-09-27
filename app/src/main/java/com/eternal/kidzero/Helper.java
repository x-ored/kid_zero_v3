package com.eternal.kidzero;

import android.os.Build;

import com.google.firebase.database.Exclude;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    @Exclude
    public static Map<String, Object> toMap(Object its) throws IllegalAccessException {
        HashMap<String, Object> result = new HashMap<>();
        Field[]fields=its.getClass().getDeclaredFields();
        for (Field x: fields) {
            x.setAccessible(true);
            Type genType = x.getGenericType();
            result.put(x.getName(),x.get(its));
        }
        return result;
    }
}
