package com.campustao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campustao.common.pojo.CampustaoResult;
import com.campustao.pojo.TbContent;
import com.campustao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	public CampustaoResult insertContent(TbContent content) {
		CampustaoResult result = contentService.insertContent(content);
		return result;
	}

}
