package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@RestController//Controller ResponseBody
public class CartController {
	@Autowired
	private CartService cartService;
	//查询我的购物车,userId
	@RequestMapping("cart/query/{userId}")
	public SysResult queryCarts(@PathVariable Long userId){
		List<Cart> cartList=cartService.queryCarts(userId);
		return SysResult.oK(cartList);
	}
	
	@RequestMapping("cart/save")
	public SysResult saveCart(Cart cart){
		try{
			cartService.saveCart(cart);
			return SysResult.oK();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "新增购物车失败");
		}
	}
	//更新商品数量
	@RequestMapping("cart/update/num/{userId}/{itemId}/{num}")
	public SysResult updateNumCart(@PathVariable Long userId,
			@PathVariable Long itemId,
			@PathVariable Integer num ){
		try{
			cartService.updateCart(userId,itemId,num);
			return SysResult.oK();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "新增购物车失败");
		}	
	}
}















