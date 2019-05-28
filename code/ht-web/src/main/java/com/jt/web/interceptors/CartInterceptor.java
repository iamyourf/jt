package com.jt.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.common.util.ObjectUtil;
import com.jt.web.pojo.User;

public class CartInterceptor implements HandlerInterceptor{
	@Autowired
	private HttpClientService client;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//实现拦截器判断登录的结果,响应,或者放行
		String ticket=CookieUtils.getCookieValue(request, "JT_TICKET");
		if(StringUtils.isNotEmpty(ticket)){//cookie有数据,曾经登录过
			//发起请求到sso获取redis的数据
			String url="http://sso.jt.com/user/query/"+ticket;
			String jsonData = client.doGet(url);
			//从sysResult的json中获取userJson,转化成user,获取id
			//jackson,中有一个类,JsonNode,解析json字符串的;
			JsonNode jsonNode = ObjectUtil.mapper.readTree(jsonData);
			JsonNode userNode = jsonNode.get("data");
			//userNode就是userJson的jsonNode格式数据,转化成字符串userJson"" null
			String userJson = userNode.asText();
			if(StringUtils.isNotEmpty(userJson)){
				User user=ObjectUtil.mapper.readValue(userJson, User.class);
				//利用拦击器,将userId传递给controller
				Long userId= user.getId();
				request.setAttribute("userId", userId);
				return true;//方法返回true表示放行,后续逻辑继续执行
				//返回false 不放行,拦住了,需要完成相应逻辑
			}
			
		};
		//不能同时满足2个if条件,就是未登录状态
		response.sendRedirect("/user/login");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
