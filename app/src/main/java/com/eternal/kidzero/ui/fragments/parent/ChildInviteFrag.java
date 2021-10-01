package com.eternal.kidzero.ui.fragments.parent;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.R;
import com.eternal.kidzero.adapters.RcChildAdapter;
import com.eternal.kidzero.core.CallbackManager;
import com.eternal.kidzero.models.ChildModelInvite;
import com.eternal.kidzero.models.UserModel;
import com.eternal.kidzero.ui.fragments.BaseFrag;
import com.google.firebase.auth.PhoneAuthCredential;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ChildInviteFrag extends BaseFrag {
    RcChildAdapter adapter;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_child_invite, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView childRc = view.findViewById(R.id.childRecycleView);
        childRc.setLayoutManager(new LinearLayoutManager(view.getContext()));
        childRc.setVisibility(View.VISIBLE);
        adapter = new RcChildAdapter(this, true){
            @Override
            public void updateItems() {
                FDatabase.getParentChildManager().getChildsInvites(new CallbackManager.Callback((callback, args) -> {
                    if(args[0] instanceof HashMap) {
                        updateItems(((HashMap<String, UserModel>) args[0]).values());
                    }
                }));
            }
        };
        adapter.rcIsEmpty_TextView = view.findViewById(R.id.isEmpty);
        childRc.setAdapter(adapter);

        adapter.updateItems();




    }

    @Override
    public void onStart() {
        super.onStart();
        addCallbak("childsInvites",(callback, args) -> {
            if(args[0] instanceof HashMap) {
                adapter.updateItems(((HashMap<String, UserModel>) args[0]).values());
            }else {
                adapter.updateItems(new ArrayList<>());
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        CallbackManager.removeCallbacks();
    }
}