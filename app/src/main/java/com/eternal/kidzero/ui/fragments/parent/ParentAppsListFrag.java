package com.eternal.kidzero.ui.fragments.parent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.AppsAsync;
import com.eternal.kidzero.R;
import com.eternal.kidzero.adapters.RcAppsAdapter;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class ParentAppsListFrag extends BaseFrag {

    public static String TAG = "AppsListFrag";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_parent_apps_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appBarInit("App control", true);

        RecyclerView appsRc = view.findViewById(R.id.appsRecyclerView);
        appsRc.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RcAppsAdapter adapter = new RcAppsAdapter(this, true);
        appsRc.setAdapter(adapter);

        new AppsAsync().setOnPostExecute(appsAsync -> {
            adapter.setArray(appsAsync.appModels);
        }).execute();
    }
}