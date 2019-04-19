package com.znv.task;

import com.znv.utils.LogUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

//@Component
public class TokenExpireTask {

	/**
	 * 每隔5秒检查token是否过期
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0/5 * *  * * ? ")
	public void run() {
		LogUtil.info("task execution");
	}
}
