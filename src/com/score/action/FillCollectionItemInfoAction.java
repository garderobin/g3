package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.SysUserOut;
import com.score.service.CollectionItemInfoService;
import com.score.dao.CollectionItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class FillCollectionItemInfoAction extends ActionSupport 
{
	private SystemStateService systemStateService;
	private CollectionItemDao collectionItemDao;
	private CollectionItemInfoService collectionItemInfoService;
	private SysUserService sysUserService;

	private Long itemId;
	
	private String text;
	
	private Integer value;

	@Transactional
	public String execute()
	{
		CollectionItem item = null;
		if (this.itemId != null)
			item = this.collectionItemDao.queryItemById(itemId);
		if (this.itemId == null || item == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		if (this.systemStateService.fillCollectionItemInfo(item) != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		SysUserOut user = this.sysUserService.findCurrentUser();
		ErrorType errorCode;
		if ((errorCode = this.collectionItemInfoService.setInfo(user, this.itemId, this.text, this.value)) == ErrorType.NO_ERROR)
			return Action.SUCCESS;
		else 
		{
			FieldErrorGenerator.addFieldError(this, errorCode);
			return Action.INPUT;
		}
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setCollectionItemInfoService(
			CollectionItemInfoService collectionItemInfoService) {
		this.collectionItemInfoService = collectionItemInfoService;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Long getItemId() {
		return itemId;
	}

	public String getText() {
		return text;
	}

	public Integer getValue() {
		return value;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}
}
