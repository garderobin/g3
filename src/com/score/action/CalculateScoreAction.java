package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.PublicHearingsItem;
import com.score.dao.CollectionItemDao;
import com.score.dao.PublicHearingsItemDao;
import com.score.service.CalculateScoreService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class CalculateScoreAction extends ActionSupport
{
	private CalculateScoreService calculateScoreService;
	private CollectionItemDao collectionItemDao;
	private PublicHearingsItemDao publicHearingsItemDao;

	private Long itemId;
	
	@Transactional
	public String calculateAll()
	{
		ErrorType errorCode;
		if ((errorCode = this.calculateScoreService.calculateAll()) != ErrorType.NO_ERROR)
		{
			FieldErrorGenerator.addFieldError(this, errorCode);
			return Action.INPUT;
		}
		else
			return Action.SUCCESS;
	}
	
	@Transactional
	public String calculateCollectionItemByItemId()
	{
		CollectionItem collectionItem = null;
		if (itemId != null && itemId > 0)
			collectionItem = this.collectionItemDao.queryItemById(itemId);
		if (collectionItem == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		ErrorType errorCode;
		if ((errorCode = this.calculateScoreService.calculateCollectionItemByItemId(itemId)) != ErrorType.NO_ERROR)
		{
			FieldErrorGenerator.addFieldError(this, errorCode);
			return Action.INPUT;
		}
		else
			return Action.SUCCESS;
	}
	
	@Transactional
	public String calculatePublicHearingsItemByItemId()
	{
		PublicHearingsItem publicHearingsItem = null;
		if (itemId != null && itemId > 0)
			publicHearingsItem = this.publicHearingsItemDao.queryItemById(itemId);
		if (publicHearingsItem == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PUBLIC_HEARINGS_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		ErrorType errorCode;
		if ((errorCode = this.calculateScoreService.calculatePublicHearingsItemByItemId(itemId)) != ErrorType.NO_ERROR)
		{
			FieldErrorGenerator.addFieldError(this, errorCode);
			return Action.INPUT;
		}
		else
			return Action.SUCCESS;
	}

	public void setCalculateScoreService(CalculateScoreService calculateScoreService) {
		this.calculateScoreService = calculateScoreService;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setPublicHearingsItemDao(PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}
