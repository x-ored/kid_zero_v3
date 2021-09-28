package com.eternal.kidzero.ui.fragments.child;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.AddChildFrag;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.eternal.kidzero.ui.fragments.parent.ChildListFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChildMainFrag extends BaseFrag {

    public static final String TAG = "ChildMainFrag";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_parent_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}