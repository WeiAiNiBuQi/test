package com.campustao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campustao.common.pojo.EUTreeNode;
import com.campustao.mapper.TbItemCatMapper;
import com.campustao.mapper.TbItemMapper;
import com.campustao.pojo.TbItemCat;
import com.campustao.pojo.TbItemCatExample;
import com.campustao.pojo.TbItemCatExample.Criteria;
import com.campustao.service.ItemCatService;
/**
 * 商品分类管理
 */

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EUTreeNode> getCatList(long parentId) {
		
		//创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//根据条件查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		//把列表转化成EUTreeNodelist
		for(TbItemCat tbItemCat : list)
		{
			EUTreeNode node = new EUTreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		//返回结果
		return resultList;
	}

}
