package com.znv.dao.mapper;

import com.znv.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleServiceMapper {
    public void insertRole(
            Role role
    );
    public void deleteRoles(
            @Param(value = "roleIds") String roleIds,
            @Param(value = "roleName") String roleName,
            @Param(value = "upRoleId") String upRoleId,
            @Param(value = "description") String description
    );
    public void updateRole(
            @Param(value = "roleId") String roleId,
            @Param(value = "roleName") String roleName,
            @Param(value = "upRoleId") String upRoleId,
            @Param(value = "description") String description
    );
    public List<Role> queryRoles(
            @Param(value = "roleIds") String roleIds,
            @Param(value = "roleName") String roleName,
            @Param(value = "upRoleId") String upRoleId,
            @Param(value = "description") String description
    );
}
