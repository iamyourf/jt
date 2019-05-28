package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.HttpClientService;
import com.jt.common.util.ObjectUtil;
import com.jt.common.vo.HttpResult;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserService {
	@Autowired
	private HttpClientService client;
	public void doRegister(User user) throws Exception {
		String url="http://sso.jt.com/user/register";
		//调用的doPost(url,map)
		//username=haha&password=124&phone=185,添加到请求体中传递
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("username", user.getUsername());
		map.put("password", user.getPassword());
		map.put("phone", user.getPhone());
		map.put("email", user.getUsername());
		
		client.doPost(url, map);
	}
	public String doLogin(User user) throws Exception {
		String url="http://sso.jt.com/user/login";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("u", user.getUsername());
		map.put("p", user.getPassword());
		HttpResult response = client.doPost(url, map);
		String jsonData=response.getBody();//{"status":200,"msg":"ok"
		//"data":"213lhwei23lndsiy23o"}
		//从json中解析data的数据 ticket
		SysResult result=ObjectUtil.mapper.readValue(jsonData, SysResult.class);
		//字符串内容,在mapper传递转化过程,会添加额外的""   "json"
		String ticket=(String)result.getData();
		//如果登录失败有可能是"" "null"
		return ticket;
	}

}
