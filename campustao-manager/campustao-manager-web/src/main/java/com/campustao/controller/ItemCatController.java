package com.campustao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campustao.common.pojo.EUTreeNode;
import com.campustao.service.ItemCatService;

/**
 * 商品分类管理Controller
 *
 */
@Controller
@RequestMapping("/item/cat")//简化请求路径
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("list")
	@ResponseBody//默认返回第一级目录
	public List<EUTreeNode> getCatList(@RequestParam(value="id",defaultValue="0")long parentId)
	{
		List<EUTreeNode> list = itemCatService.getCatList(parentId);
		return list;
	}
}
