package com.eternal.kidzero.models;

import com.eternal.kidzero.enums.Role;

public class ParentModel extends UserModel {

     String name;

    public ParentModel() {
    }
    public ParentModel(String UserId, Role Role, String name) {
        super(UserId,Role);
        this.name = name;
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
