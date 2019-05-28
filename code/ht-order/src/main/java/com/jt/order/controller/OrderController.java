package com.jt.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.util.ObjectUtil;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	//新增订单数据 Order
	@RequestMapping("order/create")
	public String saveOrder(@RequestBody String orderJson) throws Exception{
		//将json转化成order对象
		Order order=ObjectUtil.mapper.readValue(orderJson, Order.class);
		//业务层生成orderId,返回
		String orderId=orderService.saveOrder(order);
		return orderId;
	}
	//查询订单,更具订单id
	@RequestMapping("order/query/{orderId}")
	public Order queryOrderById(@PathVariable String orderId){
		Order order=orderService.queryOrder(orderId);
		return order;
	}
}
