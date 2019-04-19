package com.znv.service;

import com.znv.bean.Role;
import com.znv.bean.Usergroup;

import java.util.List;

public interface UsergroupService {

    public void insertUsergroup(Usergroup usergroup);

    public void deleteUsergroups(
            String usergroupIds,
            String usergroupName,
            String usergroupType,
            String description,
            String upUsergroupId
    );

    public void updateUsergroup(
            String usergroupId,
            String usergroupName,
            String usergroupType,
            String description,
            String upUsergroupId
    );

    public List<Usergroup> queryUsergroups(
            String usergroupIds,
            String usergroupName,
            String usergroupType,
            String description,
            String upUsergroupId
    );

    public void addUsergroupRoles(
            String usergroupId,
            String roleIds
    );

    public void deleteUsergroupRoles(
            String ids,
            String usergroupId,
            String roleIds
    );

    public List<Role> queryUsergroupRoles(
            String usergroupId
    );

    public void updateUsergroupRole(
            String id,
            String usergroupId,
            String roleId
    );
}
