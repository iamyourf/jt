package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;

@Service
public class CartService {
	@Autowired
	private CartMapper cartMapper;
	public List<Cart> queryCarts(Long userId) {
		//select * from tb_cart where user_id=#{userId}
		Cart _cart=new Cart();
		_cart.setUserId(userId);
		List<Cart> cartList=cartMapper.select(_cart);
		return cartList;
	}
	public void saveCart(Cart cart) {
		//判断是否存在,如果存在,如果不存在
		Cart _cart=new Cart();
		_cart.setUserId(cart.getUserId());
		_cart.setItemId(cart.getItemId());
		Cart exists=cartMapper.selectOne(_cart);
		if(exists!=null){//新增
			//久数据+新数据
			Cart newCart=new Cart();//id,num,updated
			newCart.setId(exists.getId());
			newCart.setUpdated(new Date());
			newCart.setNum(exists.getNum()+cart.getNum());
			cartMapper.updateByPrimaryKeySelective(newCart);
		}else{//已经存在了
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}
		
	}
	public void updateCart(Long userId, Long itemId, Integer num) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cart.setNum(num);
		cartMapper.updateNum(cart);
	}
	
	

}
