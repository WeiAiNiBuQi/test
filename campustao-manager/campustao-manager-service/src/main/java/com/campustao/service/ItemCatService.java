package com.campustao.service;

import java.util.List;

import com.campustao.common.pojo.EUTreeNode;

public interface ItemCatService {

	List<EUTreeNode> getCatList(long parentId);
}
