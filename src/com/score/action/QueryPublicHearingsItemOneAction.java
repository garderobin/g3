package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.PublicHearingsItem;
import com.score.dao.PublicHearingsItemDao;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryPublicHearingsItemOneAction extends ActionSupport 
{
	private PublicHearingsItemDao publicHearingsItemDao;
	
	private PublicHearingsItem publicHearingsItem;
	
	private Long id;

	@Transactional
	public String queryItemById()
	{
		if (this.id == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		this.publicHearingsItem = this.publicHearingsItemDao.queryItemById(this.id);
		return Action.SUCCESS;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PublicHearingsItem getPublicHearingsItem() {
		return publicHearingsItem;
	}

	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}
}
