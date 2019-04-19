package com.znv.controller;

import com.znv.bean.ResultBean;
import com.znv.service.ModuleService;
import com.znv.utils.LogUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/module")
public class ModuleController {

    @Resource
    ModuleService moduleService;

    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<Map<String, Object>> queryModules(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "moduleName", required = false) String moduleName,
            @RequestParam(value = "moduleUrl", required = false) String moduleUrl,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upModuleId", required = false) String upModuleId
    ) {
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        try {
            List<Map<String, Object>> maps = moduleService.queryModules(id, moduleName, moduleUrl, description, upModuleId);
            ret.addData(maps);
            ret.setResult(ResultBean.SUCESS);
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<String> insertModule(
            @RequestParam(value = "moduleName", required = true) String moduleName,
            @RequestParam(value = "moduleUrl", required = false) String moduleUrl,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upModuleId", required = false) String upModuleId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            moduleService.insertModule(moduleName, moduleUrl, description, upModuleId);
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
    public ResultBean<String> updateModule(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "moduleName", required = true) String moduleName,
            @RequestParam(value = "moduleUrl", required = false) String moduleUrl,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upModuleId", required = false) String upModuleId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            moduleService.updateModule(id, moduleName, moduleUrl, description, upModuleId);
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
    public ResultBean<String> deleteModule(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "moduleName", required = false) String moduleName,
            @RequestParam(value = "moduleUrl", required = false) String moduleUrl,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "upModuleId", required = false) String upModuleId
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            moduleService.deleteModule(id, moduleName, moduleUrl, description, upModuleId);
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
