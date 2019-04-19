package com.znv.filters;

import com.znv.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 登录过滤器
 * 
 * @author wr
 * @date 2018.3.7
 */

public class LoginFilter implements Filter {
	private static final Set<String> ALLOW_PATHS = Collections
			.unmodifiableSet(new HashSet<>(Arrays.asList("", "/login",
					"/logout", "/register", "/debug", "/v1/Login","/verifyAuthority/verifyToken")));

	private static final String regixStr = ".*/websocket.*";

	// 第三方登录路径匹配
	private static final String regixStr3rd = "/v1/.*";

	private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	@Resource
	RedisService redisService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * 过滤，如果例外路径可以通过，如果不是例外则验证是否已经登录，如果没有登录，则返回错误
	 * 
	 * @param servletRequest
	 *            请求
	 * @param servletResponse
	 *            回复
	 * @param filterChain
	 *            过滤器链
	 * @throws IOException
	 *             向前端发送错误失败
	 * @throws ServletException
	 *             过滤失败
	 */
	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();

		String path = request.getRequestURI()
				.substring(request.getContextPath().length())
				.replaceAll("[/]+$", "");
		logger.debug(request.getContextPath());
		logger.debug(request.getRequestURI());
		String token = request.getHeader("Authorization");
		boolean authed = validateTocken(token, servletRequest);
		boolean allowPath = ALLOW_PATHS.contains(path);
		boolean matches = Pattern.matches(regixStr, path);
		// 第三方登录
		boolean matches3rd = Pattern.matches(regixStr3rd, path);


		if (matches3rd) {// 第三方平台登录
			if (allowPath) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
//			TokenExpireTask task = (TokenExpireTask) ApplicationContextHelper
//					.getBean(TokenExpireTask.class);
			String tokenId = request.getHeader("AccessToken");
			filterChain.doFilter(servletRequest, servletResponse);
			return;
//			if (tokenId != null && task.isValidToken(tokenId)) {
//				filterChain.doFilter(servletRequest, servletResponse);
//				return;
//			}
		} else if (allowPath || matches || authed) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Please Login First.");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	public boolean validateTocken(String token, ServletRequest servletRequest) {
		if (token == null) {
			return false;
		}
		String userId = redisService.getUserId(token);
		if (userId != null) {
			redisService.refreshExpireTime(token);
			return true;
		}else{
			return false;
		}
		/*
		String authRet = redisService.validateAuthAndUpdateExpireTime(token, ((HttpServletRequest) servletRequest).getRequestURI());
		boolean authResult = ((authRet!=null) && (authRet.equals("success")));
		return authResult;*/
	}

	@Override
	public void destroy() {

	}

	public static void main(String[] args) {
		boolean matches = Pattern.matches(regixStr, "123/websocket/123");
		System.out.println(matches);
	}
}
