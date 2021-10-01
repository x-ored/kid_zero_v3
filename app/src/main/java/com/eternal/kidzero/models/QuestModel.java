package com.eternal.kidzero.models;

import com.eternal.kidzero.FDatabase;

public class QuestModel {

    public String childId;
    public String id;
    public String questText;
    public int reward;

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getId() {
        if(id == null) id = FDatabase.getInstance().getDb().child("users").child(getChildId()).child("questslist").push().getKey();
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestText() {
        return questText;
    }

    public void setQuestText(String questText) {
        this.questText = questText;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public QuestModel() {

    }
    public QuestModel(String childId) {
        this.childId = childId;
    }

    public void save() { FDatabase.getInstance().getDb().child("users").child(getChildId()).child("questslist").child(getId()).setValue(this);}
    public void remove() { FDatabase.getInstance().getDb().child("users").child(getChildId()).child("questslist").child(getId()).setValue(null);}




}
