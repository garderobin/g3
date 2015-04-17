package com.score.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.score.bean.Thought;

public class ThoughtDao extends HibernateDaoSupport{
	
	public void save(Thought thought) {
		getSession().save(thought);
	}
	public void merge(Thought thought){
		getSession().merge(thought);
	}
	
	public Thought queryByID(Long id){
		Query query = getSession().createQuery("SELECT thought FROM Thought AS thought WHERE thought.id = :id");
		query.setLong("id", id);
		Thought thought = (Thought) query.uniqueResult();
		return thought;
	}
	
	public List<Thought> queryByInfoProviderAndItemId(Long userID, Long itemID){
		Query query = getSession().createQuery("SELECT thought FROM Thought AS thought WHERE thought.infoProvider = :userID AND thought.collectionItem = :itemID order by thought.id desc");
		query.setLong("userID", userID);
		query.setLong("itemID", itemID);
		@SuppressWarnings("unchecked")
		List<Thought> thoughtList = query.list();
		return thoughtList;
	}
	
	public List<Thought> queryByPublish(){
		Query query = getSession().createQuery("SELECT thought FROM Thought AS thought WHERE thought.publish = :publish");
		query.setBoolean("publish",true);
		@SuppressWarnings("unchecked")
		List<Thought> thoughtList = query.list();
		return thoughtList;
	}
}
