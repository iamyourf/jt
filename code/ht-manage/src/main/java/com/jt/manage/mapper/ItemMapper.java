package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.MyMapper;
import com.jt.manage.pojo.Item;

public interface ItemMapper extends MyMapper<Item>{

	List<Item> queryItemList(@Param("start")int start,@Param("rows")Integer rows);

}
