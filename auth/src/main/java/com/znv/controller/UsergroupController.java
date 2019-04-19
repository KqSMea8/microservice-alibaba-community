package com.znv.controller;

import com.znv.bean.ResultBean;
import com.znv.bean.Role;
import com.znv.bean.Usergroup;
import com.znv.service.UsergroupService;
import com.znv.utils.LogUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
public class UsergroupController {

    @Resource
    UsergroupService usergroupService;

    @RequestMapping(value="/usergroup",method = RequestMethod.POST)
    public ResultBean<String> insertUsergroup(
            @RequestParam(value = "usergroupName", required = true) String usergroupName,
            @RequestParam(value = "usergroupType", required = false) String usergroupType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upUsergroupId", required = false) String upUsergroupId,
            @RequestParam(value = "roleIds", required = false) String roleIds
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try{
            Usergroup usergroup = new Usergroup(null, usergroupName,usergroupType,description,
                    upUsergroupId);
            usergroupService.insertUsergroup(usergroup);
            usergroupService.addUsergroupRoles(usergroup.getUsergroupId(), roleIds);
            ret.addData(Arrays.asList(usergroup.getUsergroupId()));
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value="/usergroup",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deleteUsergroups(
            @RequestParam(value = "usergroupIds", required = false) String usergroupIds,
            @RequestParam(value = "usergroupName", required = false) String usergroupName,
            @RequestParam(value = "usergroupType", required = false) String usergroupType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upUsergroupId", required = false) String upUsergroupId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try{
            usergroupService.deleteUsergroups(usergroupIds, usergroupName, usergroupType, description, upUsergroupId);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value="/usergroup",method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updateUsergroup(
            @RequestParam(value = "usergroupId", required = true) String usergroupId,
            @RequestParam(value = "usergroupName", required = false) String usergroupName,
            @RequestParam(value = "usergroupType", required = false) String usergroupType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upUsergroupId", required = false) String upUsergroupId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try{
            usergroupService.updateUsergroup(usergroupId, usergroupName, usergroupType, description, upUsergroupId);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value="/usergroup",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Usergroup> queryUsergroups(
            @RequestParam(value = "usergroupIds", required = false) String usergroupIds,
            @RequestParam(value = "usergroupName", required = false) String usergroupName,
            @RequestParam(value = "usergroupType", required = false) String usergroupType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upUsergroupId", required = false) String upUsergroupId
    ) {
        ResultBean<Usergroup> ret = new ResultBean<>();
        try{
            List<Usergroup> usergroups = usergroupService.queryUsergroups(usergroupIds, usergroupName, usergroupType, description, upUsergroupId);
            ret.addData(usergroups);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value="/usergroupRole",method=RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> addUsergroupRoles(
            @RequestParam(value = "usergroupId", required = true) String usergroupId,
            @RequestParam(value = "roleIds", required = true) String roleIds
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            usergroupService.addUsergroupRoles(usergroupId, roleIds);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value="/usergroupRole",method=RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deleteUsergroupRoles(
            @RequestParam(value = "ids", required = false) String ids,
            @RequestParam(value = "usergroupId", required = false) String usergroupId,
            @RequestParam(value = "roleIds", required = false) String roleIds
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            usergroupService.deleteUsergroupRoles(ids, usergroupId, roleIds);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value="/usergroupRole",method=RequestMethod.GET)
    @ResponseBody
    public ResultBean<Role> queryUsergroupRoles(
            @RequestParam(value = "usergroupId", required = false) String usergroupId
    ) {
        ResultBean<Role> ret = new ResultBean<>();
        try {
            List<Role> roles = usergroupService.queryUsergroupRoles(usergroupId);
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
