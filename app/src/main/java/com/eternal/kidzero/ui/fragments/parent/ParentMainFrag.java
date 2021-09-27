package com.eternal.kidzero.ui.fragments.parent;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ParentMainFrag extends BaseFrag {

    public static final String TAG = "ParentMainFrag";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_parent_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView nav = view.findViewById(R.id.buttomNavView);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.Connected:
                        fragment = new ChildListFrag();
                        break;
                    case R.id.findDeviceItem:
                        fragment = new AddChildFrag();
                        break;
                    default:
                        break;
                }

                if (fragment != null) {
                    parentNavigate(fragment);
                    item.setChecked(true);
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        parentNavigate(new ChildListFrag());
    }
}