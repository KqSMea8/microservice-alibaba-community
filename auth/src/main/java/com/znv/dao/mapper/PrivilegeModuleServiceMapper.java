package com.znv.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PrivilegeModuleServiceMapper {

    public void insertPrivilegeModuleLink(
            @Param(value = "privilegeId") String privilegeId,
            @Param(value = "moduleId") String moduleId
    );

    public void insertPrivilegeModulesLink(
            @Param(value = "privilegeId") String privilegeId,
            @Param(value = "moduleIds") List moduleIds
    );

    public void deletePrivilegeModuleLink(
            @Param(value = "id") String id,
            @Param(value = "privilegeId") String privilegeId,
            @Param(value = "moduleId") String moduleId
    );

    public void deletePrivilegeModuleLinks(
            @Param(value = "ids") String ids,
            @Param(value = "privilegeId") String privilegeId,
            @Param(value = "moduleIds") String moduleIds
    );

    public void updatePrivilegeModuleLink(
            @Param(value = "id") String id,
            @Param(value = "privilegeId") String privilegeId,
            @Param(value = "moduleId") String moduleId
    );

    public List<Map<String, Object>> queryPrivilegeModuleLinks(
            @Param(value = "ids") String ids,
            @Param(value = "privilegeId") String privilegeId,
            @Param(value = "moduleId") String moduleId
    );
}
