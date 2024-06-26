package com.eternal.kidzero.ui.fragments.parent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eternal.kidzero.R;
import com.eternal.kidzero.adapters.RcChildAdapter;
import com.eternal.kidzero.models.AppModel;
import com.eternal.kidzero.models.ChildModel;
import com.eternal.kidzero.models.UserModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class ChildProfileFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_child_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appBarInit("Child profile", true);

        UserModel userModel = (UserModel)getArguments().getSerializable("userModel");

        TextView stars_TextView = view.findViewById(R.id.stars_TextView);
        TextView bedTime_TextView = view.findViewById(R.id.bedTime_TextView);
        TextView childName_TextView = view.findViewById(R.id.childName_TextView);

        childName_TextView.setText(userModel.getName());

        view.findViewById(R.id.childQuests).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeActionFrag(R.id.ActGoTo_ParentQuestListFrag);
            }
        });

        view.findViewById(R.id.appControl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeActionFrag(R.id.ActGoTo_ParentAppsListFrag);
            }
        });
    }
}