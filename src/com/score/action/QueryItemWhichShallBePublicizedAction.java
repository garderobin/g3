package com.score.action;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.PublicHearingsItem;
import com.score.bean.SystemState;
import com.score.dao.CollectionItemDao;
import com.score.dao.PublicHearingsItemDao;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryItemWhichShallBePublicizedAction extends ActionSupport 
{
	private CollectionItemDao collectionItemDao;
	
	private PublicHearingsItemDao publicHearingsItemDao;
	
	private SystemStateService systemStateService;
	
	private List<CollectionItem> collectionItemList;
	
	private List<PublicHearingsItem> publicHearingsItemList;
	
	private SystemState systemState;

	@Transactional
	public String execute()
	{
		this.systemState = this.systemStateService.getSystemState();
		if (this.systemStateService.queryPublicityInformation() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		this.collectionItemList = this.collectionItemDao.queryItemWhichShallBePublicized();
		this.publicHearingsItemList = this.publicHearingsItemDao.queryItemWhichShallBePublicized();
		
		return Action.SUCCESS;
	}

	@Transactional
	public String executeText()
	{
		this.systemState = this.systemStateService.getSystemState();
		if (this.systemStateService.queryPublicHearingsItemInfoText() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		this.publicHearingsItemList = this.publicHearingsItemDao.queryItemTextWhichShallBePublicized();
		
		return Action.SUCCESS;
	}

	public List<CollectionItem> getCollectionItemList() {
		return collectionItemList;
	}

	public List<PublicHearingsItem> getPublicHearingsItemList() {
		return publicHearingsItemList;
	}

	public List<CollectionItem> getCollectionItems() {
		return collectionItemList;
	}

	public List<PublicHearingsItem> getPublicHearingsItem() {
		return publicHearingsItemList;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}
}
