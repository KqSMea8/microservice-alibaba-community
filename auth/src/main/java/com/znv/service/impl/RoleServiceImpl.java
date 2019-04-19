package com.znv.service.impl;

import com.znv.bean.Role;
import com.znv.dao.mapper.RoleServiceMapper;
import com.znv.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleServiceMapper roleServiceMapper;

    @Override
    public void insertRole(Role role) {
        roleServiceMapper.insertRole(role);
    }

    @Override
    public void deleteRoles(String roleIds, String roleName, String upRoleId, String description) {
        roleServiceMapper.deleteRoles(roleIds, roleName, upRoleId, description);
    }

    @Override
    public void updateRole(String roleId, String roleName, String upRoleId, String description) {
        roleServiceMapper.updateRole(roleId, roleName, upRoleId, description);
    }

    @Override
    public List<Role> queryRoles(String roleIds, String roleName, String upRoleId, String description) {
        return roleServiceMapper.queryRoles(roleIds, roleName, upRoleId, description);
    }
}
