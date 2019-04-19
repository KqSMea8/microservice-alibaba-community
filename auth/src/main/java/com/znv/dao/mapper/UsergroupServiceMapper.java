package com.znv.dao.mapper;

import com.znv.bean.Role;
import com.znv.bean.Usergroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UsergroupServiceMapper {

    public void insertUsergroup(Usergroup usergroup);

    public void deleteUsergroups(
            @Param(value = "usergroupIds") String usergroupIds,
            @Param(value = "usergroupName") String usergroupName,
            @Param(value = "usergroupType") String usergroupType,
            @Param(value = "description") String description,
            @Param(value = "upUsergroupId") String upUsergroupId
    );

    public void updateUsergroup(
            @Param(value = "usergroupId") String usergroupId,
            @Param(value = "usergroupName") String usergroupName,
            @Param(value = "usergroupType") String usergroupType,
            @Param(value = "description") String description,
            @Param(value = "upUsergroupId") String upUsergroupId
    );

    public List<Usergroup> queryUsergroups(
            @Param(value = "usergroupIds") String usergroupIds,
            @Param(value = "usergroupName") String usergroupName,
            @Param(value = "usergroupType") String usergroupType,
            @Param(value = "description") String description,
            @Param(value = "upUsergroupId") String upUsergroupId
    );


    public void addUsergroupRoles(
            @Param(value = "usergroupId") String usergroupId,
            @Param(value = "roleIds") List roleIds
    );

    public void deleteUsergroupRoles(
            @Param(value = "ids") String ids,
            @Param(value = "usergroupId") String usergroupId,
            @Param(value = "roleIds") String roleIds
    );

    public List<Role> queryUsergroupRoles(
            @Param(value = "usergroupId") String usergroupId
    );

    /**
     *指定usergroup_role 表id,更新另外两项，一般不用
     */
    public void updateUsergroupRole(
            @Param(value = "id") String id,
            @Param(value = "usergroupId") String usergroupId,
            @Param(value = "roleId") String roleId
    );
}
