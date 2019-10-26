package com.campustao.service;

import java.util.List;

import com.campustao.common.pojo.CampustaoResult;
import com.campustao.common.pojo.EUTreeNode;

public interface ContentCategoryService {
	
	List<EUTreeNode> getCategoryList(long parentId);
	CampustaoResult insertContentCategory(long parentId,String name);
}
