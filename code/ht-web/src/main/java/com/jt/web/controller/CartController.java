package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;

@Controller
public class CartController {
	@Autowired
	private CartService cartService;
		//查询我的购物车
	@RequestMapping("cart/show")
	public String myCart(Model model,HttpServletRequest request) throws Exception{
		//写死一个userId
		/*Long userId=7l;*/
		Long userId=(long)request.getAttribute("userId");
		List<Cart> cartList=cartService.queryCarts(userId);
		model.addAttribute("cartList", cartList);
		return "cart";//WEB-INF/views/cart.jsp
	}
	/*@RequestMapping("cart/asdfa")
	public String shadf(){
		return "asdf";
	}*/
	//新增商品到购物车
	@RequestMapping("cart/add/{itemId}")
	public String addCart(HttpServletRequest request,
			Integer num,
			@PathVariable Long itemId) throws Exception{
		Long userId=(long)request.getAttribute("userId");
		cartService.addCart(num,itemId,userId);
		return "redirect:/cart/show.html";
	}
	//修改商品数量
	@RequestMapping("cart/update/num/{itemId}/{num}")
	public String updateCart(HttpServletRequest request,
			@PathVariable Long itemId,
			@PathVariable Integer num
			) throws Exception{
		Long userId=(long)request.getAttribute("userId");
		cartService.updateCart(num,itemId,userId);
		return "redirect:/cart/show.html";
	}
}
