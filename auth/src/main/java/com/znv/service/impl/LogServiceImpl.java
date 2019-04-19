package com.znv.service.impl;

import com.znv.bean.User;
import com.znv.dao.mapper.LogServiceMapper;
import com.znv.service.LogService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    LogServiceMapper logServiceMapper;

    @Override
    public List<Map<String, Object>> queryLog(String id,
                                              String operator,
                                              String operUserId,
                                              String operUserName,
                                              String ip,
                                              Date time,
                                              String operatedUserId,
                                              String operatedUserName,
                                              String result,
                                              String detail) {
        return logServiceMapper.queryLog(id, operator,operUserId,operUserName,ip,time,operatedUserId,operatedUserName,result,detail);
    }

    @Override
    public void insertLog(String operator,
                          String operUserId,
                          String operUserName,
                          String ip,
                          Date time,
                          String operatedUserId,
                          String operatedUserName,
                          String result,
                          String detail) {
        logServiceMapper.insertLog(operator,operUserId,operUserName,ip,time,operatedUserId,operatedUserName,result,detail);
    }

    public void multiInsertLog(
            String operator,
            String operUserId,
            String operUserName,
            String ip,
            Date time,
            List<User> users,
            String result,
            String detail
    ) {
        logServiceMapper.multiInsertLog(operator,operUserId,operUserName,ip,time,users,result,detail);
    }

}
