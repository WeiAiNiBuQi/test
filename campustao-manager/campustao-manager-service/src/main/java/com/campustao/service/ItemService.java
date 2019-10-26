package com.campustao.service;

import java.util.Map;

import com.campustao.common.pojo.CampustaoResult;
import com.campustao.common.pojo.DataResult;
import com.campustao.pojo.TbItem;

public interface ItemService {

	Map<String, Object> getItemById(long itemId);
	DataResult getItemList(int page, int rows); 
	CampustaoResult createItem(TbItem item, String desc)throws Exception;
	DataResult getItemByUid(long uid,int page, int rows);
	CampustaoResult deleteItem(long itemId);
	DataResult getItemByKeyword(String keyword,int page, int rows);
	DataResult getItemByCid(long cid,int page, int rows);
	CampustaoResult updateItem(TbItem item, String desc)throws Exception;
}
