package com.znv.controller;

import com.znv.bean.ResultBean;
import com.znv.bean.Role;
import com.znv.service.RolePrivilegeService;
import com.znv.service.RoleService;
import com.znv.utils.LogUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleServiceController {

    @Resource
    RoleService roleService;

    @Resource
    RolePrivilegeService rolePrivilegeService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertRole(
            @RequestParam(value = "roleName", required = true) String roleName,
            @RequestParam(value = "upRoleId", required = false) String upRoleId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "privilegeIds", required = false) String privilegeIds
    ) {

        ResultBean<String> ret = new ResultBean<>();
        try {
            Role role = new Role(null, roleName, upRoleId, description);
            roleService.insertRole(role);
            rolePrivilegeService.insertRolePrivileges(role.getRoleId(), privilegeIds);
            ret.addData(Arrays.asList(role.getRoleId()));
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;


    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deleteRoles(
            @RequestParam(value = "roleIds", required = false) String roleIds,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "upRoleId", required = false) String upRoleId,
            @RequestParam(value = "description", required = false) String description
    ){
        ResultBean<String> ret = new ResultBean<>();
        try {
            roleService.deleteRoles(roleIds, roleName, upRoleId, description);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updateRole(
            @RequestParam(value = "roleId", required = true) String roleId,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "upRoleId", required = false) String upRoleId,
            @RequestParam(value = "description", required = false) String description
    ){
        ResultBean<String> ret = new ResultBean<>();
        try {
            roleService.updateRole(roleId, roleName, upRoleId, description);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Role> queryRoles(
            @RequestParam(value = "roleIds", required = false) String roleIds,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "upRoleId", required = false) String upRoleId,
            @RequestParam(value = "description", required = false) String description
    ){
        ResultBean<Role> ret = new ResultBean<>();
        try {
            List<Role> roles = roleService.queryRoles(roleIds, roleName, upRoleId, description);
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
}
