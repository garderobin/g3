package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.dao.CollectionItemDao;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryCollectionItemOneAction extends ActionSupport 
{
	private CollectionItemDao collectionItemDao;
	
	private CollectionItem collectionItem;
	
	private Long id;

	@Transactional
	public String queryItemById()
	{
		if (this.id == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		this.collectionItem = this.collectionItemDao.queryItemById(this.id);
		return Action.SUCCESS;
	}

	@Transactional
	public String queryItemWithCheckOperatorsById()
	{
		if (this.id == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		this.collectionItem = this.collectionItemDao.queryItemWithCheckOperatorsById(this.id);
		return Action.SUCCESS;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public CollectionItem getCollectionItem() {
		return this.collectionItem;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
