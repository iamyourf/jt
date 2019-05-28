package com.jt.cart.mapper;

import com.jt.cart.pojo.Cart;
import com.jt.common.mapper.MyMapper;

public interface CartMapper extends MyMapper<Cart>{

	void updateNum(Cart cart);

}
