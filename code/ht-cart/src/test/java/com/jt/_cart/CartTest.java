package com.jt._cart;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class CartTest {
	
		@Test
		public void test() throws UnsupportedEncodingException{
			String con="鍊间細琚洿鏂�";
			byte[] bytes = con.getBytes("gbk");
			System.out.println(new String(bytes,"utf-8"));
			
		}
}
