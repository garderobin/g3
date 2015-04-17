package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.CollectionItem;
import com.score.bean.SysUserOut;

public class CollectionItemDao extends HibernateDaoSupport 
{
	public void save(CollectionItem item)
	{
		getSession().save(item);
	}
	
	public void merge(CollectionItem item)
	{
		getSession().merge(item);
	}
	
	public void deleteById(Long itemId)
	{
		CollectionItem item = queryItemById(itemId);
		if (item != null)
			delete(item);
	}
	
	public void delete(CollectionItem item)
	{
		getSession().delete(item);
	}
	
	public CollectionItem queryItemById(Long id)
	{
		Query q = getSession().createQuery("SELECT item FROM CollectionItem AS item WHERE item.id = :id");
		q.setLong("id", id);
		CollectionItem item = (CollectionItem) q.uniqueResult();
		return item;
	}
	
	public CollectionItem queryItemWithCheckOperatorsById(Long userId)
	{
		Query q = getSession().createQuery("SELECT item FROM CollectionItem AS item WHERE item.id = :userId");
		q.setLong("userId", userId);
		CollectionItem item = (CollectionItem)q.uniqueResult();
		getCheckOperators(item);
		return item;
	}
	
	public List<CollectionItem> queryItemByCheckOperatorId(Long userId)
	{
		Query q = getSession().createQuery("SELECT item FROM CollectionItem AS item WHERE :userId IN (FROM item.checkOperator) ORDER BY item.id");
		q.setLong("userId", userId);
		@SuppressWarnings("unchecked")
		List<CollectionItem> items = q.list();
		return items;
	}
	
	public List<CollectionItem> queryItemAll()
	{
		Query q = getSession().createQuery("SELECT item FROM CollectionItem AS item ORDER BY item.id");
		@SuppressWarnings("unchecked")
		List<CollectionItem> items = q.list();
		return items;
	}
	
	public List<CollectionItem> queryItemWhichNeedCheck()
	{
		Query q = getSession().createQuery("SELECT item FROM CollectionItem AS item WHERE item.ifCheck = 1 ORDER BY item.id");
		@SuppressWarnings("unchecked")
		List<CollectionItem> items = q.list();
		return items;
	}
	
	public List<CollectionItem> queryItemWhichShallBePublicized()
	{
		Query q = getSession().createQuery("SELECT item FROM CollectionItem AS item WHERE item.publicityType >= 1 ORDER BY item.id");
		@SuppressWarnings("unchecked")
		List<CollectionItem> items = q.list();
		return items;
	}
	
	private void getCheckOperators(CollectionItem item)
	{
		List<SysUserOut> users = item.getCheckOperator();
		if (users.size() > 0)users.get(0).getId();
	}
}
