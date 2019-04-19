package com.znv.service;

import com.znv.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    public void insertRole(
            Role role
    );
    public void deleteRoles(
            String roleIds,
            String roleName,
            String upRoleId,
            String description
    );
    public void updateRole(
            String roleId,
            String roleName,
            String upRoleId,
            String description
    );
    public List<Role> queryRoles(
            String roleIds,
            String roleName,
            String upRoleId,
            String description
    );
}
