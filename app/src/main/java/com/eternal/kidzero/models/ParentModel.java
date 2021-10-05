package com.eternal.kidzero.models;

import com.eternal.kidzero.FDatabase;
import com.eternal.kidzero.enums.Role;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;


public class ParentModel extends UserModel {

    String name;

    List<String> conectedUids;

    public ParentModel() {
    }
    public ParentModel(String UserId, Role Role, String name) {
        super(UserId,Role);
        this.name = name;
    }
    @Override
    public List<String> getConectedUids() {
        return conectedUids;
    }
    @Override
    public ParentModel AddConected(String UserId) {
        if(conectedUids == null) conectedUids = new ArrayList<String>();
        conectedUids.add(UserId);
        return this;
    }
    @Override
    public ParentModel RemoveConected(String UserId) {

        if(conectedUids == null) conectedUids = new ArrayList<String>();
        conectedUids.remove(UserId);
        return this;
    }
    @Override
    public Task<Void> save() { return FDatabase.getInstance().getDb().child("users").child(uid).setValue(this); }
    @Override
    public ParentModel remove() {FDatabase.getInstance().getDb().child("users").child(uid).setValue(null);
        return this;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public ParentModel setName(String name) {
        this.name = name;
        return this;
    }


}
