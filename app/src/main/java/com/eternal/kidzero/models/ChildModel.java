package com.eternal.kidzero.models;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class ChildModel extends UserModel {

    String name;

    List<QuestModel> questslist;

    public ChildModel() {
    }

    public List<QuestModel> getQuests() {
        if(questslist == null) questslist = new ArrayList<>();
        return questslist;
    }
    public void setQuests(List<QuestModel> quests) {
        if(questslist == null) questslist = new ArrayList<>();
        this.questslist = quests;
    }
    public void addQuests(QuestModel quests) {
        if(questslist == null) questslist = new ArrayList<>();
        this.questslist.add(quests);
    }

    public void removeQuests(QuestModel quests) {
        if(questslist == null) questslist = new ArrayList<>();
        this.questslist.remove(quests);
    }

    public ChildModel(String UserId, Role Role, String name) {
        super(UserId,Role);
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }
    @Override
    public ChildModel setName(String name) {
        this.name = name;
        return this;
    }
    @Override
    public ChildModel save() { FDatabase.getInstance().getDb().child("users").child(uid).setValue(this);
        return this;
    }
    @Override
    public ChildModel remove() {FDatabase.getInstance().getDb().child("users").child(uid).setValue(null);
        return this;
    }

    @Override
    public ChildModelInvite getInvite(String parentId) {
        return new ChildModelInvite(uid,role,name,parentId);
    }
}
