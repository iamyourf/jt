package com.jt.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jt.web.interceptors.CartInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{
	
	//构造框架管理的拦截器实现类 CartInterception
	@Bean
	public CartInterceptor getCartInt(){
		return new CartInterceptor();
	}
	//父类的方法addInterceptor重写
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		//registry注册器,实现自定义的拦截器功能
		registry.addInterceptor(getCartInt()).addPathPatterns("/cart/**");
		super.addInterceptors(registry);
	}
}
