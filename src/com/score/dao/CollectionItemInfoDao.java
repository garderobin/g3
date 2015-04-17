package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.CollectionItemInfo;

public class CollectionItemInfoDao extends HibernateDaoSupport 
{
	public CollectionItemInfo save(CollectionItemInfo collectionItemInfo)
	{
		return (CollectionItemInfo)getSession().save(collectionItemInfo);
	}
	
	public void merge(CollectionItemInfo collectionItemInfo)
	{
		getSession().merge(collectionItemInfo);
	}
	
	//Use when load item-fill.jsp
	public List<CollectionItemInfo> queryByInfoProvider(Long infoProvider)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemInfo AS i WHERE i.infoProvider = :infoProvider ORDER BY i.collectionItem");
		q.setLong("infoProvider", infoProvider);
		@SuppressWarnings("unchecked")
		List<CollectionItemInfo> list = (List<CollectionItemInfo>) q.list();
		return list;
	}
	
	//Use when load item-check-one.jsp
	public List<CollectionItemInfo> queryByItemId(Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemInfo AS i WHERE i.collectionItem = :itemId ORDER BY i.infoProvider");
		q.setLong("itemId", itemId);
		@SuppressWarnings("unchecked")
		List<CollectionItemInfo> list = (List<CollectionItemInfo>) q.list();
		for (CollectionItemInfo info : list)
			info.getImages().size();
		return list;
	}
	
	//Use when setInfo
	public CollectionItemInfo queryByInfoProviderAndItemId(Long infoProvider, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemInfo AS i WHERE i.collectionItem = :itemId AND i.infoProvider = :infoProvider");
		q.setLong("itemId", itemId);
		q.setLong("infoProvider", infoProvider);
		return (CollectionItemInfo) q.uniqueResult();
	}
	
	//Use when setImages
	public CollectionItemInfo queryByInfoWithImageProviderAndItemId(Long infoProvider, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemInfo AS i WHERE i.collectionItem = :itemId AND i.infoProvider = :infoProvider");
		q.setLong("itemId", itemId);
		q.setLong("infoProvider", infoProvider);
		return (CollectionItemInfo) q.uniqueResult();
	}
}
