package com.eternal.kidzero.ui.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
            Log.d(TAG, e.toString());
            return null;
        }
    }

    public static String getPlayStoreIcon(String PackageName) {

        try {
            int size = 300;

            String url = "https://play.google.com/store/apps/details?id=" + PackageName;
            String downloaded = downloadDataFromUrl(url);

            if (downloaded.contains("T75of sHb2Xb")) {
                String[] Pat0 = downloaded.split("<div class=\"xSyT2c\"><img src=\"");
                String[] Pat1 = Pat0[1].split("=");
                return Pat1[0] + "=s" + size;
            }

            return null;
        }
        catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static File bitmapToFile(Bitmap bitmap, String path) { // File name like "image.png"

        File file = null;
        try {
            file = new File(path);
            file.createNewFile();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }
}
