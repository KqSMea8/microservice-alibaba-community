package com.znv.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleServiceMapper {
    public List<Map<String, Object>> queryModules(
            @Param(value = "id") String id,
            @Param(value = "moduleName") String moduleName,
            @Param(value = "moduleUrl") String moduleUrl,
            @Param(value = "description") String description,
            @Param(value = "upModuleId") String upModuleId
    );

    public void insertModule(
            @Param(value = "moduleName") String moduleName,
            @Param(value = "moduleUrl") String moduleUrl,
            @Param(value = "description") String description,
            @Param(value = "upModuleId") String upModuleId
    );

    public void updateModule(
            @Param(value = "id") String id,
            @Param(value = "moduleName") String moduleName,
            @Param(value = "moduleUrl") String moduleUrl,
            @Param(value = "description") String description,
            @Param(value = "upModuleId") String upModuleId
    );

    public void deleteModule(
            @Param(value = "id") String id,
            @Param(value = "moduleName") String moduleName,
            @Param(value = "moduleUrl") String moduleUrl,
            @Param(value = "description") String description,
            @Param(value = "upModuleId") String upModuleId
    );
}
