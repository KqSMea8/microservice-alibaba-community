package com.znv.service.impl;

import com.znv.dao.mapper.PrivilegeModuleServiceMapper;
import com.znv.service.PrivilegeModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PrivilegeModuleServiceImpl implements PrivilegeModuleService {

    @Resource
    PrivilegeModuleServiceMapper privilegeModuleServiceMapper;

    @Override
    public void insertPrivilegeModuleLink(String privilegeId, String moduleId) {
        privilegeModuleServiceMapper.insertPrivilegeModuleLink(privilegeId,moduleId);
    }

    @Override
    public void insertPrivilegeModulesLink(String privilegeId, String moduleIds) {
        if (moduleIds!=null&&!"".equals(moduleIds)) {
            List<String> moduleIdList = Arrays.asList(moduleIds.split(","));
            privilegeModuleServiceMapper.insertPrivilegeModulesLink(privilegeId,moduleIdList);
        }
    }

    @Override
    public void deletePrivilegeModuleLink(String id, String privilegeId, String moduleId) {
        privilegeModuleServiceMapper.deletePrivilegeModuleLink(id,privilegeId,moduleId);
    }

    @Override
    public void deletePrivilegeModuleLinks(String ids, String privilegeId, String moduleIds) {
        privilegeModuleServiceMapper.deletePrivilegeModuleLinks(ids,privilegeId,moduleIds);
    }

    @Override
    public void updatePrivilegeModuleLink(String id, String privilegeId, String moduleId) {
        privilegeModuleServiceMapper.updatePrivilegeModuleLink(id,privilegeId,moduleId);
    }

    @Override
    public List<Map<String, Object>> queryPrivilegeModuleLinks(String id, String privilegeId, String moduleId) {
        return privilegeModuleServiceMapper.queryPrivilegeModuleLinks(id,privilegeId,moduleId);
    }
}
