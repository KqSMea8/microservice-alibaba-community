package com.znv.bean;


import lombok.Data;

/**
 * 模块
 */
@Data
public class Module {
    private String id;
    private String moduleName;
    private String moduleUrl;
    private String description;
    //暂时不用分层，字段可为空
    private String upModuleId;
}
