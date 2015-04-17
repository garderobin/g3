package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.PublicHearingsItem;
import com.score.dao.PublicHearingsItemDao;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class CreatePublicHearingsItemAction extends ActionSupport 
{
	private SystemStateService systemStateService;
	private PublicHearingsItemDao publicHearingsItemDao;
	
	private Long id;
	private String itemId;
	private String name;
	private String contents;
	private Short type;
	private Integer minScore, maxScore, defaultScore, averageScore, deltaScore;
	
	private PublicHearingsItem publicHearingsItem;

	@Transactional
	public String execute()
	{
		if (this.systemStateService.editPublicHearingsItem() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		this.publicHearingsItem = null;
		if (id != null && id > 0)
			publicHearingsItem = this.publicHearingsItemDao.queryItemById(id);
		if (publicHearingsItem == null)
			publicHearingsItem = new PublicHearingsItem();
		publicHearingsItem.setItemId(itemId);
		publicHearingsItem.setName(name);
		publicHearingsItem.setContents(contents);
		publicHearingsItem.setType(type);
		publicHearingsItem.setAverageScore(averageScore);
		publicHearingsItem.setMaxScore(maxScore);
		publicHearingsItem.setMinScore(minScore);
		publicHearingsItem.setDefaultScore(defaultScore);
		publicHearingsItem.setDeltaScore(deltaScore);
		if (publicHearingsItem.getId() != null && publicHearingsItem.getId() > 0)
			this.publicHearingsItemDao.merge(publicHearingsItem);
		else
			this.publicHearingsItemDao.save(publicHearingsItem);
		return Action.SUCCESS;
	}

	public PublicHearingsItem getPublicHearingsItem() {
		return publicHearingsItem;
	}

	public void setPublicHearingsItem(PublicHearingsItem publicHearingsItem) {
		this.publicHearingsItem = publicHearingsItem;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setPublicHearingsItemDao(PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public void setMinScore(Integer minScore) {
		this.minScore = minScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	public void setDefaultScore(Integer defaultScore) {
		this.defaultScore = defaultScore;
	}

	public void setAverageScore(Integer averageScore) {
		this.averageScore = averageScore;
	}

	public void setDeltaScore(Integer deltaScore) {
		this.deltaScore = deltaScore;
	}
}
