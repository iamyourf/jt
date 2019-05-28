package com.jt.order.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.order.mapper.OrderMapper;
import com.jt.order.pojo.Order;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;
	public String saveOrder(Order order) {
		//传递的order对象缺少orderId
		String orderId=System.currentTimeMillis()+""+order.getUserId();
		//持久层存入order对象
		order.setOrderId(orderId);
		order.setCreated(new Date());
		order.setUpdated(order.getCreated());
		order.setStatus(1);//默认的订单状态都是未付款
		orderMapper.saveOrder(order);
		return orderId;
	}
	public Order queryOrder(String orderId) {
		
		return orderMapper.queryOrder(orderId);
	}

}
