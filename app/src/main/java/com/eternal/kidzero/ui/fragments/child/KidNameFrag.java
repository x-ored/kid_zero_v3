package com.eternal.kidzero.ui.fragments.child;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.Helper;
import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class KidNameFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_kid_name, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String deviceName = Helper.getDeviceName();

        TextView deviceNameTextView = view.findViewById(R.id.deviceNameTextView);
        EditText customKidNameEditText = view.findViewById(R.id.customKidNameEditText);

        setSelectEditText(customKidNameEditText);

        deviceNameTextView.setText(getString(R.string.set_name_for_device) + " '" + deviceName + "'");

        view.findViewById(R.id.setCustomName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set name from 'customKidNameEditText' settings for this device
            }
        });

        view.findViewById(R.id.setDefaultName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set name from 'deviceName' settings for this device
            }
        });
    }
}