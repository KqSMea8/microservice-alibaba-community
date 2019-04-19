package com.znv.dao.mapper;

import com.znv.bean.Module;
import com.znv.bean.Role;
import com.znv.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserServiceMapper {

    public void insertUser(User user);

    public void updateUser(
            @Param(value = "userId") String userId,
            @Param(value = "userName") String userName,
            @Param(value = "password") String password,
            @Param(value = "usergroupId") String usergroupId,
            @Param(value = "userType") String userType,
            @Param(value = "employeeId") String employeeId,
            @Param(value = "trueName") String trueName,
            @Param(value = "mobilePhone") String mobilePhone,
            @Param(value = "email") String email,
            @Param(value = "phone") String phone,
            @Param(value = "address") String address,
            @Param(value = "userState") String userState,
            @Param(value = "errLoginTimes") Integer errLoginTimes,
            @Param(value = "updateTime") Date updateTime,
            @Param(value = "description") String description,
            @Param(value = "adminUser") String adminUser,
            @Param(value = "fax") String fax,
            @Param(value = "gender") String gender,
            @Param(value = "userLevel") String userLevel,
            @Param(value = "loginClientType") String loginClientType,
            @Param(value = "userUrl") String userUrl,
            @Param(value = "systemFlag") String systemFlag
    );

    public void deleteUsers(
            @Param(value = "userIds") String userIds,
            @Param(value = "userName") String userName,
            @Param(value = "password") String password,
            @Param(value = "usergroupId") String usergroupId,
            @Param(value = "userType") String userType,
            @Param(value = "employeeId") String employeeId,
            @Param(value = "trueName") String trueName,
            @Param(value = "mobilePhone") String mobilePhone,
            @Param(value = "email") String email,
            @Param(value = "phone") String phone,
            @Param(value = "address") String address,
            @Param(value = "userState") String userState,
            @Param(value = "errLoginTimes") Integer errLoginTimes,
            @Param(value = "updateTime") Date updateTime,
            @Param(value = "description") String description,
            @Param(value = "adminUser") String adminUser,
            @Param(value = "fax") String fax,
            @Param(value = "gender") String gender,
            @Param(value = "userLevel") String userLevel,
            @Param(value = "loginClientType") String loginClientType,
            @Param(value = "userUrl") String userUrl,
            @Param(value = "systemFlag") String systemFlag,
            @Param(value = "createTime") Date createTime
    );

    public List<User> queryUsers(
            @Param(value = "userIds") String userIds,
            @Param(value = "userName") String userName,
            @Param(value = "password") String password,
            @Param(value = "usergroupId") String usergroupId,
            @Param(value = "userType") String userType,
            @Param(value = "employeeId") String employeeId,
            @Param(value = "trueName") String trueName,
            @Param(value = "mobilePhone") String mobilePhone,
            @Param(value = "email") String email,
            @Param(value = "phone") String phone,
            @Param(value = "address") String address,
            @Param(value = "userState") String userState,
            @Param(value = "errLoginTimes") Integer errLoginTimes,
            @Param(value = "updateTime") Date updateTime,
            @Param(value = "description") String description,
            @Param(value = "adminUser") String adminUser,
            @Param(value = "fax") String fax,
            @Param(value = "gender") String gender,
            @Param(value = "userLevel") String userLevel,
            @Param(value = "loginClientType") String loginClientType,
            @Param(value = "userUrl") String userUrl,
            @Param(value = "systemFlag") String systemFlag,
            @Param(value = "createTime") Date createTime
    );

    public List<Module> queryRelatedModules(
            @Param(value = "userId") String userId
    );

    public void addUserRoles(
            @Param(value = "userId") String userId,
            @Param(value = "roleIds") List roleIds
    );

    public void removeUserRoles(
            @Param(value = "userId") String userId,
            @Param(value = "roleIds") String roleIds
    );

    public List<Role> queryUserRoles(
            @Param(value = "userId") String userId
    );

    public void  changePassword(
            @Param(value = "userName") String userName,
            @Param(value = "newPassword") String newPassword);
}
