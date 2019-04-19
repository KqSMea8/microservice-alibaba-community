package com.znv.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private String userId;
    private String userName;
    private String password;
    private String usergroupId;//0不属于任何组织，默认0
    private String userType; //0普通用户,1第三方平台，默认0
    private String employeeId;
    private String trueName;
    private String mobilePhone;
    private String email;
    private String phone;
    private String address;
    private String userState;//0可用,1不可用，默认0
    private Integer errLoginTimes;//错误的登录的次数，大于5次锁定
    private Date updateTime;
    private String description;
    private String adminUser;
    private String fax;
    private String gender;//0男，1女，2未知，默认2
    private String userLevel;
    private String loginClientType;
    private byte[] userImg;
    private String userUrl;
    private String systemFlag;
    private Date createTime;
    private String usergroupName;

    //用户关联的角色集合（不包括通过用户组关联的角色）
    private List<Role> roles = new ArrayList<>();

    private List<Role> usergroupRoles = new ArrayList<>();

    //有权限的模块
    private List<HashMap> modules = new ArrayList<>();

    public User(
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
            byte[] userImg,
            String userUrl,
            String systemFlag,
            Date createTime,
            String usergroupName
    ) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.usergroupId = usergroupId;
        this.userType = userType;
        this.employeeId = employeeId;
        this.trueName = trueName;
        this.mobilePhone =  mobilePhone;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.userState = userState;
        this.errLoginTimes = errLoginTimes;
        this.updateTime = updateTime;
        this.description = description;
        this.adminUser = adminUser;
        this.fax = fax;
        this.gender = gender;
        this.userLevel = userLevel;
        this.loginClientType = loginClientType;
        this.userImg = userImg;
        this.userUrl = userUrl;
        this.systemFlag = systemFlag;
        this.createTime = createTime;
        this.usergroupName = usergroupName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
