package com.znv.service;

import com.znv.bean.SjkkBean;

import java.util.List;

/**
 * @ClassName: ServerService
 * @Description: 基础服务类-服务层接口
 */
public interface SjkkService {

	int queryBaseCount();

	List<SjkkBean> queryList();

}
