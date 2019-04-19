package com.znv.controller;

import com.znv.bean.SjkkBean;
import com.znv.service.SjkkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sjkk")
public class TestController {

	@Resource
	private SjkkService serverService;

	@RequestMapping(value = "/queryCount", method = RequestMethod.GET)
	public int queryPeoPleRelations() {
		return serverService.queryBaseCount();
	}

	@RequestMapping(value = "/queryList", method = RequestMethod.GET)
	public List<SjkkBean> queryList() {
		return serverService.queryList();
	}

	@RequestMapping(value = "/getdevice", method = RequestMethod.GET)
	public void getDevice3(
			@RequestParam(value = "kind", required = true) String kindstr,
			@RequestParam(value = "kind1", required = false) String kindstr1) {
		System.err.println(kindstr1 == null);

	}

}