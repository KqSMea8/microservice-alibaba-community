package com.znv.controller;

import com.znv.bean.ResultBean;
import com.znv.service.PrivilegeModuleService;
import com.znv.utils.LogUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/privilegeModule")
public class PrivilegeModuleServiceController {

    @Resource
    PrivilegeModuleService privilegeModuleService;

    /*@GetMapping("/insertPrivilegeModuleLink")
    @ResponseBody
    public ResultBean<String> insertPrivilegeModuleLink(
            @RequestParam(value = "privilegeId") String privilegeId,
            @RequestParam(value = "moduleId") String moduleId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            privilegeModuleService.insertPrivilegeModuleLink(privilegeId,moduleId);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }*/

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertPrivilegeModulesLink(
            @RequestParam(value = "privilegeId") String privilegeId,
            @RequestParam(value = "moduleIds") String moduleIds
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            privilegeModuleService.insertPrivilegeModulesLink(privilegeId,moduleIds);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    /*@GetMapping("/deletePrivilegeModuleLink")
    @ResponseBody
    public ResultBean<String> deletePrivilegeModuleLink(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "privilegeId", required = false) String privilegeId,
            @RequestParam(value = "moduleId", required = false) String moduleId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            privilegeModuleService.deletePrivilegeModuleLink(id,privilegeId,moduleId);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }*/

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deletePrivilegeModuleLinks(
            @RequestParam(value = "ids", required = false) String ids,
            @RequestParam(value = "privilegeId", required = false) String privilegeId,
            @RequestParam(value = "moduleIds", required = false) String moduleIds
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            privilegeModuleService.deletePrivilegeModuleLinks(ids,privilegeId,moduleIds);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
            return ret;
        }
        return ret;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updatePrivilegeModuleLink(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "privilegeId", required = false) String privilegeId,
            @RequestParam(value = "moduleId", required = false) String moduleId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            privilegeModuleService.updatePrivilegeModuleLink(id,privilegeId,moduleId);
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
    public ResultBean<Map<String, Object>> queryPrivilegeModuleLinks(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "privilegeId", required = false) String privilegeId,
            @RequestParam(value = "moduleId", required = false) String moduleId
    ) {
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        try {
            List<Map<String, Object>> maps = privilegeModuleService.queryPrivilegeModuleLinks(id,privilegeId,moduleId);
            ret.addData(maps);
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
