package com.eternal.kidzero;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;
import static com.eternal.kidzero.core.CallbackManager.callCallbak;

import android.widget.TextView;

import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.interfaces.functions.FunctionsP3V;
import com.eternal.kidzero.models.ChildModel;
import com.eternal.kidzero.models.ParentModel;
import com.eternal.kidzero.models.UserModel;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ParentChilds {
    public String TAG = "ParentChilds";
    HashMap<String, UserModel> childs;
    List<String> LastConectedUids;

    public ParentChilds() {
        addCallbak(UserModel.class.getName(),(o) -> {
            if(o[0] instanceof UserModel) {
                if(FDatabase.curentUser().getRole() == Role.Parent){
                    FDatabase.getInstance().addPostEventListener(FDatabase.getInstance().getDb().child("users").child(FbCore.getInstance().get_user().getPhoneNumber()).child("conectedUids"),dataSnapshot -> {
                        List<String> LastConectedUids = (List<String>)dataSnapshot.getValue();
                        if(LastConectedUids != null)
                            ChildControl(LastConectedUids);
                        },databaseError -> {

                    });
                }
            }
        });
    }
    public void ChildControl(List<String> LCU){
        if(LastConectedUids == null) LastConectedUids= new ArrayList<>();
        // ставим текущиш детей на удаление
        for (String ids : LastConectedUids)
        {
            if(!LCU.contains(ids))
            {
                RemoveChildAndDisableEventlistiner(ids);
            }
        }

        for (String ids : LCU)
        {
            if(!LastConectedUids.contains(ids))
            {
                AddChildAndEnableEventlistiner(ids);
            }
        }
    }

    public void RemoveChildAndDisableEventlistiner(String ids){
        if(childs==null) childs = new HashMap<>();
        if(childs.containsKey(ids))
        {
            childs.remove(ids);
            FDatabase.getInstance().removePostEventListener(FDatabase.getInstance().getDb().child("users").child(ids));
            callCallbak(ParentChilds.class.getName(),childs.values());
        }
    }

    public Collection<UserModel> getChilds() {
        if(childs==null) childs = new HashMap<>();
        return childs.values();
    }

    public void AddChildAndEnableEventlistiner(String ids){
        FDatabase.getInstance().addPostEventListener(FDatabase.getInstance().getDb().child("users").child(ids),dataSnapshot -> {
            UserModel curentUserData = dataSnapshot.getValue(UserModel.class);
            if(curentUserData != null) curentUserData = curentUserData.getUserModel(dataSnapshot);
            if(childs==null) childs = new HashMap<>();
            if(childs.containsKey(ids)) childs.remove(ids);
            childs.put(ids,curentUserData);
            callCallbak(ParentChilds.class.getName(),childs.values());
        },databaseError -> {

        });
    }




}
