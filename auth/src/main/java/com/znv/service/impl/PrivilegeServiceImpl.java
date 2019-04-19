package com.znv.service.impl;

import com.znv.bean.Privilege;
import com.znv.dao.mapper.PrivilegeServiceMapper;
import com.znv.service.PrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Resource
    PrivilegeServiceMapper privilegeServiceMapper;

    @Override
    public String insertPrivilege(String privilegeName, String privilegeType, String description, String upPrivilegeId) {
        Privilege privilege = new Privilege(null, privilegeName,privilegeType,description,upPrivilegeId);
        privilegeServiceMapper.insertPrivilege(privilege);
        return privilege.getPrivilegeId();
    }

    @Override
    public void deletePrivileges(String privilegeIds, String privilegeName, String privilegeType, String description, String upPrivilegeId) {
        privilegeServiceMapper.deletePrivileges(privilegeIds,privilegeName,privilegeType,description,upPrivilegeId);
    }

    @Override
    public void updatePrivilege(String privilegeId, String privilegeName, String privilegeType, String description, String upPrivilegeId) {
        privilegeServiceMapper.updatePrivilege(privilegeId,privilegeName,privilegeType,description,upPrivilegeId);
    }

    @Override
    public List<Map<String, Object>> queryPrivileges(String privilegeId, String privilegeName, String privilegeType, String description, String upPrivilegeId) {
        return privilegeServiceMapper.queryPrivileges(privilegeId,privilegeName,privilegeType,description,upPrivilegeId);
    }
}
