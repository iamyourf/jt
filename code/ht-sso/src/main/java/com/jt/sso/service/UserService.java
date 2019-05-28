package com.jt.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.redis.RedisService;
import com.jt.common.util.ObjectUtil;
import com.jt.common.vo.SysResult;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	public SysResult checkParam(String param, Integer type) {
		//逻辑判断,type==1,查username type==2 查phone
		User _user=new User();
		int count=0;
		if(type==1){//where username拼接
			_user.setUsername(param);
			count=userMapper.selectCount(_user);
		}else{
			_user.setPhone(param);
			count=userMapper.selectCount(_user);
		}
		return count==0? SysResult.oK(false):SysResult.oK(true);
	}
	public void insertUser(User user) {
		//由于前台没有传递email,保证数据插入的有效
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		user.setEmail(user.getUsername());
		//password加密
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		userMapper.insertSelective(user);
	}
	@Autowired
	private RedisService redis;
	public String doLogin(String username, String password) throws Exception {
		//利用username 和password来查询user where username and password
		//判断查询结果,如果user==null 说明登录失败,return ""
		//user!=null 登录成功,生成ticket,存redis,将ticket返回
		User _user=new User();
		_user.setUsername(username);
		_user.setPassword(DigestUtils.md5Hex(password));
		User user = userMapper.selectOne(_user);
		if(user!=null){//登录校验成功
			//将user信息json存储redis,避免sesson共享问题
			String ticket=DigestUtils.
		md5Hex("JT_TICKET_"+System.currentTimeMillis()+username);
			String userJson=ObjectUtil.mapper.writeValueAsString(user);
			redis.set(ticket, userJson);
			return ticket;
		}
		//登录失败返回空字符串
		return "";//如果返回null,前台接到的是SysResult的json,ticket "null"
	}
	public String queryUserJson(String ticket) {
		
		return redis.get(ticket);
	}

}
