package com.znv.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleService {
    public List<Map<String, Object>> queryModules(
            String id,
            String moduleName,
            String moduleUrl,
            String description,
            String upModuleId
    );

    public void insertModule(
            String moduleName,
            String moduleUrl,
            String description,
            String upModuleId
    );

    public void updateModule(
            String id,
            String moduleName,
            String moduleUrl,
            String description,
            String upModuleId
    );

    public void deleteModule(
            String id,
            String moduleName,
            String moduleUrl,
            String description,
            String upModuleId
    );
}
