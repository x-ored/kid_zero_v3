package com.eternal.kidzero.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CallbackManager {
    public static CallbackManager instance;

    HashMap<String, HashMap<String, Callback>> callbaks;

    public static interface Calbackfs<Callback,T> {
        void apply(CallbackManager.Callback callback, T... args);
    }
    public static class Callback {
        String name;
        String classname;
        Calbackfs<Callback,Object> func;
        public Callback(Calbackfs<Callback,Object> func){
            StackTraceElement[]  classnamelist = new Throwable().getStackTrace();
            String classname = new Throwable().getStackTrace()[1].getClassName();
            this.setClassname(classname).setFunc(func);
        }
        public Callback(String name,String classname,Calbackfs<Callback,Object> func){
            this.setName(name).setClassname(classname).setFunc(func);
        }

        public Callback setClassname(String classname) {
            this.classname = classname;
            return this;
        }

        public Callback setName(String name) {
            this.name = name;
            return this;
        }

        public Callback setFunc(Calbackfs<Callback, Object> func) {
            this.func = func;
            return this;
        }


        public void apply(Object... args){
            try {
                if(args.length > 0) {
                    func.apply(this,args);
                }else{
                    func.apply(this);
                }

            }catch (Exception e){
                Log.d("CallbackManager", "CallbackManager callback error", e);
                dispose();
            }
        }
        public void dispose(){
            if(name != null && classname !=null) {
                removeCallbak(name, classname);
            }
        }

        public void close(){
            dispose();
        }
        public Callback removeif(String classname){
            if (this.classname.equals(classname) ) {
                dispose();
            }

            return this;
        }
    }

    public CallbackManager(){}
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
            for (Callback entry : Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).values()) {
                entry.apply(args);
            }
        }
    }
    public static void addCallbak(String name, Calbackfs<Callback,Object> func) {
        StackTraceElement[]  classnamelist = new Throwable().getStackTrace();
        String KEY = new Throwable().getStackTrace()[1].getClassName();

        if (CallbackManager.getInstance().callbaks == null) {
            CallbackManager.getInstance().callbaks = new HashMap<>();
        }
        if(!CallbackManager.getInstance().callbaks.containsKey(name)) {
            CallbackManager.getInstance().callbaks.put(name, new HashMap<>());
        }
        if(CallbackManager.getInstance().callbaks.containsKey(name) && Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).containsKey(KEY)) {
            removeCallbak(name,KEY);
        }
        Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).put(KEY,new Callback(name,KEY,func));
    }
    public static void addCallbak(String name, Callback func) {
        String KEY = func.classname;
        if (CallbackManager.getInstance().callbaks == null) {
            CallbackManager.getInstance().callbaks = new HashMap<>();
        }
        if(!CallbackManager.getInstance().callbaks.containsKey(name)) {
            CallbackManager.getInstance().callbaks.put(name, new HashMap<>());
        }
        if(CallbackManager.getInstance().callbaks.containsKey(name) && Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).containsKey(KEY)) {
            removeCallbak(name,KEY);
        }
        Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).put(KEY,func.setName(name));
    }
    public static void removeCallbacks()
    {
        String classname = new Throwable().getStackTrace()[1].getClassName();
        if (CallbackManager.getInstance().callbaks == null) {
            CallbackManager.getInstance().callbaks = new HashMap<>();
        }
        for (Map.Entry<String, HashMap<String, Callback>> entry : Objects.requireNonNull(CallbackManager.getInstance().callbaks).entrySet()) {
            for (Callback entry2 : new ArrayList<>(Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(entry.getKey())).values())) {
             entry2.removeif(classname);
            }
        }
    }
    public static void removeCallbak(String name)
    {
        String ident = new Throwable().getStackTrace()[1].getClassName();
       if(Objects.requireNonNull(CallbackManager.getInstance().callbaks.containsKey(name))) {
           for (Callback entry2 : Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).values()) {
               entry2.removeif(ident);
           }
       }
    }
    public static void removeCallbak(String name,String KEY)
    {
        if (CallbackManager.getInstance().callbaks == null) {
            CallbackManager.getInstance().callbaks = new HashMap<>();
        }
        if(CallbackManager.getInstance().callbaks.containsKey(name)) Objects.requireNonNull(CallbackManager.getInstance().callbaks.get(name)).remove(KEY);
    }

}
