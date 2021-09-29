package com.eternal.kidzero.ui.fragments.child;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.eternal.kidzero.ui.fragments.DebugPreviewFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChildMainFrag extends BaseFrag {

    public static final String TAG = "ChildMainFrag";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_child_main, container, false);
    }

    public BaseFrag currentFrag = null;
    public int currentFragId = 0;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentFragId = R.id.questItem;
        currentFrag = new QuestListFrag();
        childNavigate(currentFrag);

        BottomNavigationView nav = view.findViewById(R.id.childbuttomNavView);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (currentFragId != item.getItemId()) {
                    if (currentFrag != null) {
                        currentFrag.slideDown();
                    }
                    currentFrag = null;

                    switch (item.getItemId()) {
                        case R.id.questItem:
                            currentFrag = new QuestListFrag();
                            break;
                        case R.id.appLimitItem:
                            currentFrag = new DebugPreviewFrag();
                            break;
                        default:
                            break;
                    }

                    if (currentFrag != null) {
                        currentFrag.useAnim(R.anim.slide_up);
                        childNavigate(currentFrag);
                        item.setChecked(true);
                        currentFragId = item.getItemId();
                    }
                }
                return false;
            }
        });
    }
}