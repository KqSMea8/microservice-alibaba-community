package com.znv.redis;

import com.znv.utils.LogUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用于对外提供redis服务，最好不要依赖项目中的类
 */
@Service
public class RedisService {

    @Value("${server.redis.session.timeout:1800}")
    private Long sessionTimeout;
    //redis中存储了 tockenId:userId作为用户在线的证据，每个tockenId前面加上这个tocken
    private String tockenHeader = "tockenheader:";
    private String userHeader = "userheader:";
    private String resourceType = "modules";

    private TimeUnit timeUnit = TimeUnit.SECONDS;

    private StringRedisTemplate template;
    //header+tocken:userId  可以用来判断是否登录
    ValueOperations<String, String> valueOperations;
    //header+userId:resoucename{module,device}:set{moduleurls}
    //header+userId:modules:url1,url2,url3
    HashOperations<String, String, String> moduleAuthOperations;

    /**
     * 为tocken添加header
     *
     * @param tocken 暂时定为sessionid
     * @return
     */
    public String addTockenHeader(String tocken) {
        return tockenHeader + tocken;
    }

    /**
     * 为userid添加header
     *
     * @param userId
     * @return
     */
    public String addUserHeader(String userId) {
        return userHeader + userId;
    }

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.template = stringRedisTemplate;
        valueOperations = template.opsForValue();
        moduleAuthOperations = template.opsForHash();
    }

    /**
     * Is there the tockenId in redis.
     *
     * @param tockenId tocken
     * @return
     */
    public boolean hasTocken(String tockenId) {
        boolean hasTocken = false;
        try {
            if (valueOperations.get(addTockenHeader(tockenId)) != null)
                hasTocken = true;
        } catch (Exception e) {
            LogUtil.error(e.toString());
        }
        return hasTocken;
    }

    /**
     * 移除 tockenId 的过期时间，tockenId 将持久保持。.
     *
     * @param tockenId tockenId
     * @return
     */
    public boolean persistToken(String tockenId) {
        try {
            return template.persist(addTockenHeader(tockenId));
        } catch (Exception e) {
            LogUtil.error(e.toString());
        }
        return false;
    }

    /**
     * 将session放入redis, 并指定过期时间
     *
     * @param tockenId tocken
     * @param userId   username/userid
     * @param timeout  long
     * @param timeUnit java.util.concurrent.TimeUnit
     * @return
     */
    public boolean rememberUserByTocken(String tockenId, String userId, long timeout, TimeUnit timeUnit) {
        try {
            valueOperations.set(addTockenHeader(tockenId), userId, timeout, timeUnit);
        } catch (Exception e) {
            LogUtil.error(e.toString());
            return false;
        }
        return true;
    }

    /**
     * 使用默认过期时间${server.redis.session.timeout} 单位为秒
     * 将用户信息保存到redis中
     *
     * @param tockenId
     * @param userId
     * @return
     */
    public boolean rememberUserByTocken(String tockenId, String userId) {
        try {
            valueOperations.set(addTockenHeader(tockenId), userId, sessionTimeout, timeUnit);
        } catch (Exception e) {
            LogUtil.error(e.toString());
            return false;
        }
        return true;
    }

    /**
     * 更新会话的过期时间
     *
     * @param tockenId
     * @param timeout
     * @param timeUnit
     * @return
     */
    public boolean refreshExpireTime(String tockenId, long timeout, TimeUnit timeUnit) {
        try {
            template.expire(addTockenHeader(tockenId), timeout, timeUnit);
        } catch (Exception e) {
            LogUtil.error(e.toString());
            return false;
        }
        return true;
    }

    /**
     * 按照过期默认配置刷新过期时间
     *
     * @param tockenId
     * @return
     */
    public boolean refreshExpireTime(String tockenId) {
        try {
            template.expire(addTockenHeader(tockenId), sessionTimeout, timeUnit);
        } catch (Exception e) {
            LogUtil.error(e.toString());
            return false;
        }
        return true;
    }

    public boolean logoutByTocken(String tockenId) {
        try {
            String userId = valueOperations.get(addTockenHeader(tockenId));
            template.delete(addTockenHeader(tockenId));
            if (userId == null) {
                return true;
            }
            //判断是否这个用户通过其他session也登录过，如果没有则删除用户信息
            Set<String> keys = template.keys(tockenHeader + "*");
            boolean hasSameUser = false;
            for (String key : keys) {
                if (userId.equals(valueOperations.get(key))) {
                    hasSameUser = true;
                }
            }
            if (!hasSameUser) {
                template.delete(addUserHeader(userId));
            }
        } catch (Exception e) {
            LogUtil.error(e.toString());
            return false;
        }
        return true;
    }

    public boolean validateloginState(String tockenId) {
        try {
            boolean value = template.hasKey(addTockenHeader(tockenId));
            return value;
        } catch (Exception e) {
            LogUtil.error(e.toString());
            return false;
        }
    }

    /**
     * 验证是否登录，验证是否有权限，刷新超时时间
     *
     * @param tockenId
     * @param path
     * @return
     */
    public String validateAuthAndUpdateExpireTime(String tockenId, String path) {
        boolean refreshResult = refreshExpireTime(tockenId);
        boolean logined = validateloginState(tockenId);
        if (!logined) {
            return "session timeout error";
        }
        if (!refreshResult) {
            return "refresh expire time error";
        }

        if (path == null) {
            return "path can not be null error";
        }
        path = path.replaceAll("\\\\", "/");
        try {
            //获取userid
            String userId = valueOperations.get(addTockenHeader(tockenId));
            if (userId != null) {
                String resoucePatterns = moduleAuthOperations.get(addUserHeader(userId), resourceType);
                if (resoucePatterns != null) {
                    //处理掉最后的","
                    if (resoucePatterns.endsWith(",")) {
                        resoucePatterns = resoucePatterns.substring(0, resoucePatterns.lastIndexOf(","));
                    }
                    String[] patterns = resoucePatterns.split(",");
                    if (patterns == null) {
                        return "authentication failed";
                    }
                    AntPathMatcher antPathMatcher = new AntPathMatcher();
                    for (String pattern : patterns) {
                        if (antPathMatcher.match(pattern, path)) {
                            return "success";
                        }
                    }
                } else {
                    return "authentication failed";
                }
            } else {
                return "login info error, please relogin";
            }
        } catch (Exception e) {
            LogUtil.error(e.toString());
            return e.getMessage();
        }
        return "authentication failed";
    }

    /**
     * query module authority
     *
     * @return
     * @parackenId
     */
    public List<String> queryModuleAuth(String userId) {
        String resoucePatterns = moduleAuthOperations.get(addUserHeader(userId), resourceType);
        List<String> patterns = new ArrayList<String>();
        if (resoucePatterns != null) {
            //处理掉最后的","
            if (resoucePatterns.endsWith(",")) {
                resoucePatterns = resoucePatterns.substring(0, resoucePatterns.lastIndexOf(","));
            }
            patterns = Arrays.asList(resoucePatterns.split(","));
        }
        return patterns;
    }

    /**
     * get userId
     *
     * @param tockenId
     * @return
     */
    public String getUserId(String tockenId) {
        return valueOperations.get(addTockenHeader(tockenId));
    }

    /**
     * 将用户的权限信息添加到redis中, 如果已经有了就不在添加
     *
     * @param userId
     * @param moduleUrls
     */
    public void setUserModuleUrls(String userId, Set<String> moduleUrls) {
        if (!template.hasKey(addUserHeader(userId))) {
            moduleAuthOperations.put(addUserHeader(userId), resourceType, Strings.join(moduleUrls.iterator(), ','));
        }
    }

    /**
     * 强制更新，不管内存里面有没有
     *
     * @param userId
     * @param moduleUrls
     */
    public void setUserModuleUrlsForcely(String userId, Set<String> moduleUrls) {
        moduleAuthOperations.put(addUserHeader(userId), resourceType, Strings.join(moduleUrls.iterator(), ','));
    }

    /**
     * 将用户的权限信息从redis中删除
     *
     * @param userId
     */
    public void clearUserModuleUrls(String userId) {
        template.delete(addUserHeader(userId));
    }

    public Set<String> validateUserLoginState(String userId) {
        Set<String> values = new HashSet<>();

        if (userId != null) {
            try {
                Set<String> keys = template.keys(tockenHeader + "*");

                for (String key : keys) {
                    if (userId.equals(valueOperations.get(key))) {
                        values.add(key.replace(this.tockenHeader, ""));
                    }
                }
                LogUtil.info(String.format("userId is %s, and keys is %s", userId, values));
                return values;
            } catch (Exception e) {
                LogUtil.exception(e);
            }
        }
        return values;
    }

}
