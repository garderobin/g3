package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.PublicHearingsItem;

public class PublicHearingsItemDao extends HibernateDaoSupport 
{
	public void save(PublicHearingsItem item)
	{
		getSession().save(item);
	}
	
	public void merge(PublicHearingsItem item)
	{
		getSession().merge(item);
	}
	
	public void deleteById(Long itemId)
	{
		PublicHearingsItem item = queryItemById(itemId);
		if (item != null)
			delete(item);
	}
	
	public void delete(PublicHearingsItem item)
	{
		getSession().delete(item);
	}
	
	public PublicHearingsItem queryItemById(Long id)
	{
		Query q = getSession().createQuery("SELECT item FROM PublicHearingsItem AS item WHERE item.id = :id");
		q.setLong("id", id);
		PublicHearingsItem item = (PublicHearingsItem) q.uniqueResult();
		return item;
	}
	
	public List<PublicHearingsItem> queryItemAll()
	{
		Query q = getSession().createQuery("SELECT item FROM PublicHearingsItem AS item ORDER BY item.id");
		@SuppressWarnings("unchecked")
		List<PublicHearingsItem> items = q.list();
		return items;
	}
	
	public List<PublicHearingsItem> queryItemWhichShallBePublicized()
	{
		Query q = getSession().createQuery("SELECT item FROM PublicHearingsItem AS item WHERE item.type = 0 ORDER BY item.id");
		@SuppressWarnings("unchecked")
		List<PublicHearingsItem> items = q.list();
		return items;
	}
	
	public List<PublicHearingsItem> queryItemTextWhichShallBePublicized()
	{
		Query q = getSession().createQuery("SELECT item FROM PublicHearingsItem AS item WHERE item.type = 1 ORDER BY item.id");
		@SuppressWarnings("unchecked")
		List<PublicHearingsItem> items = q.list();
		return items;
	}
}
