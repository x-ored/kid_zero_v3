package com.eternal.kidzero.models;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.FbCore;
import com.eternal.kidzero.Helper;
import com.eternal.kidzero.enums.Role;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserModel {
    String uid;
    Role role;

    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public UserModel(String UserId,Role Role) {
        this.uid = UserId;
        this.role = Role;
    }

    public Role getRole() {
        return role;
    }
    public UserModel setRole(Role role) {
        this.role = role;
        return this;
    }
    public String getUid() {
        return uid;
    }

    public String getName()
    {
       return "UserModel";
    }
    public UserModel setName(String name) {
        return this;
    }
    public List<String> getConectedUids() {
        return new ArrayList<>();
    }

    public UserModel AddConected(String UserId) {
        return this;
    }
    public UserModel RemoveConected(String UserId) {
        return this;
    }
    public UserModel getInvite(String parentId) {
        return null;
    }

    public UserModel getUserModel(DataSnapshot dataSnapshot)
    {
        if (this.getRole() == Role.Parent) {
             return dataSnapshot.getValue(ParentModel.class);
        }
        if (this.getRole() == Role.Child) {
                return  dataSnapshot.getValue(ChildModel.class);
        }
        return null;
    }
    public void add() {}

    public UserModel save() {
    return this;
    }

    public UserModel remove() {
    return this;
    }


    @Exclude
    public Map<String, Object> toMap() throws IllegalAccessException {
        return Helper.toMap(this);
    }


    public List<QuestModel> getQuests() {return null;}
    public void setQuests(List<QuestModel> quests) {}
    public void addQuests(QuestModel quests) {}
    public void removeQuests(QuestModel quests) {}

}