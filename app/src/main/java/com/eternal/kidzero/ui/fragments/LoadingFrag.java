package com.eternal.kidzero.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.R;

public class LoadingFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (FbCore.getInstance().get_fAuth().getCurrentUser() == null) {
            executeActionFrag(R.id.ActGoTo_AuthFrag);
        }
        else {
            // Open main fragment
        }
    }
}