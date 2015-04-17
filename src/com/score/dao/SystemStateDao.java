package com.score.dao;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.SystemState;

public class SystemStateDao extends HibernateDaoSupport
{
	public void merge(SystemState systemState)
	{
		getSession().merge(systemState);
	}
	
	public SystemState query()
	{
		Query q = getSession().createQuery("SELECT s FROM SystemState AS s");
		return (SystemState) q.uniqueResult();
	}
}
