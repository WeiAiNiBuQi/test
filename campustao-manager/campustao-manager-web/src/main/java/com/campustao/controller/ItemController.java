package com.campustao.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campustao.common.pojo.CampustaoResult;
import com.campustao.common.pojo.DataResult;
import com.campustao.pojo.TbItem;
import com.campustao.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public Map<String, Object> getItemById(@PathVariable Long itemId)
	{
		Map<String, Object> result = itemService.getItemById(itemId);
		return result;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public DataResult getItemList(Integer page, Integer rows) {
		DataResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping(value="/item/save",method= RequestMethod.POST)
	@ResponseBody
	public CampustaoResult createItem(TbItem item, String desc) throws Exception
	{
		CampustaoResult result = itemService.createItem(item,desc);
		return result;
	}
	
	@RequestMapping("/item/my_publish")
	@ResponseBody
	public DataResult getItemList(Long uid,Integer page, Integer rows) {
		DataResult result = itemService.getItemByUid(uid, page, rows);
		return result;
	}
	
	
	@RequestMapping("/item/search")
	@ResponseBody
	public DataResult getItemByKeyword(String keyword, int page, int rows) {
		try {
			String value= new String(keyword.getBytes("iso8859-1"),"UTF-8");;
			DataResult result = itemService.getItemByKeyword(value, page, rows);
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/item/category")
	@ResponseBody
	public DataResult getItemByCid(long cid, int page, int rows) 
	{
		DataResult result = itemService.getItemByCid(cid, page, rows);
		return result;
	}
	
	@RequestMapping("/item/delete/{itemId}")
	@ResponseBody
	public CampustaoResult deleteItem(@PathVariable Long itemId)  
	{
		CampustaoResult result = itemService.deleteItem(itemId);
		return result;
	}
	
	@RequestMapping(value="/item/update",method= RequestMethod.POST)
	@ResponseBody
	public CampustaoResult updateItem(TbItem item, String desc) throws Exception
	{
		CampustaoResult result = itemService.updateItem(item,desc);
		return result;
	}
	
}
