package com.score.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.hibernate.Hibernate;

import com.score.bean.CollectionItemInfo;
import com.score.bean.CollectionItemInfoImage;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemInfoDao;
import com.score.dao.CollectionItemInfoImageDao;
import com.score.util.SmallImageGenerator;

public class CollectionItemInfoImageService 
{
	private CollectionItemInfoService collectionItemInfoService;
	private CollectionItemInfoDao collectionItemInfoDao;
	private CollectionItemInfoImageDao collectionItemInfoImageDao;

	public Long createCollectionItemInfoImage(SysUserOut user, Long itemId, InputStream inputStream, String type) throws IOException, SQLException
	{
		CollectionItemInfo itemInfo = this.collectionItemInfoDao.queryByInfoProviderAndItemId(user.getId(), itemId);
		if (itemInfo == null)
		{
			this.collectionItemInfoService.setInfo(user, itemId, null, null);
			itemInfo = this.collectionItemInfoDao.queryByInfoProviderAndItemId(user.getId(), itemId);
		}
		CollectionItemInfoImage image = new CollectionItemInfoImage();
		byte[] buffer = new byte[(int)inputStream.available()];
		inputStream.read(buffer); 
		image.setImage(Hibernate.createBlob(buffer));
		//byte[] smallBuffer = SmallImageGenerator.generate(buffer, 100, 70);
		//image.setSmallImage(Hibernate.createBlob(smallBuffer));
		image.setType(type);
		image.setCollectionItemInfo(itemInfo);
		this.collectionItemInfoImageDao.save(image);
		return image.getId();
	}

	public void setCollectionItemInfoService(
			CollectionItemInfoService collectionItemInfoService) {
		this.collectionItemInfoService = collectionItemInfoService;
	}

	public void setCollectionItemInfoImageDao(
			CollectionItemInfoImageDao collectionItemInfoImageDao) {
		this.collectionItemInfoImageDao = collectionItemInfoImageDao;
	}


	public void setCollectionItemInfoDao(CollectionItemInfoDao collectionItemInfoDao) {
		this.collectionItemInfoDao = collectionItemInfoDao;
	}
}
