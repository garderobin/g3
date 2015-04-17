package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.CollectionItemScore;

public class CollectionItemScoreDao extends HibernateDaoSupport 
{
	public void save(CollectionItemScore collectionItemScore)
	{
		getSession().save(collectionItemScore);
	}
	
	public void merge(CollectionItemScore collectionItemScore)
	{
		getSession().merge(collectionItemScore);
	}
	
	public List<CollectionItemScore> queryAll()
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemScore AS i JOIN FETCH i.infoTarget ORDER BY i.id");
		@SuppressWarnings("unchecked")
		List<CollectionItemScore> list = (List<CollectionItemScore>) q.list();
		return list;
	}
	
	public List<CollectionItemScore> queryByItemId(Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemScore AS i WHERE i.collectionItem = :itemId ORDER BY i.infoTarget");
		q.setLong("itemId", itemId);
		@SuppressWarnings("unchecked")
		List<CollectionItemScore> list = (List<CollectionItemScore>) q.list();
		return list;
	}
	
	public List<CollectionItemScore> queryByInfoTarget(Long userId)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemScore AS i WHERE i.infoTarget = :userId ORDER BY i.collectionItem");
		q.setLong("userId", userId);
		@SuppressWarnings("unchecked")
		List<CollectionItemScore> list = (List<CollectionItemScore>) q.list();
		return list;
	}
	
	//Use when setInfo and when load score
	public CollectionItemScore queryByInfoTargetAndItemId(Long infoTarget, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM CollectionItemScore AS i WHERE i.collectionItem = :itemId AND i.infoTarget = :infoTarget");
		q.setLong("itemId", itemId);
		q.setLong("infoTarget", infoTarget);
		return (CollectionItemScore) q.uniqueResult();
	}
}
