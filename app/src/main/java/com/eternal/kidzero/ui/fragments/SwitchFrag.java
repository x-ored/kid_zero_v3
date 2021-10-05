package com.eternal.kidzero.ui.fragments;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.MainActivity;
import com.eternal.kidzero.R;
import com.eternal.kidzero.SplashActivity;
import com.eternal.kidzero.core.CallbackManager;
import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.models.ChildModel;
import com.eternal.kidzero.models.ParentModel;
import com.eternal.kidzero.models.UserModel;

public class SwitchFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_switch, container, false);
    }



    public void loadOldRole(){
        executeActionFrag(R.id.ActGoTo_LoadingFrag);
    }
    public void createRole(Role role){
        if(role.equals(Role.Parent)){
            new ParentModel(FbCore.getInstance().get_user().getPhoneNumber(),Role.Parent,"Admin").save().addOnSuccessListener(aVoid -> {
                loadOldRole();
            }).addOnFailureListener(e -> {
                // show Error
            });;

        } else {
            new ChildModel(FbCore.getInstance().get_user().getPhoneNumber(),Role.Child,"User").save().addOnSuccessListener(aVoid -> {
                executeActionFrag(R.id.ActGoTo_InviteParentFrag);
            }).addOnFailureListener(e -> {
                // show Error
            });
        }

    }
    public void selectRole(Role selectRole){
        FDatabase.getInstance().getCurentUserData(new CallbackManager.Callback((callback, args) -> {
            if(args[0] instanceof UserModel){

               if(((UserModel)args[0]).getRole().equals(selectRole)){
                   loadOldRole();
               }else {
                   createRole(selectRole);
               }
            }else{
                createRole(selectRole);
            }
        }));
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.parentalDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRole(Role.Parent);
            }
        });

        view.findViewById(R.id.childDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRole(Role.Child);
            }
        });

        FDatabase.getInstance().getCurentUserData(new CallbackManager.Callback((callback, args) -> {
            if(args[0] instanceof UserModel) {
                ((TextView)getFragView().findViewById(R.id.deviceRole)).setText(((UserModel) args[0]).getName().toString()+" "+((UserModel) args[0]).getRole().toString());
            }
        }));
    }
    @Override
    public void onStart() {
        super.onStart();
        addCallbak(UserModel.class.getName(),(callback,objects) -> {
            if(objects[0] instanceof UserModel) {
                ((TextView)getFragView().findViewById(R.id.deviceRole)).setText(((UserModel) objects[0]).getName().toString()+" "+((UserModel) objects[0]).getRole().toString());
            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        CallbackManager.removeCallbacks();
    }
}