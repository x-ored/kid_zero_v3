package com.eternal.kidzero.core;

import static com.eternal.kidzero.core.CallbackManager.addCallbak;
import static com.eternal.kidzero.core.CallbackManager.callCallbak;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.models.ChildModelInvite;
import com.eternal.kidzero.models.QuestModel;
import com.eternal.kidzero.models.UserModel;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ParentChildsManager {
    public String TAG = "ParentChilds";
    HashMap<String, UserModel> childs;
    HashMap<String, UserModel> childsInvites;
    List<String> LastConectedUids;

    String selectUsermodel;
    int selectQuestModel;

    public UserModel getSelectUsermodel() {
        if(childs.containsKey(selectUsermodel))
        return childs.get(selectUsermodel);

        return null;
    }

    public void setSelectUsermodel(String selectUsermodel) {
        this.selectUsermodel = selectUsermodel;
    }

    public QuestModel getSelectQuestModel() {
        if(getSelectUsermodel()!=null && Objects.requireNonNull(getSelectUsermodel()).getQuests().get(selectQuestModel) !=null)
            return Objects.requireNonNull(getSelectUsermodel()).getQuests().get(selectQuestModel);

        return null;
    }

    public void setSelectQuestModel(int selectQuestModel) {
        this.selectQuestModel = selectQuestModel;
    }

    public ParentChildsManager() {
        addCallbak(UserModel.class.getName(),(ident, args) -> {
            if(args[0] instanceof UserModel) {
                if(FDatabase.curentUser().getRole() == Role.Parent){
                    FDatabase.getInstance().addPostEventListener(FDatabase.getInstance().getDb().child("users").child(FbCore.getInstance().get_user().getPhoneNumber()).child("conectedUids"), dataSnapshot -> {
                        List<String> LastConectedUids = (List<String>)dataSnapshot.getValue();
                        if(LastConectedUids == null) LastConectedUids = new ArrayList<>();
                        LastConectedUids.removeAll(Collections.singleton(null));
                        ChildControl(LastConectedUids);
                        },databaseError -> {

                    });

                    FDatabase.getInstance().addPostEventListener(FDatabase.getInstance().getDb().child("invites").child(FbCore.getInstance().get_user().getPhoneNumber()), dataSnapshot -> {
                        GenericTypeIndicator<HashMap<String, ChildModelInvite>> t = new GenericTypeIndicator<HashMap<String, ChildModelInvite>>() {};
                        setChildsInvites(dataSnapshot.getValue(t));
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
            callCallbak(ParentChildsManager.class.getName(),childs.values());
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
                callCallbak(ParentChildsManager.class.getName(), childs.values());
            }
        },databaseError -> {

        });
    }






    public void setChildsInvites(HashMap<? extends String, ? extends UserModel> childsInvites) {
        this.childsInvites = (HashMap<String, UserModel>) childsInvites;
        callCallbak("childsInvites",childsInvites);
    }

    public void getChildsInvites(CallbackManager.Callback callback) {
        if(childsInvites!=null) {
            callback.apply(childsInvites);
        }else{
            addCallbak("childsInvites", callback);
        }

    }
}
