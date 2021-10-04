package com.eternal.kidzero.ui.fragments.child;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.AppsAsync;
import com.eternal.kidzero.R;
import com.eternal.kidzero.adapters.RcAppsAdapter;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class AppsListFrag extends BaseFrag {

    public static String TAG = "AppsListFrag";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_apps_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView appsRc = view.findViewById(R.id.appsRecyclerView);
        appsRc.setLayoutManager(new LinearLayoutManager(view.getContext()));

        RcAppsAdapter adapter = new RcAppsAdapter();
        appsRc.setAdapter(adapter);

        new AppsAsync().setOnPostExecute(appsAsync -> {
            adapter.setArray(appsAsync.appModels);
            appsRc.setVisibility(View.VISIBLE);
        }).execute();
    }
}