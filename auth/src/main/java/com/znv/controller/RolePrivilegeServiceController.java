package com.znv.controller;

import com.znv.bean.ResultBean;
import com.znv.service.RolePrivilegeService;
import com.znv.utils.LogUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rolePrivilege")
public class RolePrivilegeServiceController {

    @Resource
    RolePrivilegeService rolePrivilegeService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertRolePrivileges(
            @RequestParam(value = "roleId", required = true) String roleId,
            @RequestParam(value = "privilegeIds", required = true) String privilegeIds
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            rolePrivilegeService.insertRolePrivileges(roleId, privilegeIds);
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
    public ResultBean<String> deleteRolePrivileges(
            @RequestParam(value = "ids", required = false) String ids,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "privilegeIds", required = false) String privilegeIds
    ) {

        ResultBean<String> ret = new ResultBean<>();
        try {
            rolePrivilegeService.deleteRolePrivileges(ids, roleId, privilegeIds);
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
    public ResultBean<String> updateRolePrivilege(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "privilegeId", required = false) String privilegeId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            rolePrivilegeService.updateRolePrivilege(id, roleId, privilegeId);
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
    public ResultBean<Map<String, Object>> queryRolePrivileges(
            @RequestParam(value = "ids", required = false) String ids,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "privilegeId", required = false) String privilegeId
    ) {
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        try {
            List<Map<String, Object>> datas = rolePrivilegeService.queryRolePrivileges(ids, roleId, privilegeId);
            ret.addData(datas);
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
