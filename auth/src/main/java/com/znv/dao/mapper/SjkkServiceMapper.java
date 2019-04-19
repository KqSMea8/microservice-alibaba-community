package com.znv.dao.mapper;


import com.znv.bean.SjkkBean;

import java.util.List;

public interface SjkkServiceMapper {

    /**
     * 查询单表记录总数
     * 
     * @return 总数
     */
    int queryBaseCount();

    List<SjkkBean> queryList();

}
