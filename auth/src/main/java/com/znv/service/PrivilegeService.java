package com.znv.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PrivilegeService {
    /**
     *
     * @param privilegeName
     * @param privilegeType
     * @param description
     * @param upPrivilegeId
     */
    public String insertPrivilege(
            String privilegeName,
            String privilegeType,
            String description,
            String upPrivilegeId
    );

    public void deletePrivileges(
            String privilegeIds,
            String privilegeName,
            String privilegeType,
            String description,
            String upPrivilegeId
    );

    public void updatePrivilege(
            String privilegeId,
            String privilegeName,
            String privilegeType,
            String description,
            String upPrivilegeId
    );

    public List<Map<String, Object>> queryPrivileges(
            String privilegeId,
            String privilegeName,
            String privilegeType,
            String description,
            String upPrivilegeId
    );
}
