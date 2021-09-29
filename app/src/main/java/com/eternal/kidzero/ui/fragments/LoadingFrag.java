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

        if (FbCore.getInstance().get_user() == null) {
           // executeActionFrag(R.id.ActGoTo_AuthFrag);
            executeActionFrag(R.id.ActGoTo_ChildMainFrag);
        }
        else {
         //   executeActionFrag(R.id.ActGoTo_ParentMainFrag);

            executeActionFrag(R.id.ActGoTo_ParentMainFrag);
        }
    }
}