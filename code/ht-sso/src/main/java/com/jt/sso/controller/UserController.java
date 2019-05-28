package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jt.common.util.ObjectUtil;
import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	//数据的教研,username phone是否合法
	//1,2
	@RequestMapping("user/check/{param}/{type}")
	@ResponseBody
	public String checkParam(@PathVariable String param,
			@PathVariable Integer type,
			String callback) throws Exception{
		SysResult result=userService.checkParam(param,type);
		//拼接jsonp格式
		return callback+"("+ObjectUtil.mapper.
				writeValueAsString(result)+")";
	}
	//用户注册
	@RequestMapping("/user/register")
	@ResponseBody
	public SysResult insertUser(User user){
		try{
			userService.insertUser(user);
			return SysResult.oK(user.getUsername());
		}catch(Exception e ){
			return SysResult.build(201, e.getMessage());
		}
	}
	
	//用户登录
	@RequestMapping("user/login")
	@ResponseBody
	public SysResult doLogin(@RequestParam("u") String username,String p) throws Exception{
		//业务层user信息保存到redis的key值ticket
		String ticket=userService.doLogin(username,p);
		return SysResult.oK(ticket);//登录失败,返回空字符串的ticket
	}
	
	//利用ticket验证登录过程
	@RequestMapping("user/query/{ticket}")
	@ResponseBody
	public String queryUserByTicket(@PathVariable String ticket,
			String callback) throws JsonProcessingException{
		String userJson=userService.queryUserJson(ticket);
		if(callback==null){//代码发起的请求,不需要jsonp
		return	ObjectUtil.mapper.
				writeValueAsString(SysResult.oK(userJson));
		}
		return callback+"("+ObjectUtil.mapper.
				writeValueAsString(SysResult.oK(userJson))+")";
	}
}


































