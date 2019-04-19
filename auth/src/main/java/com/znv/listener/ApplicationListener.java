package com.znv.listener;

import com.znv.utils.LogUtil;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
public class ApplicationListener implements ServletContextListener {

	/**
	 * 日志对象
	 */
	//private static Logger logger = LoggerFactory.getLogger(ApplicationListener.class);

	/**
	 * 
	 * Title: contextDestroyed
	 * 
	 * Description: 监听销毁执行方法
	 * 
	 * @param arg0
	 *            arg0
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LogUtil.info("ServletContextListener.contextDestroyed.......");
	}

	/**
	 * 
	 * Title: contextInitialized
	 * 
	 * Description: 监听初始化方法
	 * 
	 * @param arg0
	 *            arg0
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LogUtil.info("ServletContextListener.contextInitialized.......");
	}
}
