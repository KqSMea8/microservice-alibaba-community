package com.znv.configuration;

import com.znv.filters.LoginFilter;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * 过滤器配置
 *
 * @author wr
 * @date 2018-3-7
 */
// 注释@Configuration代表屏蔽登录验证
@Configuration
@Import(LoginFilter.class)
public class FilterConfigration {
    @Resource
    private LoginFilter loginFilter;

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean<LoginFilter> filterRegistrationBean() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<LoginFilter>();
        bean.setFilter(loginFilter);
        bean.addUrlPatterns("/*");
        bean.setName("loginFilter");
        bean.setOrder(1);
        return bean;
    }
}
