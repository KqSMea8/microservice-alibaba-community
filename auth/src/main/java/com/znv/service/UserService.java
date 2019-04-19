package com.znv.service;

import com.znv.bean.Module;
import com.znv.bean.Role;
import com.znv.bean.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface UserService {
    public void insertUser(User user);

    public void updateUser(
            String userId,
            String userName,
            String password,
            String usergroupId,
            String userType,
            String employeeId,
            String trueName,
            String mobilePhone,
            String email,
            String phone,
            String address,
            String userState,
            Integer errLoginTimes,
            Date updateTime,
            String description,
            String adminUser,
            String fax,
            String gender,
            String userLevel,
            String loginClientType,
            String userUrl,
            String systemFlag
    );

    public void deleteUsers(
            String userIds,
            String userName,
            String password,
            String usergroupId,
            String userType,
            String employeeId,
            String trueName,
            String mobilePhone,
            String email,
            String phone,
            String address,
            String userState,
            Integer errLoginTimes,
            Date updateTime,
            String description,
            String adminUser,
            String fax,
            String gender,
            String userLevel,
            String loginClientType,
            String userUrl,
            String systemFlag,
            Date createTime
    );

    public List<User> queryUsers(
            String userIds,
            String userName,
            String password,
            String usergroupId,
            String userType,
            String employeeId,
            String trueName,
            String mobilePhone,
            String email,
            String phone,
            String address,
            String userState,
            Integer errLoginTimes,
            Date updateTime,
            String description,
            String adminUser,
            String fax,
            String gender,
            String userLevel,
            String loginClientType,
            String userUrl,
            String systemFlag,
            Date createTime
    );

    public List<User> queryUserByName(
            String userName
    );

    public List<User> queryUserById(
            String userId
    );

    public List<Module> queryRelatedModules(
            String userId
    );

    public void addUserRoles(
            String userId,
            String roleIds
    );

    public void removeUserRoles(
            String userId,
            String roleIds
    );

    public List<Role> queryUserRoles(
            String userId
    );

    public void changePassword(String userName, String newPassword);
}
