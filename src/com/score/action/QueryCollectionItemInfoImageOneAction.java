package com.score.action;

import java.io.InputStream;
import java.sql.SQLException;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemInfo;
import com.score.bean.CollectionItemInfoImage;
import com.score.bean.SysUserOut;
import com.score.bean.SystemState;
import com.score.dao.CollectionItemInfoImageDao;
import com.score.dao.CollectionItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;

public class QueryCollectionItemInfoImageOneAction 
{
	private CollectionItemDao collectionItemDao;
	private CollectionItemInfoImageDao collectionItemInfoImageDao;
	private SystemStateService systemStateService;
	private SysUserService sysUserService;
	
	private SystemState systemState;
	
	private Long id;
	private Boolean ifOriPic;
	
	private CollectionItemInfoImage itemInfoImage;
	
	private int errorCode;

	@Transactional
	public String execute()
	{
		this.systemState = this.systemStateService.getSystemState();
		
		if (id != null && id > 0)
			this.itemInfoImage = this.collectionItemInfoImageDao.queryById(id);
		if (this.itemInfoImage == null)
		{
			this.errorCode = 104;
			return Action.ERROR;
		}
		CollectionItemInfo itemInfo = this.itemInfoImage.getCollectionItemInfo();
		if (itemInfo == null)
		{
			this.errorCode = 103;
			return Action.ERROR;
		}
		
		SysUserOut currentUser = this.sysUserService.findCurrentUser();
		CollectionItem collectionItem = this.collectionItemDao.queryItemWithCheckOperatorsById(itemInfo.getCollectionItem().getId());
		if (itemInfo.getInfoProvider().getId().equals(currentUser.getId()) || collectionItem.checkCheckOperator(currentUser) == true){}
		else
		{
			this.errorCode = 111;
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIfOriPic(Boolean ifOriPic) {
		this.ifOriPic = ifOriPic;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setCollectionItemInfoImageDao(
			CollectionItemInfoImageDao collectionItemInfoImageDao) {
		this.collectionItemInfoImageDao = collectionItemInfoImageDao;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public InputStream getImage() throws SQLException
	{
		if (ifOriPic != null && ifOriPic == true)
			return this.itemInfoImage.getImage().getBinaryStream();
		else
			return this.itemInfoImage.getSmallImage().getBinaryStream();
	}
	
	public String getType()
	{
		if (ifOriPic != null && ifOriPic == true)
			return this.itemInfoImage.getType();
		else
			return "image/jpg";
	}

	public int getErrorCode() {
		return errorCode;
	}
}
