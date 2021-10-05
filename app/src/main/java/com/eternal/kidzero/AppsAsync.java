package com.eternal.kidzero;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.eternal.kidzero.interfaces.functions.Functions;
import com.eternal.kidzero.models.AppModel;
import com.eternal.kidzero.ui.helpers.Network;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppsAsync extends AsyncTask<Void, Integer, String> {

    Functions.Action<AppsAsync> onPostExecute;

    public ArrayList<AppModel> appModels = new ArrayList<>();

    public AppsAsync setOnPostExecute(Functions.Action<AppsAsync> onPostExecute) {
        this.onPostExecute = onPostExecute;
        return this;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(Void...arg0) {

        List<PackageInfo> apps = App.getAppContext().getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < apps.size(); i++) {
            PackageInfo p = apps.get(i);

            if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
                String Name = p.applicationInfo.loadLabel(App.getAppContext().getPackageManager()).toString();
                String packageName = p.applicationInfo.packageName;

                //Drawable x = getImageFromGooglePlay(packageName);
                Drawable x = p.applicationInfo.loadIcon(App.getAppContext().getPackageManager());
                appModels.add(new AppModel(Name, packageName, x));
            }
        }

        return "";
    }

    public Drawable getImageFromGooglePlay(String packageName) {
        String locStorage = App.getAppContext().getApplicationInfo().dataDir;
        String appIconPath = locStorage + "/" + packageName;

        if (!new File(appIconPath).exists()) {
            String imageUrl = Network.getPlayStoreIcon(packageName);
            if (imageUrl != null) {
                Bitmap bmp = Network.getBitmapFromURL(Network.getPlayStoreIcon(packageName));
                Network.bitmapToFile(bmp, appIconPath);

                return drawableFromFile(appIconPath);
            }
        }
        else {
            return drawableFromFile(appIconPath);
        }

        return null;
    }

    public Drawable drawableFromFile(String appIconPath) {
        System.out.println("Loading ->>>>>> " + appIconPath);
        return new BitmapDrawable(App.getAppContext().getResources(), BitmapFactory.decodeFile(appIconPath));
    }

    protected void onProgressUpdate(Integer...a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        onPostExecute.apply(this);
    }
}