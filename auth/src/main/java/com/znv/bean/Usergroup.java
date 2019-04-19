package com.znv.bean;

import lombok.Data;

import java.util.List;

/**
 * 用户组（组织）
 */
@Data
public class Usergroup {
    private String usergroupId;
    private String usergroupName;
    private String usergroupType;
    private String description;
    private String upUsergroupId;

    private List<User> users;
    private List<Role> roles;

    public Usergroup(){}

    public Usergroup(String usergroupId, String usergroupName, String usergroupType,
                     String description, String upUsergroupId) {
        this.usergroupId = usergroupId;
        this.usergroupName = usergroupName;
        this.usergroupType = usergroupType;
        this.description = description;
        this.upUsergroupId = upUsergroupId;
    }

}
