package com.campustao.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campustao.common.pojo.CampustaoResult;
import com.campustao.mapper.TbContentMapper;
import com.campustao.pojo.TbContent;
import com.campustao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public CampustaoResult insertContent(TbContent content) {
		//补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		return CampustaoResult.ok();
	}

}
