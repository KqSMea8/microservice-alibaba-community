package com.znv.service;

import java.util.List;
import java.util.Map;

public interface RolePrivilegeService {

    public void insertRolePrivileges(
            String roleId,
            String privilegeIds
    );

    public void deleteRolePrivileges(
            String ids,
            String roleId,
            String privilegeIds
    );

    public void updateRolePrivilege(
            String id,
            String roleId,
            String privilegeId
    );

    public List<Map<String, Object>> queryRolePrivileges(
            String ids,
            String roleId,
            String privilegeId
    );
}
