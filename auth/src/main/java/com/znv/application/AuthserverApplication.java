package com.znv.application;

import com.znv.utils.LogUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/***
 * ComponentScan指定包之后，SpringBoot会自动描该包下面的功能，如Controller
 * EnableAutoConfiguration 使用自动配置
 * EnableScheduling 开启定时任务
 */
@SpringBootApplication
@ComponentScan("com.znv")
@EnableScheduling
@EnableDiscoveryClient
public class AuthserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
		LogUtil.info("AuthserverApplication is Running");
	}
}
