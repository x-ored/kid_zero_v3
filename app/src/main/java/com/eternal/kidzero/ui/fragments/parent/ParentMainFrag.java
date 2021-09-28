package com.eternal.kidzero.ui.fragments.parent;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.AddChildFrag;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ParentMainFrag extends BaseFrag {

    public static final String TAG = "ParentMainFrag";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_parent_main, container, false);
    }

    public BaseFrag currentFrag = null;
    public int currentFragId = 0;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentFrag = new ChildListFrag();
        parentNavigate(currentFrag);

        BottomNavigationView nav = view.findViewById(R.id.buttomNavView);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (currentFragId != item.getItemId()) {
                    if (currentFrag != null) {
                        currentFrag.slideDown();
                    }
                    currentFrag = null;

                    switch (item.getItemId()) {
                        case R.id.Connected:
                            currentFrag = new ChildListFrag();
                            break;
                        case R.id.findDeviceItem:
                            currentFrag = new AddChildFrag();
                            break;
                        default:
                            break;
                    }

                    if (currentFrag != null) {
                        currentFrag.useAnim(R.anim.slide_up);
                        parentNavigate(currentFrag);
                        item.setChecked(true);
                        currentFragId = item.getItemId();
                    }
                }
                return false;
            }
        });
    }
}