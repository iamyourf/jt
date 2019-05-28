package com.jt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.HttpClientService;
import com.jt.common.util.ObjectUtil;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Item;
@Service
public class CartService {
	
	@Autowired
	private HttpClientService client;
	
	//查询我的购物车
	public List<Cart> queryCarts(Long userId) throws Exception{
		String url="http://cart.jt.com/cart/query/"+userId;
		String jsonData = client.doGet(url);
		//从数据中解析出data的list<cart>数据
		SysResult result=ObjectUtil.mapper.readValue(jsonData, SysResult.class);
		List<Cart> cartList=(List<Cart>) result.getData();
		return cartList;
	}

	public void addCart(Integer num, Long itemId, Long userId) throws Exception {
		//http请求,请求后台,获取title price image
		String url="http://manage.jt.com/items/"+itemId;
		String itemJson = client.doGet(url);
		Item item=ObjectUtil.mapper.readValue(itemJson, Item.class);
		url="http://cart.jt.com/cart/save";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("itemId", itemId);
		map.put("num", num);
		map.put("itemTitle", item.getTitle());
		map.put("itemPrice", item.getPrice());
		map.put("itemImage", item.getImage());
		client.doGet(url, map);
	}

	public void updateCart(Integer num, Long itemId, Long userId) throws Exception {
		String url="http://cart.jt.com/cart/update/num/"
				+userId+"/"+itemId+"/"+num;
		client.doGet(url);
		
	}
}
