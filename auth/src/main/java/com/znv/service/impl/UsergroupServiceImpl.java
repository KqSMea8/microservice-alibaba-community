package com.znv.service.impl;

import com.znv.bean.Role;
import com.znv.bean.Usergroup;
import com.znv.dao.mapper.UsergroupServiceMapper;
import com.znv.service.UsergroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class UsergroupServiceImpl implements UsergroupService {

    @Resource
    UsergroupServiceMapper usergroupServiceMapper;

    @Override
    public void insertUsergroup(Usergroup usergroup) {
        usergroupServiceMapper.insertUsergroup(usergroup);
    }

    @Override
    public void deleteUsergroups(String usergroupIds, String usergroupName, String usergroupType, String description, String upUsergroupId) {
        usergroupServiceMapper.deleteUsergroups(usergroupIds, usergroupName, usergroupType, description, upUsergroupId);
    }

    @Override
    public void updateUsergroup(String usergroupId, String usergroupName, String usergroupType, String description, String upUsergroupId) {
        usergroupServiceMapper.updateUsergroup(usergroupId, usergroupName, usergroupType, description, upUsergroupId);
    }

    @Override
    public List<Usergroup> queryUsergroups(String usergroupIds, String usergroupName, String usergroupType, String description, String upUsergroupId) {
        return usergroupServiceMapper.queryUsergroups(usergroupIds, usergroupName, usergroupType, description, upUsergroupId);
    }

    @Override
    public void addUsergroupRoles(String usergroupId, String roleIds) {
        if (roleIds!=null&&!"".equals(roleIds)) {
            List<String> roleIdList = Arrays.asList(roleIds.split(","));
            usergroupServiceMapper.addUsergroupRoles(usergroupId, roleIdList);
        }

    }

    @Override
    public void deleteUsergroupRoles(String ids, String usergroupId, String roleIds) {
        usergroupServiceMapper.deleteUsergroupRoles(ids, usergroupId, roleIds);
    }

    @Override
    public List<Role> queryUsergroupRoles(String usergroupId) {
        return usergroupServiceMapper.queryUsergroupRoles(usergroupId);
    }

    @Override
    public void updateUsergroupRole(String id, String usergroupId, String roleId) {
        usergroupServiceMapper.updateUsergroupRole(id, usergroupId, roleId);
    }
}
