package com.eternal.kidzero.ui.fragments.parent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eternal.kidzero.R;
import com.eternal.kidzero.models.AppModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.google.android.material.imageview.ShapeableImageView;

public class AppLimitFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_app_limit, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppModel appModel = (AppModel)getArguments().getSerializable("appModel");

        appBarInit("App limit", true);

        TextView cost_TextView = view.findViewById(R.id.cost_TextView);
        TextView appName = view.findViewById(R.id.appName);
        ShapeableImageView appImage = view.findViewById(R.id.appImage);

        if (appModel != null) {
            cost_TextView.setText(String.valueOf(appModel.cost));
            appName.setText(appModel.name);
            appImage.setImageDrawable(appModel.drawable);
        }
    }
}