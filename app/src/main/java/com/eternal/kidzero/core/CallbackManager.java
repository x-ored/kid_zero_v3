package com.eternal.kidzero.core;

import android.util.Log;

import com.eternal.kidzero.interfaces.functions.FunctionsPmaxV;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CallbackManager {
    public static CallbackManager instance;


    HashMap<String, HashMap<String,FunctionsPmaxV<String,Object>>> callbaks;
    public CallbackManager(){

    }
    public static CallbackManager getInstance() {
        if (instance == null) {
            instance = new CallbackManager();
        }
        return instance;
    }

    public static void callCallbak(String name,Object... args) {
        if (CallbackManager.getInstance().callbaks == null) {
            CallbackManager.getInstance().callbaks = new HashMap<>();
        }
        if(CallbackManager.getInstance().callbaks.containsKey(name)){
            for (Map.Entry<String, FunctionsPmaxV<String,Object>> entry : Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).entrySet()) {
                try {
                    entry.getValue().apply(entry.getKey(),args);
                }catch (Exception e){
                    Log.d("CallbackManager", "CallbackManager callback error", e);
                    removeCallbak(name,entry.getKey());
                }
            }
        }
    }
    public static void addCallbak(String name,FunctionsPmaxV<String,Object> func) {
        String ident = new Throwable().getStackTrace()[1].getClassName();


        if (CallbackManager.getInstance().callbaks == null) {
            CallbackManager.getInstance().callbaks = new HashMap<>();
        }
        if(!CallbackManager.getInstance().callbaks.containsKey(name)) {
            CallbackManager.getInstance().callbaks.put(name, new HashMap<>());
        }
        if(CallbackManager.getInstance().callbaks.containsKey(name) && Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).containsKey(ident)) {
            removeCallbak(name,ident);
        }
        Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).put(ident,func);


    }

    public static void removeCallbak(String name) {
        String ident = new Throwable().getStackTrace()[1].getClassName();
        removeCallbak(name,ident);
    }
    public static void removeCallbak(String name,String ident) {
        if (CallbackManager.getInstance().callbaks == null) {
            CallbackManager.getInstance().callbaks = new HashMap<>();
        }
        if(CallbackManager.getInstance().callbaks.containsKey(name)) Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).remove(ident);


    }

}
