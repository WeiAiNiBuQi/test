package com.campustao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campustao.common.pojo.CampustaoResult;
import com.campustao.common.pojo.EUTreeNode;
import com.campustao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value= "id",defaultValue="0")Long parentId) 
	{
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public CampustaoResult createContentCategory(Long parentId, String name) {
		CampustaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}

}
