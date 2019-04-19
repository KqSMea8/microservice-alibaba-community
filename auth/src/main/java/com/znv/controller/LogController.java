package com.znv.controller;

import com.znv.bean.ResultBean;
import com.znv.service.LogService;
import com.znv.utils.DateUtil;
import com.znv.utils.LogUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/log")
public class LogController {

    @Resource
    LogService logService;

    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<Map<String, Object>> queryModules(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "operator", required = false) String operator,
            @RequestParam(value = "operUserId", required = false) String operUserId,
            @RequestParam(value = "operUserName", required = false) String operUserName,
            @RequestParam(value = "ip", required = false)  String ip,
            @RequestParam(value = "time", required = false) String time,
            @RequestParam(value = "time", required = false) String operatedUserId,
            @RequestParam(value = "operatedUserName", required = false) String operatedUserName,
            @RequestParam(value = "result", required = false) String result,
            @RequestParam(value = "detail", required = false) String detail
    ) {
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        try {
            List<Map<String, Object>> maps = logService.queryLog(id,operator, operUserId, operUserName, ip, DateUtil.strToDateLong(time),operatedUserId, operatedUserName,result,detail);
            ret.addData(maps);
            ret.setResult(ResultBean.SUCESS);
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            LogUtil.error(e.toString());
        }
        return ret;
    }
}
