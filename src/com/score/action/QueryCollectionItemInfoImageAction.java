package com.score.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemInfo;
import com.score.bean.CollectionItemInfoImage;
import com.score.bean.SysUserOut;
import com.score.bean.SystemState;
import com.score.dao.CollectionItemInfoDao;
import com.score.dao.CollectionItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryCollectionItemInfoImageAction extends ActionSupport
{
	private CollectionItemDao collectionItemDao;
	private CollectionItemInfoDao collectionItemInfoDao;
	private SystemStateService systemStateService;
	private SysUserService sysUserService;
	
	private SystemState systemState;
	
	private Long itemId;
	private Long userId;
	
	private List<CollectionItemInfoImage> itemInfoImage;

	@Transactional
	public String execute()
	{
		this.systemState = this.systemStateService.getSystemState();
		
		CollectionItemInfo collectionItemInfo = null;

		SysUserOut currentUser = this.sysUserService.findCurrentUser();
		if (this.userId == null || this.userId <= 0)
			this.userId = currentUser.getId();
		if (itemId != null && itemId > 0)
			collectionItemInfo = this.collectionItemInfoDao.queryByInfoWithImageProviderAndItemId(this.userId, this.itemId);
		
		CollectionItem collectionItem = this.collectionItemDao.queryItemWithCheckOperatorsById(this.itemId);
		if (collectionItem.checkCheckOperator(currentUser) == true) { }
		else if (this.userId.equals(currentUser.getId())) { }
		else
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.USER_IS_NOT_IN_THE_CHECKOPERATORS_LIST_NOR_INFORPROVIDER);
			return Action.INPUT;
		}
		
		if (collectionItemInfo != null)
			this.itemInfoImage = collectionItemInfo.getImages();
		else
			this.itemInfoImage = new ArrayList<CollectionItemInfoImage>();
		
		return Action.SUCCESS;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setCollectionItemInfoDao(
			CollectionItemInfoDao collectionItemInfoDao) {
		this.collectionItemInfoDao = collectionItemInfoDao;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public List<CollectionItemInfoImage> getItemInfoImage() {
		return itemInfoImage;
	}
}
