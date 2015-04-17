package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.PublicHearingsItemScore;

public class PublicHearingsItemScoreDao extends HibernateDaoSupport 
{
	public void save(PublicHearingsItemScore publicHearingsItemScore)
	{
		getSession().save(publicHearingsItemScore);
	}
	
	public void merge(PublicHearingsItemScore publicHearingsItemScore)
	{
		getSession().merge(publicHearingsItemScore);
	}
	
	public List<PublicHearingsItemScore> queryAll()
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemScore AS i ORDER BY i.id");
		@SuppressWarnings("unchecked")
		List<PublicHearingsItemScore> list = (List<PublicHearingsItemScore>) q.list();
		return list;
	}
	
	public List<PublicHearingsItemScore> queryByItemId(Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemScore AS i WHERE i.publicHearingsItem = :itemId ORDER BY i.infoTarget");
		q.setLong("itemId", itemId);
		@SuppressWarnings("unchecked")
		List<PublicHearingsItemScore> list = (List<PublicHearingsItemScore>) q.list();
		return list;
	}
	
	public List<PublicHearingsItemScore> queryByInfoTarget(Long userId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemScore AS i WHERE i.infoTarget = :userId ORDER BY i.publicHearingsItem");
		q.setLong("userId", userId);
		@SuppressWarnings("unchecked")
		List<PublicHearingsItemScore> list = (List<PublicHearingsItemScore>) q.list();
		return list;
	}
	
	//Use when setInfo
	public PublicHearingsItemScore queryByInfoTargetAndItemId(Long infoTarget, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemScore AS i WHERE i.publicHearingsItem = :itemId AND i.infoTarget = :infoTarget");
		q.setLong("itemId", itemId);
		q.setLong("infoTarget", infoTarget);
		return (PublicHearingsItemScore) q.uniqueResult();
	}
}
