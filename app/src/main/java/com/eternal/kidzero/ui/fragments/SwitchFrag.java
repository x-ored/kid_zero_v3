package com.eternal.kidzero.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.R;

public class SwitchFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_switch, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.parentalDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this device owner is parent
            }
        });

        view.findViewById(R.id.childDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this device owner is child
            }
        });
    }
}