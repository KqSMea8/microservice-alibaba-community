package com.znv.community.gate.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.znv.community.gate.feign.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
@RefreshScope
@Slf4j
public class LoginFilter extends ZuulFilter {
	private static final Set<String> ALLOW_PATHS = Collections
			.unmodifiableSet(new HashSet<>(Arrays.asList("", "/login","/auth/login",
					"/logout", "/register", "/debug", "/v1/Login", "/all/exportAllAlarm")));

	private static final String regixStr = ".*/websocket.*";

	// 第三方登录路径匹配
	private static final String regixStr3rd = "/v1/.*";

	@Value("${auth.onoff}")
	boolean isOnOff;

//	@Value("${auth.httpUri}")
//	String authUri;

	@Autowired
	ITokenService iTokenService;

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		if (isOnOff) {
			return true;
		}
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();
		HttpSession session = request.getSession();

		String path = request.getRequestURI()
				.substring(request.getContextPath().length())
				.replaceAll("[/]+$", "");
		log.debug(request.getContextPath());
		log.debug(request.getRequestURI());

		// 内部测试，见DebugController
		boolean isDebug = "admin_debug"
				.equals(session.getAttribute("username"));
		boolean allowPath = ALLOW_PATHS.contains(path);
		boolean matches = Pattern.matches(regixStr, path);
		// 第三方平台登录校验
		boolean matches3rd = Pattern.matches(regixStr3rd, path);

		if (matches3rd) {// 第三方平台,可视化调用接口

			// 解决跨域
			response.setHeader("Access-Control-Allow-Origin",
					request.getHeader("Origin"));
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Methods",
					"POST, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers",
					"x-requested-with");
			ctx.setSendZuulResponse(true);
			return null;
		} else if (allowPath || matches || isDebug) {
			ctx.setSendZuulResponse(true);
			return null;
		} else {
			String token = request.getHeader("Authorization");
			log.debug("Authorization get Authorization:" + token);
			log.debug("request.getRequestURI()"
					+ request.getRequestURI());

			// 给可视化的固定token，持有此token将不走鉴权服务
			if ("12CBD9B708D887A41AFAB97DAC46AAC6".equals(token)) {
				log.debug("可视化token，跳过鉴权");
				return null;
			}
//					String url = authUri + "?token=" + token;
//					JSONObject result = HttprequestHelper.httpRequest(url,
//							"GET", "");
			JSONObject result = iTokenService.verifyToken(token);
			log.debug("Authorization get result" + result);
			// 0表示检验鉴权成功
			if ("0".equals(result.getString("result"))) {
				ctx.setSendZuulResponse(true);
				return null;
			} else {
				try {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
							"Please Login First.");
				} catch (IOException e) {
					e.printStackTrace();
				}
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				ctx.setSendZuulResponse(false);
//					ctx.setResponseStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
//					ctx.setResponseBody("Please Login First.");
				return null;
				}
			}

	}
}
