package com.eternal.kidzero.models;

import com.eternal.kidzero.Helper;
import com.eternal.kidzero.enums.InviteStatus;
import com.eternal.kidzero.enums.Role;
import com.eternal.kidzero.interfaces.functions.Functions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserModel implements Serializable {

    String uid;
    Role role;

    public UserModel() { }

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
    public void sendInvite(Functions.Action<String> surcess, Functions.Action<String> canle,Functions.Action<String> waitinviteOnTick) {}

    public Task<Void> save() {
    return null;
    }

    public UserModel remove() {
    return this;
    }

    public InviteStatus getStatus() {
        return InviteStatus.none;
    }

    public UserModel setStatus(InviteStatus status) {
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