package com.campustao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campustao.common.pojo.CampustaoResult;
import com.campustao.common.pojo.EUTreeNode;
import com.campustao.mapper.TbContentCategoryMapper;
import com.campustao.pojo.TbContentCategory;
import com.campustao.pojo.TbContentCategoryExample;
import com.campustao.pojo.TbContentCategoryExample.Criteria;
import com.campustao.pojo.TbContentExample;
import com.campustao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		//根据parentId查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			//创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
		
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public CampustaoResult insertContentCategory(long parentId, String name) {
		
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//'状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录,因为修改了mapper，所以会自动返回contentCategory的主键
		contentCategoryMapper.insert(contentCategory);
		//查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		return CampustaoResult.ok(contentCategory);
	}
	
}
