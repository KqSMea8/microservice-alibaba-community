package com.znv.controller;

import com.znv.bean.ResultBean;
import com.znv.bean.User;
import com.znv.redis.RedisService;
import com.znv.service.UserService;
import com.znv.utils.LogUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangye on 2018/5/22.
 */
@RestController
@RequestMapping("/verifyAuthority")
public class VerifyAuthority {
    @Resource
    RedisService redisService;
    @Resource
    UserService userService;
    /**
     * verify if you have authority
     *
     * @param path
     * @param request
     * @return
     */
    @GetMapping("/verifyModuleAuth")
    public String verifyModuleAuth(@RequestParam(value = "path", required = false) String path, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return redisService.validateAuthAndUpdateExpireTime(token,path);
    }

    /**
     * query all authority by session
     *
     * @param request
     * @return
     */
    @GetMapping("/queryModuleAuth")
    public ResultBean<String> queryModuleAuth(HttpServletRequest request) {
        ResultBean<String> ret = new ResultBean<>();
        String token = request.getHeader("Authorization");
        String userId = redisService.getUserId(token);
        if (userId != null) {
            try {
                List<String> patterns = redisService.queryModuleAuth(userId);
                ret.addData(patterns);
                ret.setResult(ResultBean.SUCESS);
            } catch (Exception e) {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark(e.getMessage());
                LogUtil.error(e.toString());
            }
        }else{
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("没有登录");
        }
        return ret;
    }

    /**
     * refresh valid time
     *
     * @param request
     * @return
     */
    @GetMapping("/refreshExpireTime")
    public boolean refreshExpireTime(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return redisService.refreshExpireTime(token);
    }

    /**
     * verify token
     *
     * @param token
     * @return
     */
    @GetMapping("/verifyToken")
    public ResultBean<String> verifyToken(@RequestParam(value = "token", required = true) String token) {
        String userId = redisService.getUserId(token);
        ResultBean<String> ret = new ResultBean<>();
        if (userId != null) {
            redisService.refreshExpireTime(token);
            ret.setResult(ResultBean.SUCESS);
        }else{
            ret.setResult(ResultBean.FAILED);
        }
        return ret;
    }
    /**
     * verify token
     *
     * @param request
     * @return
     */
    @GetMapping("/username")
    public ResultBean<String> getUsernameByToken(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        String userId = redisService.getUserId(token);
        ResultBean<String> ret = new ResultBean<>();
        if (userId != null) {
            List<User> users = userService.queryUsers(userId,null, null, null, null, null ,null ,null
                    ,null ,null ,null ,null,null,null ,null ,null ,
                    null ,null ,null ,null ,null ,null ,null);
            String username = users.get(0).getUserName();
            ret.setResult(ResultBean.SUCESS);
            List<String> usernameList = new ArrayList<String>();
            usernameList.add(username);
            ret.addData(usernameList);
        }else{
            ret.setResult(ResultBean.FAILED);
        }
        return ret;
    }
}
