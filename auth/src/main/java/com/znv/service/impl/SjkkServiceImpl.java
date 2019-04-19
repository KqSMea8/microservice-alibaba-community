package com.znv.service.impl;

import com.znv.bean.SjkkBean;
import com.znv.dao.mapper.SjkkServiceMapper;
import com.znv.service.SjkkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SjkkServiceImpl implements SjkkService {

	@Resource
	private SjkkServiceMapper sjkkServiceMapper;

	@Override
	public int queryBaseCount() {
		return sjkkServiceMapper.queryBaseCount();
	}

	@Override
	public List<SjkkBean> queryList() {
		return sjkkServiceMapper.queryList();
	}
}
