package com.znv.controller;

import com.znv.bean.ResultBean;
import com.znv.service.PrivilegeModuleService;
import com.znv.service.PrivilegeService;
import com.znv.utils.LogUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/privilege")
public class PrivilegeServiceController {

    @Resource
    PrivilegeService privilegeService;
    @Resource
    PrivilegeModuleService privilegeModuleService;

    /**
     *
     * @param privilegeName
     * @param privilegeType
     * @param description
     * @param upPrivilegeId
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertPrivilege(
            @RequestParam(value = "privilegeName") String privilegeName,
            @RequestParam(value = "privilegeType", required = false) String privilegeType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upPrivilegeId", required = false) String upPrivilegeId,
            @RequestParam(value = "moduleIds", required = false) String moduleIds
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            String id = privilegeService.insertPrivilege(
                    privilegeName, privilegeType, description, upPrivilegeId
            );
            if (moduleIds!=null && id!=null && !id.isEmpty()) {
                privilegeModuleService.insertPrivilegeModulesLink(id, moduleIds);
            }
            ret.addData(Arrays.asList(id));
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
    public ResultBean<String> deletePrivileges(
            @RequestParam(value = "privilegeIds") String privilegeIds,
            @RequestParam(value = "privilegeName", required = false) String privilegeName,
            @RequestParam(value = "privilegeType", required = false) String privilegeType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upPrivilegeId", required = false) String upPrivilegeId
    ){
        ResultBean<String> ret = new ResultBean<>();
        try {
            privilegeService.deletePrivileges(
                    privilegeIds,privilegeName,privilegeType,description,upPrivilegeId
            );
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
    public ResultBean<String> updatePrivilege(
            @RequestParam(value = "privilegeId", required = true) String privilegeId,
            @RequestParam(value = "privilegeName", required = true) String privilegeName,
            @RequestParam(value = "privilegeType", required = false) String privilegeType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upPrivilegeId", required = false) String upPrivilegeId
    ){
        ResultBean<String> ret = new ResultBean<>();
        try {
            privilegeService.updatePrivilege(
                    privilegeId,privilegeName,privilegeType,description,upPrivilegeId
            );
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
    public ResultBean<Map<String, Object>> queryPrivileges(
            @RequestParam(value = "privilegeId", required = false) String privilegeId,
            @RequestParam(value = "privilegeName", required = false) String privilegeName,
            @RequestParam(value = "privilegeType", required = false) String privilegeType,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upPrivilegeId", required = false) String upPrivilegeId
    ){
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        try {
            List<Map<String, Object>> maps = privilegeService.queryPrivileges(
                    privilegeId, privilegeName, privilegeType, description, upPrivilegeId
            );
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
