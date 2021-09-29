package com.eternal.kidzero.models;

import com.eternal.kidzero.enums.Role;

public class ChildModel extends UserModel {

     String name;

    public ChildModel() {
    }
    public ChildModel(String UserId,Role Role,String name) {
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
}
