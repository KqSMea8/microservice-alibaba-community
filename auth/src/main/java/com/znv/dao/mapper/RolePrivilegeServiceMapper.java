package com.znv.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RolePrivilegeServiceMapper {

    public void insertRolePrivileges(
            @Param(value = "roleId") String roleId,
            @Param(value = "privilegeIds") List privilegeIds
    );

    public void deleteRolePrivileges(
            @Param(value = "ids") String ids,
            @Param(value = "roleId") String roleId,
            @Param(value = "privilegeIds") String privilegeIds
    );

    public void updateRolePrivilege(
            @Param(value = "id") String id,
            @Param(value = "roleId") String roleId,
            @Param(value = "privilegeId") String privilegeId
    );

    public List<Map<String, Object>> queryRolePrivileges(
            @Param(value = "ids") String ids,
            @Param(value = "roleId") String roleId,
            @Param(value = "privilegeId") String privilegeId
    );

}
