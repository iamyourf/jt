package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	//用户注册数据提交
	@RequestMapping("service/user/doRegister")
	@ResponseBody
	public SysResult doRegister(User user){
		try{
			userService.doRegister(user);
			return SysResult.oK(user.getUsername());
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, e.getMessage());
		}
	}
	//登录功能
	@RequestMapping("service/user/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String ticket=userService.doLogin(user);
		//判断是否有登录结果;一旦失败,ticket为空
		if("".equals(ticket)||"null".equals(ticket)||ticket==null){
			//登录失败 ajax提交,返回失败信息,201
			return SysResult.build(201, "失败");
		}else{
			//登录成功
			//将ticket放到cookie中返回SysResult对象
			CookieUtils.setCookie(request, response, 
					"JT_TICKET", ticket);
			return SysResult.oK();
		}
			
		}
	}































