package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.dao.CollectionItemDao;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class DeleteCollectionItemAction extends ActionSupport 
{
	private Long id;
	
	private SystemStateService systemStateService;
	private CollectionItemDao collectionItemDao;

	@Transactional
	public String execute()
	{
		if (this.systemStateService.editCollectionItem() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		if (this.id != null && this.id > 0)
			this.collectionItemDao.deleteById(this.id);
		else
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId()
	{
		return this.id;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}
}
