package com.znv.service;

import java.util.List;
import java.util.Map;

public interface PrivilegeModuleService {

    public void insertPrivilegeModuleLink(
            String privilegeId,
            String moduleId
    );

    public void insertPrivilegeModulesLink(
            String privilegeId,
            String moduleIds
    );

    public void deletePrivilegeModuleLink(
            String id,
            String privilegeId,
            String moduleId
    );

    public void deletePrivilegeModuleLinks(
            String ids,
            String privilegeId,
            String moduleIds
    );

    public void updatePrivilegeModuleLink(
            String id,
            String privilegeId,
            String moduleId
    );

    public List<Map<String, Object>> queryPrivilegeModuleLinks(
            String id,
            String privilegeId,
            String moduleId
    );
}
