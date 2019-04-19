package com.znv.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.collections.MappingChange;
import com.znv.bean.LoginUser;
import com.znv.bean.Module;
import com.znv.bean.ResultBean;
import com.znv.bean.User;
import com.znv.configuration.LoginConfiguration;
import com.znv.redis.RedisService;
import com.znv.service.LogService;
import com.znv.service.UserService;
import com.znv.utils.CusAccessUtil;
import com.znv.utils.LogUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class LoginController {


    @Resource
    UserService userService;

    @Resource
    RedisService redisService;

	@Resource
	LogService logService;

	@Resource
	LoginConfiguration loginConfiguration;

    /**
     * login
     *
     * @param user
     * @param response
     * @return loginSuccess if logined, if failed return string with loginFailed
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public ResultBean<Map<String,String>> login(@RequestBody LoginUser user, HttpServletRequest request, HttpServletResponse response) {
        ResultBean<Map<String,String>> ret = new ResultBean<>();

        //用户不为空，用户名也不为空
        if (user == null || StringUtils.isEmpty(user.getUserName())
                || StringUtils.isEmpty(user.getPassword())) {
            ret.setResult(ResultBean.FAILED);
            //ret.setRemark("loginFailed:user info can't be null");
            ret.setRemark("登录失败:用户信息不能为空");
            return ret;
        }

        List<User> users = userService.queryUserByName(user.getUserName());

		if (users != null && users.size()>0) {
			User userTmp = users.get(0);
			if(userTmp.getErrLoginTimes() != null && userTmp.getErrLoginTimes()>=5){
				ret.setResult(ResultBean.FAILED);
				ret.setRemark("登录失败:用户已锁定");
				logService.insertLog("login",userTmp.getUserId(),userTmp.getUserName(),CusAccessUtil.getIpAddress(request),new Date(),null,null,"1",null);
				return ret;
			}
			if (userTmp.getPassword().equals(user.getPassword())) {
				//登录成功了就清零错误次数,用户状态改成0
				userService.updateUser(userTmp.getUserId(),null, null, null, null, null ,null ,null
						,null ,null ,null ,null,0,null ,null ,null ,
						null ,null ,null ,null ,null ,null );
				String token = user.creatToken();

				// 判断用户是否已经登录，如果已经登录，则踢出上次登录的用户
				Set<String> lastLoginTokenSet = redisService.validateUserLoginState(userTmp.getUserId());
				LogUtil.info(String.format("lastLoginToken is %s", lastLoginTokenSet));

				//if (!userTmp.getUserName().equals("admin") && lastLoginTokenSet.size() >= 0)去掉admin用户可以多次登录
				if (lastLoginTokenSet.size() >= 0) {
					if(!loginConfiguration.isEnableLoginAtSameTime()){//lvjingxu add at 20180712
						for (String key : lastLoginTokenSet) {
							redisService.logoutByTocken(key);
							LogUtil.info(String.format("logout success: lastLoginToken is %s", key));
						}
					}
				}

				response.addHeader("token",token);

				redisService.rememberUserByTocken(token,userTmp.getUserId());
				//admin用户不超时
				if (loginConfiguration.getPersistTokenUsers().contains(userTmp.getUserName())) {
					redisService.persistToken(token);
				}
				//将用户权限放入redis
				List<Module> modules = userService.queryRelatedModules(userTmp.getUserId());
				Set<String> urls = new HashSet<>();
				for (Module module: modules) {
					urls.add(module.getModuleUrl());
				}
				redisService.setUserModuleUrlsForcely(userTmp.getUserId(), urls);
				List<Map<String,String>> retList = new ArrayList<Map<String,String>>();
				Map<String,String> retMap=new HashMap<String,String>();
				retMap.put("userId",userTmp.getUserId());
				retMap.put("userName",userTmp.getUserName());
				retMap.put("token",token);
				retList.add(retMap);
				ret.setResult(ResultBean.SUCESS);
				ret.setRemark("登录成功");
				ret.setData(retList);
				logService.insertLog("login",userTmp.getUserId(),userTmp.getUserName(), CusAccessUtil.getIpAddress(request),new Date(),null,null,"0",null);
				return ret;
			}
			else{
				ret.setResult(ResultBean.FAILED);
				int count=userTmp.getErrLoginTimes()+1;
				userService.updateUser(userTmp.getUserId(),null, null, null, null, null ,null ,null
						,null ,null ,null ,null,count,null ,null ,null ,
						null ,null ,null ,null ,null ,null );
				//密码错误5次
				if(count==5){
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						public void run() {
							//20分钟清零失败次数
							userService.updateUser(userTmp.getUserId(),null, null, null, null, null ,null ,null
									,null ,null ,null ,null,0,null ,null ,null ,
									null ,null ,null ,null ,null ,null );
						}
					}, 1000 * 60 * 20);
					ret.setRemark("登录失败:用户锁定，密码错误超过5次，请20分钟后再试");
					logService.insertLog("login",userTmp.getUserId(),userTmp.getUserName(),CusAccessUtil.getIpAddress(request),new Date(),null,null,"1",null);
				}else{
					ret.setRemark("登录失败:用户密码错误");
					logService.insertLog("login",userTmp.getUserId(),userTmp.getUserName(),CusAccessUtil.getIpAddress(request),new Date(),null,null,"1",null);

				}
				return ret;
			}
		}
		ret.setResult(ResultBean.FAILED);
		ret.setRemark("登录失败:用户不存在");
		return ret;
	}


    /**
     * logout
     *
     * @param request
     * @return return string logoutSuccess,just remove session user data
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET,
            RequestMethod.POST})
    public ResultBean<Object> logout(HttpServletRequest request) {
        ResultBean<Object> ret = new ResultBean<>();
        String token = request.getHeader("Authorization");
        if (redisService.hasTocken(token)) {
            redisService.logoutByTocken(token);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("退出成功");
			String userId = redisService.getUserId(token);
			List<User> users = userService.queryUsers(userId,null, null, null, null, null ,null ,null
					,null ,null ,null ,null,null,null ,null ,null ,
					null ,null ,null ,null ,null ,null ,null);
			if (users != null && users.size()>0) {
				User userTmp = users.get(0);
				logService.insertLog("logout",userTmp.getUserId(),userTmp.getUserName(),CusAccessUtil.getIpAddress(request),new Date(),null,null,"0",null);
			}
			} else {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("用户未登录");
        }
        return ret;
    }

    /**
     * 第三方登录ZNVR平台
     *
     * @param user
     * @return loginSuccess if logined, if failed return string with loginFailed
     */
    @RequestMapping(value = "v1/Login", method = RequestMethod.POST, consumes = "application/json")
    public String v1Login(@RequestBody LoginUser user) {
        JSONObject result = new JSONObject();
        result.put("result", "FAILT");
        result.put("resultCode", "401");
        result.put("token", "");

        if (user == null || StringUtils.isEmpty(user.getUserName())
                || StringUtils.isEmpty(user.getPassword())) {
            return result.toJSONString();
        }

        if (user.getUserName().equals("alertuser")
                && user.getPassword().equals("alert20180320")) {
//			TokenExpireTask task = (TokenExpireTask) ApplicationContextHelper
//					.getBean(TokenExpireTask.class);
            String tokenId = UUID.randomUUID().toString();
//			task.addToken(tokenId);
            result.put("result", "SUCCESSED");
            result.put("resultCode", "0");
            result.put("token", tokenId);
            result.put("Expire", 7200);
        }

        return result.toJSONString();
    }
}
