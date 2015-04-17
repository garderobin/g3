package com.score.service;

import org.springframework.transaction.annotation.Transactional;

import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemScore;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemDao;
import com.score.dao.CollectionItemScoreDao;

public class CollectionItemScoreService 
{
	private CollectionItemScoreDao collectionItemScoreDao;
	private CollectionItemDao collectionItemDao;
	
	@Transactional
	public boolean setInfo(SysUserOut user, Long itemId, Double value, Long rank)
	{
		CollectionItem item = this.collectionItemDao.queryItemById(itemId);
		if (item == null)
			return false;
		CollectionItemScore info = this.collectionItemScoreDao.queryByInfoTargetAndItemId(user.getId(), itemId);
		if (info == null)
			info = new CollectionItemScore();
		info.setCollectionItem(item);
		info.setInfoTarget(user);
		info.setValue(value);
		info.setRank(rank);
		this.collectionItemScoreDao.merge(info);
		return true;
	}

	public void setCollectionItemScoreDao(CollectionItemScoreDao collectionItemScoreDao) {
		this.collectionItemScoreDao = collectionItemScoreDao;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}
}
