package com.znv.service.impl;

import com.znv.dao.mapper.ModuleServiceMapper;
import com.znv.service.ModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Resource
    ModuleServiceMapper moduleServiceMapper;

    @Override
    public List<Map<String, Object>> queryModules(String id, String moduleName, String moduleUrl, String description, String upModuleId) {
        return moduleServiceMapper.queryModules(id,moduleName,moduleUrl,description,upModuleId);
    }

    @Override
    public void insertModule(String moduleName, String moduleUrl, String description, String upModuleId) {
        moduleServiceMapper.insertModule(moduleName,moduleUrl,description,upModuleId);
    }

    @Override
    public void updateModule(String id, String moduleName, String moduleUrl, String description, String upModuleId) {
        moduleServiceMapper.updateModule(id,moduleName,moduleUrl,description,upModuleId);
    }

    @Override
    public void deleteModule(String id, String moduleName, String moduleUrl, String description, String upModuleId) {
        moduleServiceMapper.deleteModule(id,moduleName,moduleUrl,description,upModuleId);
    }
}
