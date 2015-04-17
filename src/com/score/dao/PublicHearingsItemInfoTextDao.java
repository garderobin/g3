package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.PublicHearingsItemInfoText;

public class PublicHearingsItemInfoTextDao extends HibernateDaoSupport 
{
	public void save(PublicHearingsItemInfoText publicHearingsItemInfoText)
	{
		getSession().save(publicHearingsItemInfoText);
	}
	
	public void merge(PublicHearingsItemInfoText publicHearingsItemInfoText)
	{
		getSession().merge(publicHearingsItemInfoText);
	}
	
	//Use when load public-hearings-fill.jsp
	public List<PublicHearingsItemInfoText> queryByInfoTargetAndItemId(Long infoTarget, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemInfoText AS i WHERE i.publicHearingsItem = :itemId AND i.infoTarget = :infoTarget");	//random order for not recognize the provider
		q.setLong("itemId", itemId);
		q.setLong("infoTarget", infoTarget);
		@SuppressWarnings("unchecked")
		List<PublicHearingsItemInfoText> list = (List<PublicHearingsItemInfoText>) q.list();
		return list;
	}
	
	//Use when load public-hearings-fill.jsp
	public List<PublicHearingsItemInfoText> queryByInfoProviderAndItemId(Long infoProvider, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemInfoText AS i WHERE i.publicHearingsItem = :itemId AND i.infoProvider = :infoProvider ORDER BY i.infoTarget");
		q.setLong("itemId", itemId);
		q.setLong("infoProvider", infoProvider);
		@SuppressWarnings("unchecked")
		List<PublicHearingsItemInfoText> list = (List<PublicHearingsItemInfoText>) q.list();
		return list;
	}
	
	//Use when setInfo
	public PublicHearingsItemInfoText queryByInfoProviderAndInfoTargetAndItemId(Long infoProvider, Long infoTarget, Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemInfoText AS i WHERE i.publicHearingsItem = :itemId AND i.infoTarget = :infoTarget AND i.infoProvider = :infoProvider");
		q.setLong("itemId", itemId);
		q.setLong("infoTarget", infoTarget);
		q.setLong("infoProvider", infoProvider);
		return (PublicHearingsItemInfoText) q.uniqueResult();
	}
	
	//Use when calculate
	public List<PublicHearingsItemInfoText> queryByItemId(Long itemId)
	{
		Query q = getSession().createQuery("SELECT i FROM PublicHearingsItemInfoText AS i WHERE i.publicHearingsItem = :itemId ORDER BY i.infoTarget");
		q.setLong("itemId", itemId);
		@SuppressWarnings("unchecked")
		List<PublicHearingsItemInfoText> list = (List<PublicHearingsItemInfoText>) q.list();
		return list;
	}
}
