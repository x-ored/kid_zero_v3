package com.eternal.kidzero.models;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.enums.Role;

public class ChildModelInvite extends UserModel {
    String name;
    String id;
    String parentId;

    public ChildModelInvite(){}
    public ChildModelInvite(String UserId, Role Role, String name,String parentId) {
        super(UserId,Role);
        this.name = name;
        this.parentId = parentId;
    }
    public String getId() {
        if(id == null) id = FDatabase.getInstance().getDb().child("invites").child(parentId).push().getKey();
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getName()
    {
        return name;
    }
    @Override
    public ChildModelInvite setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public void add() {
        FDatabase.getInstance().getDb().child("invites").child(parentId).child(getId()).setValue(this);
    }
    @Override
    public ChildModelInvite save() { FDatabase.getInstance().getDb().child("invites").child(parentId).child(getId()).setValue(this);
    return this;
    }
    @Override
    public ChildModelInvite remove() {
        if(id != null)
        FDatabase.getInstance().getDb().child("invites").child(parentId).child(getId()).setValue(null);

        return this;
    }
}
