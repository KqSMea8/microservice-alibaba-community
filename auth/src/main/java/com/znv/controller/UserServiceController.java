package com.znv.controller;

import com.znv.bean.Module;
import com.znv.bean.ResultBean;
import com.znv.bean.Role;
import com.znv.bean.User;
import com.znv.redis.RedisService;
import com.znv.service.LogService;
import com.znv.service.RoleService;
import com.znv.service.UserService;
import com.znv.service.UsergroupService;
import com.znv.utils.CusAccessUtil;
import com.znv.utils.LogUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserServiceController {

    @Resource
    RedisService redisService;

    @Resource
    UserService userService;

    @Resource
    LogService logService;

    @Resource
    RoleService roleService;

    @Resource
    UsergroupService usergroupService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertUser(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "usergroupId", required = false, defaultValue = "0") String usergroupId,
            @RequestParam(value = "gender", required = false, defaultValue = "2") int gender,
            @RequestParam(value = "userType", required = false, defaultValue = "0") int userType,
            @RequestParam(value = "employeeId", required = false) String employeeId,
            @RequestParam(value = "trueName", required = false) String trueName,
            @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "userState", required = false, defaultValue = "0") int userState,
            @RequestParam(value = "errLoginTimes", required = false, defaultValue = "0") int errLoginTimes,
            @RequestParam(value = "updateTime", required = false) Date updateTime,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "adminUser", required = false) String adminUser,
            @RequestParam(value = "fax", required = false) String fax,
            @RequestParam(value = "userLevel", required = false, defaultValue = "1") int userLevel,
            @RequestParam(value = "loginClientType", required = false, defaultValue = "1") int loginClientType,
            @RequestParam(value = "userUrl", required = false) String userUrl,
            @RequestParam(value = "systemFlag", required = false) String systemFlag,
            @RequestParam(value = "roleIds", required = false) String roleIds,
            HttpServletRequest request
    ) {
        ResultBean<String> ret = new ResultBean<>();

        if(isSpecialChar(userName)||password.length()<6){
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("用户名不能为空，不能含有特殊字符，密码不能小于6位");
            return ret;
        }
        try {

            User user = new User(null, userName, password, usergroupId, userType + "", employeeId, trueName,
                    mobilePhone, email, phone, address, userState + "", errLoginTimes, updateTime, description, adminUser,
                    fax, gender + "", userLevel + "", loginClientType + "", null, userUrl, systemFlag, null, null);
            userService.insertUser(user);
            userService.addUserRoles(user.getUserId(), roleIds);
            ret.addData(Arrays.asList(user.getUserId()));
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
            Map<String,String> map = this.getLogingUserInfo(request);
            if (map.size() != 0){
                logService.insertLog("create", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), user.getUserId(), user.getUserName(), "0",null);
            }
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updateUser(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "usergroupId", required = false) String usergroupId,
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "employeeId", required = false) String employeeId,
            @RequestParam(value = "trueName", required = false) String trueName,
            @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "userState", required = false) String userState,
            @RequestParam(value = "errLoginTimes", required = false) Integer errLoginTimes,
            @RequestParam(value = "updateTime", required = false) Date updateTime,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "adminUser", required = false) String adminUser,
            @RequestParam(value = "fax", required = false) String fax,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "userLevel", required = false) String userLevel,
            @RequestParam(value = "loginClientType", required = false) String loginClientType,
            @RequestParam(value = "userUrl", required = false) String userUrl,
            @RequestParam(value = "systemFlag", required = false) String systemFlag,
            HttpServletRequest request
    ) {
        ResultBean<String> ret = new ResultBean<>();
        if(userName!=null&&isSpecialChar(userName)){
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("用户名不能为空，不能含有特殊字符");
            return ret;
        }
        if(userName!=null&&password!=null&&password.length()<6){
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("密码不能小于6位");
            return ret;
        }
        try {
            userService.updateUser(userId, userName, password, usergroupId, userType, employeeId, trueName,
                    mobilePhone, email, phone, address, userState, errLoginTimes, updateTime, description, adminUser,
                    fax, gender, userLevel, loginClientType, userUrl, systemFlag);

            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

            Map<String,String> map = this.getLogingUserInfo(request);
            if (map.size() != 0){
                User user = userService.queryUserById(userId).get(0);
                if(usergroupId!=null){
                    String usergroupName = usergroupService.queryUsergroups(usergroupId,null,null,null,null).get(0).getUsergroupName();
                    logService.insertLog("update",  map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "0","修改用户组为:"+usergroupName);
                }
                if(trueName!=null){
                    logService.insertLog("update",  map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "0","修改真实姓名为:"+trueName);
                }
                if(phone!=null){
                    logService.insertLog("update",  map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "0","修改电话为:"+phone);
                }
                if(email!=null){
                    logService.insertLog("update",  map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "0","修改邮箱为:"+email);
                }
                if(gender!=null){
                    String genderName="";
                    if(gender=="0"){
                        genderName="男";
                    }
                    else if(gender=="1"){
                        genderName="女";
                    }else{
                        genderName="未知";
                    }

                    logService.insertLog("update",  map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "0","修改性别为:"+genderName);
                }

            }

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deleteUsers(
            @RequestParam(value = "userIds", required = false) String userIds,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "usergroupId", required = false) String usergroupId,
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "employeeId", required = false) String employeeId,
            @RequestParam(value = "trueName", required = false) String trueName,
            @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "userState", required = false) String userState,
            @RequestParam(value = "errLoginTimes", required = false) Integer errLoginTimes,
            @RequestParam(value = "updateTime", required = false) Date updateTime,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "adminUser", required = false) String adminUser,
            @RequestParam(value = "fax", required = false) String fax,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "userLevel", required = false) String userLevel,
            @RequestParam(value = "loginClientType", required = false) String loginClientType,
            @RequestParam(value = "userUrl", required = false) String userUrl,
            @RequestParam(value = "systemFlag", required = false) String systemFlag,
            @RequestParam(value = "createTime", required = false) Date createTime,
            HttpServletRequest request
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            List<User> usersToDelete = userService.queryUsers(userIds, null, null, null, null, null, null, null, null
                    , null, null, null, null, null, null, null, null, null, null, null, null, null, null);
            userService.deleteUsers(userIds, userName, password, usergroupId, userType, employeeId, trueName,
                    mobilePhone, email, phone, address, userState, errLoginTimes, updateTime, description, adminUser,
                    fax, gender, userLevel, loginClientType, userUrl, systemFlag, createTime);
            Map<String,String> map = this.getLogingUserInfo(request);
            if (map.size() != 0){
                logService.multiInsertLog("delete", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), usersToDelete, "0",null);
            }
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<User> queryUsers(
            @RequestParam(value = "userIds", required = false) String userIds,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "usergroupId", required = false) String usergroupId,
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "employeeId", required = false) String employeeId,
            @RequestParam(value = "trueName", required = false) String trueName,
            @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "userState", required = false) String userState,
            @RequestParam(value = "errLoginTimes", required = false) Integer errLoginTimes,
            @RequestParam(value = "updateTime", required = false) Date updateTime,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "adminUser", required = false) String adminUser,
            @RequestParam(value = "fax", required = false) String fax,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "userLevel", required = false) String userLevel,
            @RequestParam(value = "loginClientType", required = false) String loginClientType,
            @RequestParam(value = "userUrl", required = false) String userUrl,
            @RequestParam(value = "systemFlag", required = false) String systemFlag,
            @RequestParam(value = "createTime", required = false) Date createTime
    ) {
        ResultBean<User> ret = new ResultBean<>();
        try {
            List<User> users = userService.queryUsers(userIds, userName, password, usergroupId, userType, employeeId, trueName,
                    mobilePhone, email, phone, address, userState, errLoginTimes, updateTime, description, adminUser,
                    fax, gender, userLevel, loginClientType, userUrl, systemFlag, createTime);
            ret.addData(users);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value = "/userRelatedModule", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Module> queryRelatedModules(
            @RequestParam(value = "userId", required = true) String userId
    ) {
        ResultBean<Module> ret = new ResultBean<>();
        try {
            List<Module> modules = userService.queryRelatedModules(userId);
            ret.addData(modules);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value = "/userRole", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addUserRoles(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "roleIds", required = true) String roleIds,
            HttpServletRequest request
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            userService.addUserRoles(userId, roleIds);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
            Map<String,String> map = this.getLogingUserInfo(request);
            if (map.size() != 0){
                User user = userService.queryUserById(userId).get(0);
                List<Role> roles = roleService.queryRoles(roleIds,null,null,null);
                String rolesName = "";
                for(Role role : roles){
                    rolesName+=role.getRoleName();
                }
                logService.insertLog("update", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "0",null);
            }
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;

    }

    @RequestMapping(value = "/userRole", method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> removeUserRoles(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "roleIds", required = false) String roleIds,
            HttpServletRequest request
    ) {
        ResultBean<String> ret = new ResultBean<>();

        try {
            userService.removeUserRoles(userId, roleIds);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
            Map<String,String> map = this.getLogingUserInfo(request);
            if (map.size() != 0){
                User user = userService.queryUserById(userId).get(0);
                List<Role> roles = roleService.queryRoles(roleIds,null,null,null);
                String rolesName = "";
                for(Role role : roles){
                    rolesName+=role.getRoleName();
                }
                logService.insertLog("update", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "0","删除角色:"+rolesName);
            }
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value = "/userRole", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Role> queryUserRoles(
            @RequestParam(value = "userId", required = true) String userId
    ) {
        ResultBean<Role> ret = new ResultBean<>();
        try {
            List<Role> roles = userService.queryUserRoles(userId);
            ret.addData(roles);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> changePassword(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "oldPassword", required = false) String oldPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword,
            @RequestParam(value = "type", required = true) String type,
            HttpServletRequest request
    ) {
        ResultBean<String> ret = new ResultBean<>();

        try {
            if (userName == null || userName.isEmpty()) {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark(String.format("用户名不能为空！"));
                return ret;
            }

            if (newPassword == null || newPassword.length()<6){
                ret.setResult(ResultBean.FAILED);
                ret.setRemark(String.format("密码不能小于6位"));
                return ret;
            }

            List<String> str = new ArrayList<>();
            if ("reset".equals(type.toLowerCase())) {
                userService.changePassword(userName, newPassword);
            } else if ("change".equals(type.toLowerCase())) {
                List<User> users = userService.queryUserByName(userName);
                if (users != null && users.size() > 0 && users.get(0) != null) {
                    if (oldPassword == null || oldPassword.isEmpty()) {
                        ret.setResult(ResultBean.FAILED);
                        ret.setRemark(String.format("旧密码不正确"));
                        return ret;
                    }

                    if (!users.get(0).getPassword().toLowerCase().equals(oldPassword.toLowerCase())) {
                        ret.setResult(ResultBean.FAILED);
                        ret.setRemark(String.format("旧密码不正确"));
                        return ret;
                    }

                    userService.changePassword(userName, newPassword);
                } else {
                    ret.setResult(ResultBean.FAILED);
                    ret.setRemark(String.format("%s is not exist!", userName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
        }
        Map<String,String> map = this.getLogingUserInfo(request);
        if (map.size() != 0){
            User user = userService.queryUserByName(userName).get(0);
            logService.insertLog("update", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), user.getUserId(), userName, "0","修改密码");
        }

        return ret;
    }

    public Map<String,String> getLogingUserInfo(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            String token = request.getHeader("Authorization");
            if (token != null & !token.isEmpty() ) {
                String userId = redisService.getUserId(token);
                map.put("userId", userId);
                map.put("userName", "");
                List<User> users = userService.queryUserById(userId);
                if (users.size() > 0) {
                    map.put("userName",  users.get(0).getUserName());
                }
            }else{
                LogUtil.info("User operation without authorization!");
            }
        }
        catch(Exception ex){
            LogUtil.exception(ex);
        }

        return map;
    }

    private static boolean isSpecialChar(String str) {
        String regEx = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？%+_]|\n|\r|\t|\\s";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
}
