package com.eternal.kidzero.ui.helpers;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Network {

    public static final String TAG = "Network";
    public static JSONObject geolocationApiObj = null;

    public static void initGeolocationApi() {
        if (geolocationApiObj == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        geolocationApiObj = new JSONObject(downloadDataFromUrl("http://ip-api.com/json"));
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }
                }
            }).start();
        }
    }

    public static String getCountryCode() {
        try {
            return geolocationApiObj.getString("countryCode");
        }
        catch (Exception e) {
            return "US";
        }
    }

    public static String downloadDataFromUrl(String url) {
        try {
            URL oracle = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String Result = "";
            String inputLine;

            while ((inputLine = in.readLine()) != null)

                Result += inputLine + "\n";

            in.close();

            return Result;
        }
        catch (Exception e) {
            System.out.println("->>>>>>>>" + e.toString());
            return null;
        }
    }
}
