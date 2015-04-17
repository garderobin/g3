package com.score.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.score.bean.Message;
import com.score.bean.SysUserOut;

public class MessageDao extends HibernateDaoSupport
{

	public void save(Message mes)
	{
		getSession().save(mes);
	}
	
	public void merge(Message mes)
	{
		getSession().merge(mes);
	}
	 
	public void deleteById(Integer mesId)
	{
		Message mes = queryById(mesId);
		if (mes != null)
			delete(mes);
	}
	
	public void delete(Message mes)
	{
		getSession().delete(mes);
	}
	
	public Message queryById(Integer id)
	{
		Query q = getSession().createQuery("SELECT mes FROM Message AS mes WHERE mes.id = :id");
		q.setInteger("id", id);
		Message mes = (Message) q.uniqueResult();
		return mes;
	}
	
	public List<Message> queryByInfoTarget(SysUserOut user)
	{
		Query q = getSession().createQuery("SELECT mes FROM Message AS mes WHERE mes.infoTarget = :user");
		q.setLong("user", user.getId());
		@SuppressWarnings("unchecked")
		List<Message> list = q.list();
		return list;
	}
	
	public List<Message> queryByInfoProvider(SysUserOut user)
	{
		Query q = getSession().createQuery("SELECT mes FROM Message AS mes WHERE mes.infoProvider = :user");
		q.setLong("user", user.getId());
		@SuppressWarnings("unchecked")
		List<Message> list = q.list();
		return list;
	}
	
	public List<Message> queryItemAll()
	{
		Query q = getSession().createQuery("SELECT mes FROM Message AS mes ORDER BY mes.id");
		@SuppressWarnings("unchecked")
		List<Message> items = q.list();
		return items;
	}
	
}
