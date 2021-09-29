package com.eternal.kidzero.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.R;
import com.eternal.kidzero.core.CallbackManager;
import com.eternal.kidzero.models.UserModel;

public class LoadingFrag extends BaseFrag {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FbCore.getInstance().getAuth().getCurrentUser() == null) {
            executeActionFrag(R.id.ActGoTo_AuthFrag);
            //executeActionFrag(R.id.ActGoTo_ChildMainFrag);
        } else {
            //   executeActionFrag(R.id.ActGoTo_ParentMainFrag);
            FDatabase.getInstance().getCurentUserData(new CallbackManager.Callback((callback, args) -> {
                if(args[0] instanceof UserModel){
                    UserModel userData = (UserModel)args[0];
                    switch (userData.getRole()){
                        case Parent:
                            executeActionFrag(R.id.ActGoTo_ParentMainFrag);
                            break;
                        case Child:
                            executeActionFrag(R.id.ActGoTo_ChildMainFrag);
                            break;
                        default:
                            executeActionFrag(R.id.ActGoTo_AuthFrag);
                    }
                }
            }));
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStop() {
        super.onStop();
        CallbackManager.removeCallbacks();
    }
}