package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.score.bean.PublicHearingsItemInfo;

public class PublicHearingsItemInfoDao extends HibernateDaoSupport 
{
	public void save(PublicHearingsItemInfo publicHearingsItemInfo)
	{
		getSession().save(publicHearingsItemInfo);
	}
	
	public void merge(PublicHearingsItemInfo publicHearingsItemInfo)
	{
		getSession().merge(publicHearingsItemInfo);
	}
	
	//Use when load public-hearings-fill.jsp
	public List<PublicHearingsItemInfo> queryByInfoProviderAndItemId(Long infoProvider, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemInfo AS i WHERE i.publicHearingsItem = :itemId AND i.infoProvider = :infoProvider ORDER BY i.infoTarget");
		q.setLong("itemId", itemId);
		q.setLong("infoProvider", infoProvider);
		@SuppressWarnings("unchecked")
		List<PublicHearingsItemInfo> list = (List<PublicHearingsItemInfo>) q.list();
		return list;
	}
	
	//Use when setInfo
	public PublicHearingsItemInfo queryByInfoProviderAndInfoTargetAndItemId(Long infoProvider, Long infoTarget, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemInfo AS i WHERE i.publicHearingsItem = :itemId AND i.infoTarget = :infoTarget AND i.infoProvider = :infoProvider");
		q.setLong("itemId", itemId);
		q.setLong("infoTarget", infoTarget);
		q.setLong("infoProvider", infoProvider);
		return (PublicHearingsItemInfo) q.uniqueResult();
	}
	
	//Use when calculate
	public List<PublicHearingsItemInfo> queryByItemId(Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemInfo AS i WHERE i.publicHearingsItem = :itemId ORDER BY i.infoTarget");
		q.setLong("itemId", itemId);
		@SuppressWarnings("unchecked")
		List<PublicHearingsItemInfo> list = (List<PublicHearingsItemInfo>) q.list();
		return list;
	}
}
