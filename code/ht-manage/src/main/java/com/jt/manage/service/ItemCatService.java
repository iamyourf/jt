package com.jt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr.Item;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.pojo.ItemCatResult;
import com.jt.manage.util.ItemCatUtil;

@Service	
public class ItemCatService {
	@Autowired
	private ItemCatMapper itemCatMapper;
	//使用商品分类的mapper对象调用方法获取parent_id对应的下级分类list
	public List queryItemList(Long id) {
		//利用id查询下级list
		//参数是pojo对象,通用mapper会根据非空属性拼接where条件
		//where parent_id=#{id}
		ItemCat itemCat=new ItemCat();//如果使用空对象,调用select方法
		//selectALl()
		itemCat.setParentId(id);
		List<ItemCat> itemCatList=itemCatMapper.select(itemCat);//parent_id=id
		return itemCatList;
	}
	public ItemCatResult queryAllItemCat() {
		//查询所有商品分类的list
		List<ItemCat> list=itemCatMapper.selectAll();
		ItemCatResult result=ItemCatUtil.allItemCat(list);
		return result; 
	}

}
