package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.TotalScore;

public class TotalScoreDao extends HibernateDaoSupport 
{
	public void save(TotalScore totalScore)
	{
		getSession().save(totalScore);
	}
	
	public void merge(TotalScore totalScore)
	{
		getSession().merge(totalScore);
	}
	
	//Use when generate report
	public List<TotalScore> queryAll()
	{
		Query q = getSession().createQuery("SELECT i FROM TotalScore AS i ORDER BY i.id");
		@SuppressWarnings("unchecked")
		List<TotalScore> totalScores = q.list();
		return totalScores;
	}
	
	//Use when setInfo
	public TotalScore queryByInfoTarget(Long infoTarget)
	{
		Query q = getSession().createQuery("SELECT i FROM TotalScore AS i WHERE i.infoTarget = :infoTarget");
		q.setLong("infoTarget", infoTarget);
		return (TotalScore) q.uniqueResult();
	}
}
