package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.dao.CollectionItemDao;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class CreateCollectionItemPublicitySetAction extends ActionSupport 
{
	private SystemStateService systemStateService;
	private CollectionItemDao collectionItemDao;
	
	private Long id;
	
	private Integer publicityType;

	@Transactional
	public String execute()
	{
		if (this.systemStateService.editCollectionItemPublicity() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		CollectionItem item = null;
		if (this.id != null && this.id > 0)
			item = this.collectionItemDao.queryItemById(this.id);
		if (item == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		
		if (this.publicityType == null)
			this.publicityType = 0;
		
		item.setPublicityType(this.publicityType);
		
		this.collectionItemDao.merge(item);
		return Action.SUCCESS;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	public void setPublicityType(Integer publicityType) {
		this.publicityType = publicityType;
	}
}
