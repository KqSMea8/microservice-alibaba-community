package com.znv.dao.mapper;

import com.znv.bean.Privilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PrivilegeServiceMapper {

    public Long insertPrivilege(
            Privilege privilege
    );

    public void deletePrivileges(
            @Param(value = "privilegeIds") String privilegeIds,
            @Param(value = "privilegeName") String privilegeName,
            @Param(value = "privilegeType") String privilegeType,
            @Param(value = "description") String description,
            @Param(value = "upPrivilegeId") String upPrivilegeId
    );

    public void updatePrivilege(
            @Param(value = "privilegeId") String privilegeId,
            @Param(value = "privilegeName") String privilegeName,
            @Param(value = "privilegeType") String privilegeType,
            @Param(value = "description") String description,
            @Param(value = "upPrivilegeId") String upPrivilegeId
    );

    public List<Map<String, Object>> queryPrivileges(
            @Param(value = "privilegeId") String privilegeId,
            @Param(value = "privilegeName") String privilegeName,
            @Param(value = "privilegeType") String privilegeType,
            @Param(value = "description") String description,
            @Param(value = "upPrivilegeId") String upPrivilegeId
    );
}
