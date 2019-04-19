package com.znv.service.impl;

import com.znv.dao.mapper.RolePrivilegeServiceMapper;
import com.znv.service.RolePrivilegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {

    @Resource
    RolePrivilegeServiceMapper rolePrivilegeServiceMapper;

    @Override
    public void insertRolePrivileges(String roleId, String privilegeIds) {
        if(privilegeIds!=null&&!"".equals(privilegeIds)){
            List<String> privilegeIdList = Arrays.asList(privilegeIds.split(","));
            rolePrivilegeServiceMapper.insertRolePrivileges(roleId, privilegeIdList);
        }
    }

    @Override
    public void deleteRolePrivileges(String ids, String roleId, String privilegeIds) {
        rolePrivilegeServiceMapper.deleteRolePrivileges(ids, roleId, privilegeIds);
    }

    @Override
    public void updateRolePrivilege(String id, String roleId, String privilegeId) {
        rolePrivilegeServiceMapper.updateRolePrivilege(id, roleId, privilegeId);
    }

    @Override
    public List<Map<String, Object>> queryRolePrivileges(String ids, String roleId, String privilegeId) {
        return rolePrivilegeServiceMapper.queryRolePrivileges(ids,roleId,privilegeId);
    }
}
