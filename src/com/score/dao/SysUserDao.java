package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.SysUser;
import com.score.bean.SysUserOut;

public class SysUserDao extends HibernateDaoSupport {

	public void merge(SysUser user)
	{
		getSession().merge(user);
	}
	
	public SysUser findByUsername(String name) {
		Query q = getSession().createQuery("select u from SysUser as u where u.username = :name");
		q.setString("name", name);
		return (SysUser) q.uniqueResult();
	}
	
	public SysUser queryById(Long userId) {
		Query q = getSession().createQuery("select u from SysUser as u where u.id = :userId");
		q.setLong("userId", userId);
		return (SysUser) q.uniqueResult();
	}
	
	public SysUserOut findUserOutByUsername(String name) {
		Query q = getSession().createQuery("select u from SysUserOut as u where u.username = :name");
		q.setString("name", name);
		return (SysUserOut) q.uniqueResult();
	}
	
	public List<SysUserOut> queryAllUserOut() {
		Query q = getSession().createQuery("SELECT u FROM SysUserOut AS u WHERE u.ifTest = 1 ORDER BY u.id");
		@SuppressWarnings("unchecked")
		List<SysUserOut> list = q.list();
		return list;
	}
	
	public List<SysUserOut> queryAllUser() {
		Query q = getSession().createQuery("SELECT u FROM SysUserOut AS u ORDER BY u.id");
		@SuppressWarnings("unchecked")
		List<SysUserOut> list = q.list();
		return list;
	}
	
	public SysUserOut queryUserOutById(Long id)
	{
		Query q = getSession().createQuery("SELECT u FROM SysUserOut AS u WHERE u.id = :id");
		q.setLong("id", id);
		SysUserOut user = (SysUserOut)q.uniqueResult();
		return user;
	}

	public SysUserOut queryUserOutWithCheckItemById(Long id)
	{
		Query q = getSession().createQuery("SELECT u FROM SysUserOut AS u WHERE u.id = :id");
		q.setLong("id", id);
		SysUserOut user = (SysUserOut)q.uniqueResult();
		return user;
	}
	
	public Long queryUserNumber()
	{
		Query q = getSession().createQuery("SELECT COUNT(u) FROM SysUserOut AS u WHERE u.ifTest = 1");
		Long num = (Long) q.uniqueResult();
		return num;
	}
}
