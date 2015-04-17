package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItemInfo;
import com.score.bean.CollectionItemInfoImage;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemInfoImageDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class DeleteCollectionItemInfoImageAction extends ActionSupport 
{
	private Long id;
	
	private SystemStateService systemStateService;
	private SysUserService sysUserService;
	private CollectionItemInfoImageDao collectionItemInfoImageDao;

	@Transactional
	public String execute()
	{
		CollectionItemInfoImage collectionItemInfoImage = null;
		if (this.id != null && this.id > 0)
			collectionItemInfoImage = this.collectionItemInfoImageDao.queryById(id);
		if (collectionItemInfoImage == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_INFO_IMAGE_NOT_EXIST);
			return Action.INPUT;
		}
		CollectionItemInfo info = collectionItemInfoImage.getCollectionItemInfo();
		if (this.systemStateService.fillCollectionItemInfo(info.getCollectionItem()) != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		SysUserOut currentUser = this.sysUserService.findCurrentUser();
		Long userId = info.getInfoProvider().getId();
		if (userId.equals(currentUser.getId()))
		{
			this.collectionItemInfoImageDao.deleteCollectionItemInfoImageById(this.id);
			return Action.SUCCESS;
		}
		else
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.USER_IS_NOT_INFOPROVIDER);
			return Action.INPUT;
		}
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId()
	{
		return this.id;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setCollectionItemInfoImageDao(
			CollectionItemInfoImageDao collectionItemInfoImageDao) {
		this.collectionItemInfoImageDao = collectionItemInfoImageDao;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}
}
