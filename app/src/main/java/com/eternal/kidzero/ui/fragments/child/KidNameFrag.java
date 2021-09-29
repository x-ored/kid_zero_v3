package com.eternal.kidzero.ui.fragments.child;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.Helper;
import com.eternal.kidzero.R;
import com.eternal.kidzero.core.CallbackManager;
import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.models.UserModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class KidNameFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_kid_name, container, false);
    }

    public void selectName(String newname){
        FDatabase.getInstance().getCurentUserData(new CallbackManager.Callback((callback, args) -> {
            if(args[0] instanceof UserModel){
                ((UserModel)args[0]).setName(newname).save();
            }
        }));

        new Handler().postDelayed(()->{
            executeActionFrag(R.id.LoadingFrag);
        }, 4000);

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
                selectName(customKidNameEditText.getText().toString());
            }
        });

        view.findViewById(R.id.setDefaultName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectName(deviceName);
            }
        });


        FDatabase.getInstance().getCurentUserData(new CallbackManager.Callback((callback, args) -> {
            if(args[0] instanceof UserModel) {
               ((EditText)getFragView().findViewById(R.id.customKidNameEditText)).setText(((UserModel) args[0]).getName().toString());
            }
        }));


    }




    @Override
    public void onStart() {
        super.onStart();

        addCallbak(UserModel.class.getName(),(callback, objects) -> {
            if(objects[0] instanceof UserModel) {
                ((EditText)getFragView().findViewById(R.id.customKidNameEditText)).setText(((UserModel) objects[0]).getName().toString());
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        CallbackManager.removeCallbacks();
    }


}