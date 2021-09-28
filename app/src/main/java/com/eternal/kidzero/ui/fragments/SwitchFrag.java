package com.eternal.kidzero.ui.fragments;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;
import static com.eternal.kidzero.core.CallbackManager.removeCallbak;

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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


       addCallbak(UserModel.class.getName(),(ident,args) -> {

           if(args[0] instanceof UserModel) {
               ((TextView)view.findViewById(R.id.deviceRole)).setText(((UserModel) args[0]).getName().toString()+" "+((UserModel) args[0]).getRole().toString());

               new Handler().postDelayed(()->{
                   removeCallbak(UserModel.class.getName(),ident);
                   executeActionFrag(R.id.LoadingFrag);
               }, 3000);


           }else {
               ((TextView)view.findViewById(R.id.deviceRole)).setText("Whu is you?");
           }
       });

        view.findViewById(R.id.parentalDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ParentModel(FbCore.getInstance().get_user().getPhoneNumber(),Role.Parent,"Admin").AddConected("test").AddConected("test2").save();
                new ChildModel("test",Role.Child,"test").save();
                new ChildModel("test2",Role.Child,"test").save();



            }
        });

        view.findViewById(R.id.childDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this device owner is child
                executeActionFrag(R.id.ActGoTo_InviteParentFrag);
            }
        });
    }
}