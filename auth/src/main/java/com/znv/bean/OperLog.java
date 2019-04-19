package com.znv.bean;


import lombok.Data;

/**
 * 操作日志
 */
@Data
public class OperLog {
    private String id;
    private String operator;
    private String operUserId;
    private String operUserName;
    private String ip;
    private String time;
    private String operatedUserId;
    private String operatedUserName;
    private String result;
    private String detail;
}
