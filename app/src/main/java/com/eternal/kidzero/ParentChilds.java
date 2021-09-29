package com.eternal.kidzero;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;
import static com.eternal.kidzero.core.CallbackManager.callCallbak;

import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.models.UserModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ParentChilds {
    public String TAG = "ParentChilds";
    HashMap<String, UserModel> childs;
    List<String> LastConectedUids;

    public ParentChilds() {
        addCallbak(UserModel.class.getName(),(ident, args) -> {
            if(args[0] instanceof UserModel) {
                if(FDatabase.curentUser().getRole() == Role.Parent){
                    FDatabase.getInstance().addPostEventListener(FDatabase.getInstance().getDb().child("users").child(FbCore.getInstance().get_user().getPhoneNumber()).child("conectedUids"),dataSnapshot -> {
                        List<String> LastConectedUids = (List<String>)dataSnapshot.getValue();
                        if(LastConectedUids == null) LastConectedUids = new ArrayList<>();
                        LastConectedUids.removeAll(Collections.singleton(null));
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
        for (String ids : new ArrayList<>(LastConectedUids))
        {
            if(!LCU.contains(ids))
            {   LastConectedUids.remove(ids);
                RemoveChildAndDisableEventlistiner(ids);
            }
        }

        for (String ids : LCU)
        {
            if(!LastConectedUids.contains(ids))
            {   LastConectedUids.add(ids);
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
            if(dataSnapshot.getValue()!= null)
            {
                UserModel curentUserData = Objects.requireNonNull(dataSnapshot.getValue(UserModel.class)).getUserModel(dataSnapshot);
                if (childs == null) childs = new HashMap<>();
                if (childs.containsKey(ids)) childs.remove(ids);
                childs.put(ids, curentUserData);
                callCallbak(ParentChilds.class.getName(), childs.values());
            }
        },databaseError -> {

        });
    }




}
