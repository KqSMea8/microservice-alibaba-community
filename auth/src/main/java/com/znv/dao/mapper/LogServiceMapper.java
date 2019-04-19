package com.znv.dao.mapper;

import com.znv.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LogServiceMapper {
    public List<Map<String, Object>> queryLog(
            @Param(value = "id") String id,
            @Param(value = "operator") String operator,
            @Param(value = "operUserId") String operUserId,
            @Param(value = "operUserName") String operUserName,
            @Param(value = "ip") String ip,
            @Param(value = "time") Date time,
            @Param(value = "operatedUserId") String operatedUserId,
            @Param(value = "operatedUserName") String operatedUserName,
            @Param(value = "result") String result,
            @Param(value = "detail") String detail
    );

    public void insertLog(
            @Param(value = "operator") String operator,
            @Param(value = "operUserId") String operUserId,
            @Param(value = "operUserName") String operUserName,
            @Param(value = "ip") String ip,
            @Param(value = "time") Date time,
            @Param(value = "operatedUserId") String operatedUserId,
            @Param(value = "operatedUserName") String operatedUserName,
            @Param(value = "result") String result,
            @Param(value = "detail") String detail
    );

    public void multiInsertLog(
            @Param(value = "operator") String operator,
            @Param(value = "operUserId") String operUserId,
            @Param(value = "operUserName") String operUserName,
            @Param(value = "ip") String ip,
            @Param(value = "time") Date time,
            @Param(value = "users") List<User> users,
            @Param(value = "result") String result,
            @Param(value = "detail") String detail
    );

}
