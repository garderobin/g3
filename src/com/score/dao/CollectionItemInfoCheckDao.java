package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.CollectionItemInfoCheck;

public class CollectionItemInfoCheckDao extends HibernateDaoSupport 
{
	public void save(CollectionItemInfoCheck collectionItemInfoCheck)
	{
		getSession().save(collectionItemInfoCheck);
	}
	
	public void merge(CollectionItemInfoCheck collectionItemInfoCheck)
	{
		getSession().merge(collectionItemInfoCheck);
	}

	//Use when load item-check-one.jsp
	public List<CollectionItemInfoCheck> queryByInfoProviderAndItemId(Long infoProvider, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemInfoCheck AS i WHERE i.collectionItem = :itemId AND i.infoProvider = :infoProvider ORDER BY i.infoTarget");
		q.setLong("itemId", itemId);
		q.setLong("infoProvider", infoProvider);
		@SuppressWarnings("unchecked")
		List<CollectionItemInfoCheck> list = (List<CollectionItemInfoCheck>) q.list();
		return list;
	}
	
	//Use when setInfo
	public CollectionItemInfoCheck queryByInfoProviderAndInfoTargetAndItemId(Long infoProvider, Long infoTarget, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemInfoCheck AS i WHERE i.collectionItem = :itemId AND i.infoTarget = :infoTarget AND i.infoProvider = :infoProvider");
		q.setLong("itemId", itemId);
		q.setLong("infoTarget", infoTarget);
		q.setLong("infoProvider", infoProvider);
		return (CollectionItemInfoCheck) q.uniqueResult();
	}
}
