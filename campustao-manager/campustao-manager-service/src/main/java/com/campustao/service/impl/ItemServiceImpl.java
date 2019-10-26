package com.campustao.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campustao.common.pojo.CampustaoResult;
import com.campustao.common.pojo.DataResult;
import com.campustao.common.utils.IDUtils;
import com.campustao.mapper.TbItemDescMapper;
import com.campustao.mapper.TbItemMapper;
import com.campustao.mapper.TbUserMapper;
import com.campustao.pojo.TbItem;
import com.campustao.pojo.TbItemDesc;
import com.campustao.pojo.TbItemDescExample;
import com.campustao.pojo.TbItemExample;
import com.campustao.pojo.TbItemExample.Criteria;
import com.campustao.pojo.TbUser;
import com.campustao.pojo.TbUserExample;
import com.campustao.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public Map<String, Object> getItemById(long itemId) {
//		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		Map<String, Object> result = new HashMap(); 
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		
		List<TbItem> list =itemMapper.selectByExample(example);
		if(list!=null && list.size()>0)
		{
			
			TbItem tbItem = list.get(0);
			
			/*TbItemDescExample exampleDesc = new TbItemDescExample();
			com.campustao.pojo.TbItemDescExample.Criteria CriteriaDesc = exampleDesc.createCriteria();
			CriteriaDesc.andItemIdEqualTo(itemId);
			TbItemDesc itemDesc = itemDescMapper.selectByExample(exampleDesc).get(0);*/
			TbItemDesc itemDesc= itemDescMapper.selectByPrimaryKey(itemId);
			TbUser user = userMapper.selectByPrimaryKey(tbItem.getUid());
			
			result.put("item", tbItem);
			result.put("desc", itemDesc);
			result.put("user", user);
			return result;
		}
		return null;
	}
	
	@Override
	public DataResult getItemList(int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo((byte)1);//搜索未下架的商品
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		DataResult result = new DataResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public CampustaoResult createItem(TbItem item, String desc) throws Exception {
		//把item补全
		//生成商品ID
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到数据库
		itemMapper.insert(item);
		//添加商品描述信息
		CampustaoResult result = insertItemDesc(itemId,desc);
		if(result.getStatus() != 200)
		{//事务交给Spring来管理，在这里不要捕获异常，抛异常后Spring会自动回滚，
		//没抛异常Spring会认为事务正常，会正常提交事务，从而导致错误	
			throw new Exception();
		}
		return CampustaoResult.ok();
	}

	/**
	 * 添加商品描述
	 * @param desc
	 * @param itemId 
	 */
	private CampustaoResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);;
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return CampustaoResult.ok();
	}

	/**
	 * 修改商品描述
	 * @param desc
	 * @param itemId 
	 */
	private CampustaoResult updateItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);//这里使用updateByPrimaryKey()
		return CampustaoResult.ok();
	}
	

	/**
	 * 获取我的发布
	 */
	@Override
	public DataResult getItemByUid(long uid, int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid).andStatusEqualTo((byte)1);
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		DataResult result = new DataResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	
	/*
	 * 下架商品
	 * */
	@Override
	public CampustaoResult deleteItem(long itemId) {
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		item.setStatus((byte)2);
		itemMapper.updateByPrimaryKey(item);
		return CampustaoResult.ok();
	}

	/**
	 * 搜索
	 */
	@Override
	public DataResult getItemByKeyword(String keyword, int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andTitleLike("%"+keyword+"%").andStatusEqualTo((byte)1);
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		DataResult result = new DataResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	
	@Override
	public DataResult getItemByCid(long cid, int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andCidEqualTo(cid).andStatusEqualTo((byte)1);
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		DataResult result = new DataResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public CampustaoResult updateItem(TbItem item, String desc) throws Exception {
		long itemId = item.getId();
		TbItem oldItem = itemMapper.selectByPrimaryKey(itemId);
		String title = item.getTitle();
		long cid = item.getCid();
		String image = item.getImage();
		Long price = item.getPrice();
		String sellPoint = item.getSellPoint();
		if(title != null)
		{
			oldItem.setTitle(title);
		}
		if(cid != 0)
		{
			oldItem.setCid(cid);
		}
		if(image != null)
		{
			oldItem.setImage(image);
		}
		if(price!=0)
		{
			oldItem.setPrice(price);
		}
		if(sellPoint!=null)
		{
			oldItem.setSellPoint(sellPoint);
		}
		oldItem.setUpdated(new Date());
		//插入到数据库
		itemMapper.updateByPrimaryKey(oldItem);
		//添加商品描述信息
		CampustaoResult result = updateItemDesc(itemId,desc);
		if(result.getStatus() != 200)
		{//事务交给Spring来管理，在这里不要捕获异常，抛异常后Spring会自动回滚，
		//没抛异常Spring会认为事务正常，会正常提交事务，从而导致错误	
			throw new Exception();
		}
		return CampustaoResult.ok();
	}
	
}
