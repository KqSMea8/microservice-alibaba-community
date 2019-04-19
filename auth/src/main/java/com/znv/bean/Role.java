package com.znv.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Role {
    private String roleId;
    private String roleName;
    //暂时不分层，可为空
    private String upRoleId;
    private String description;

    //角色关联的权限集合
    private List<Privilege> privileges = new ArrayList<>();

    public Role() {

    }

    public Role(
            String roleId,
            String roleName,
            String upRoleId,
            String description
    ) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.upRoleId = upRoleId;
        this.description = description;
    }

}
