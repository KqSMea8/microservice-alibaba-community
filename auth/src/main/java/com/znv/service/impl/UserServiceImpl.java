package com.znv.service.impl;

import com.znv.bean.Module;
import com.znv.bean.Role;
import com.znv.bean.User;
import com.znv.dao.mapper.UserServiceMapper;
import com.znv.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserServiceMapper userServiceMapper;

    @Override
    public void insertUser(User user) {
        userServiceMapper.insertUser(user);
    }

    @Override
    public void updateUser(String userId, String userName, String password, String usergroupId,
                           String userType, String employeeId, String trueName,
                           String mobilePhone, String email, String phone,
                           String address, String userState, Integer errLoginTimes,Date updateTime,
                           String description, String adminUser, String fax,
                           String gender, String userLevel, String loginClientType,
                           String userUrl, String systemFlag) {
        userServiceMapper.updateUser(userId, userName, password, usergroupId, userType, employeeId,
                trueName, mobilePhone, email, phone,address,userState,errLoginTimes,updateTime,
                description,adminUser,fax,gender,userLevel,loginClientType,userUrl,systemFlag);
    }

    @Override
    public void deleteUsers(String userIds, String userName, String password, String usergroupId, String userType, String employeeId, String trueName, String mobilePhone, String email, String phone, String address, String userState,  Integer errLoginTimes,Date updateTime, String description, String adminUser, String fax, String gender, String userLevel, String loginClientType, String userUrl, String systemFlag, Date createTime) {
        userServiceMapper.deleteUsers(userIds,userName,password,usergroupId,userType,
                employeeId,trueName,mobilePhone,email,phone,address,userState,errLoginTimes,updateTime,
                description,adminUser,fax,gender,userLevel,loginClientType,userUrl,systemFlag,
                createTime);
    }

    @Override
    public List<User> queryUsers(String userIds, String userName, String password, String usergroupId, String userType, String employeeId, String trueName, String mobilePhone, String email, String phone, String address, String userState,  Integer errLoginTimes,Date updateTime, String description, String adminUser, String fax, String gender, String userLevel, String loginClientType, String userUrl, String systemFlag, Date createTime) {
        return userServiceMapper.queryUsers(userIds,userName,password,usergroupId,
                userType,employeeId,trueName,mobilePhone,email,phone,address,userState,errLoginTimes,
                updateTime,description,adminUser,fax,gender,userLevel,loginClientType,
                userUrl,systemFlag,createTime);
    }

    @Override
    public List<User> queryUserByName(
            String userName
    ) {
        return userServiceMapper.queryUsers(null,userName,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,
                null,null,null);
    }

    @Override
    public List<User> queryUserById(
            String userId
    ) {
        return userServiceMapper.queryUsers(userId,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,
                null,null,null);
    }

    @Override
    public List<Module> queryRelatedModules(String userId) {
        return userServiceMapper.queryRelatedModules(userId);
    }

    @Override
    public void addUserRoles(String userId, String roleIds) {
        if (roleIds!=null&&!"".equals(roleIds)) {
            List<String> roleIdList = Arrays.asList(roleIds.split(","));
            userServiceMapper.addUserRoles(userId, roleIdList);
        }
    }

    @Override
    public void removeUserRoles(String userId, String roleIds) {
        userServiceMapper.removeUserRoles(userId, roleIds);
    }

    @Override
    public List<Role> queryUserRoles(String userId) {
        return userServiceMapper.queryUserRoles(userId);
    }

    @Override
    public void changePassword(String userName , String newPassword) {
       userServiceMapper.changePassword(userName, newPassword);
    }
}
