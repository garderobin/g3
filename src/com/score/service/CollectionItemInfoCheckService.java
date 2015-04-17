package com.score.service;

import org.springframework.transaction.annotation.Transactional;

import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemInfoCheck;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemDao;
import com.score.dao.CollectionItemInfoCheckDao;

public class CollectionItemInfoCheckService 
{
	private CollectionItemInfoCheckDao collectionItemInfoCheckDao;
	private CollectionItemDao collectionItemDao;
	
	@Transactional
	public boolean setInfo(SysUserOut user, Long itemId, SysUserOut infoTarget, Integer value)
	{
		CollectionItem item = this.collectionItemDao.queryItemById(itemId);
		if (item == null)
			return false;
		CollectionItemInfoCheck info = this.collectionItemInfoCheckDao.queryByInfoProviderAndInfoTargetAndItemId(user.getId(), infoTarget.getId(), itemId);
		if (info == null)
			info = new CollectionItemInfoCheck();
		info.setCollectionItem(item);
		info.setInfoProvider(user);
		info.setInfoTarget(infoTarget);
		info.setValue(value);
		this.collectionItemInfoCheckDao.merge(info);
		return true;
	}

	public void setCollectionItemInfoCheckDao(
			CollectionItemInfoCheckDao collectionItemInfoCheckDao) {
		this.collectionItemInfoCheckDao = collectionItemInfoCheckDao;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}
}
