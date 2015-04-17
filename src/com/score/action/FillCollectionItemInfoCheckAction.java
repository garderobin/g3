package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.SysUserOut;
import com.score.service.CollectionItemInfoCheckService;
import com.score.dao.CollectionItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class FillCollectionItemInfoCheckAction extends ActionSupport 
{
	private SystemStateService systemStateService;
	private CollectionItemDao collectionItemDao;
	private CollectionItemInfoCheckService collectionItemInfoCheckService;
	private SysUserService sysUserService;
	
	private Long itemId;
	
	private Long infoTarget;
	
	private Integer value;

	@Transactional
	public String execute()
	{
		CollectionItem collectionItem = null;
		if (itemId != null && itemId > 0)
			collectionItem = this.collectionItemDao.queryItemWithCheckOperatorsById(itemId);
		if (collectionItem == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		
		if (this.systemStateService.fillCollectionItemInfoCheck() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		
		SysUserOut currentUser = this.sysUserService.findCurrentUser();
		
		if (collectionItem.checkCheckOperator(currentUser) == false)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.USER_IS_NOT_IN_THE_CHECKOPERATORS_LIST);
			return Action.INPUT;
		}
		
		SysUserOut user = this.sysUserService.findCurrentUser();
		SysUserOut infoTargetUser = this.sysUserService.queryUserOutById(this.infoTarget);
		if (infoTargetUser == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.USER_NOT_EXIST);
			return Action.INPUT;
		}
		
		if (this.collectionItemInfoCheckService.setInfo(user, this.itemId, infoTargetUser, this.value))
			return Action.SUCCESS;
		else 
			return Action.ERROR;
	}
	
	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}
	
	public void setCollectionItemInfoCheckService(
			CollectionItemInfoCheckService collectionItemInfoCheckService) {
		this.collectionItemInfoCheckService = collectionItemInfoCheckService;
	}
	
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public void setInfoTarget(Long infoTarget) {
		this.infoTarget = infoTarget;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public Long getInfoTarget() {
		return infoTarget;
	}
}
