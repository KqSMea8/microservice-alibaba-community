package com.znv.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.AutomapConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {
    private String privilegeId;
    private String privilegeName;
    private String privilegeType;
    private String description;
    //权限没有分层，暂时不用
    private String upPrivilegeId;

    //该权限拥有的所有模块集合
    private List<Module> modules = new ArrayList<>();

    public Privilege(String privilegeId, String privilegeName, String privilegeType, String description, String upPrivilegeId) {
        this.privilegeId = privilegeId;
        this.privilegeName = privilegeName;
        this.privilegeType = privilegeType;
        this.description = description;
        this.upPrivilegeId = upPrivilegeId;
    }
}
