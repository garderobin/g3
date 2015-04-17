package com.score.service;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemInfo;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemDao;
import com.score.dao.CollectionItemInfoDao;
import com.score.util.ErrorType;

public class CollectionItemInfoService 
{
	private CollectionItemInfoDao collectionItemInfoDao;
	private CollectionItemDao collectionItemDao;
	
	@Transactional
	public ErrorType setInfo(SysUserOut user, Long itemId, String text, Integer value)
	{
		CollectionItem item = this.collectionItemDao.queryItemById(itemId);
		if (item == null)
			return ErrorType.COLLECTION_ITEM_NOT_EXIST;
		if (item.getStartFillingTime() != null)
		{
			Date date = new Date();
			if (date.before(item.getStartFillingTime()) || date.after(item.getEndFillingTime()))
			{
				return ErrorType.COLLECTION_ITEM_FILLING_IS_NOT_AT_A_PROPER_ITEM_STATE;
			}
		}
		CollectionItemInfo info = this.collectionItemInfoDao.queryByInfoProviderAndItemId(user.getId(), itemId);
		if (info == null)
			info = new CollectionItemInfo();
		info.setCollectionItem(item);
		info.setInfoProvider(user);
		info.setValue(value);
		info.setText(text);
		this.collectionItemInfoDao.merge(info);
		return ErrorType.NO_ERROR;
	}

	public void setCollectionItemInfoDao(CollectionItemInfoDao collectionItemInfoDao) {
		this.collectionItemInfoDao = collectionItemInfoDao;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}
}
