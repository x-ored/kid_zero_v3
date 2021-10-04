package com.eternal.kidzero;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.eternal.kidzero.interfaces.functions.Functions;
import com.eternal.kidzero.models.AppModel;

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

                Drawable drawable = p.applicationInfo.loadIcon(App.getAppContext().getPackageManager());
                appModels.add(new AppModel(Name, packageName, drawable));
            }
        }

        return "";
    }

    protected void onProgressUpdate(Integer...a) {
        super.onProgressUpdate(a);
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        onPostExecute.apply(this);
    }
}