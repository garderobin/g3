package com.score.action;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.PublicHearingsItem;
import com.score.bean.SystemState;
import com.score.dao.PublicHearingsItemDao;
import com.score.service.SystemStateService;

@SuppressWarnings("serial")
public class QueryPublicHearingsItemAction extends ActionSupport 
{
	private PublicHearingsItemDao publicHearingsItemDao;
	private SystemStateService systemStateService;
	
	private List<PublicHearingsItem> publicHearingsItemList;
	private SystemState systemState;

	@Transactional
	public String queryItemAll()
	{
		this.systemState = this.systemStateService.getSystemState();
		this.publicHearingsItemList = this.publicHearingsItemDao.queryItemAll();
		return Action.SUCCESS;
	}

	@Transactional
	public String queryItemWhichShallBePublicized()
	{
		this.systemState = this.systemStateService.getSystemState();
		this.publicHearingsItemList = this.publicHearingsItemDao.queryItemWhichShallBePublicized();
		return Action.SUCCESS;
	}

	public SystemState getSystemState() {
		return this.systemState;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

	public List<PublicHearingsItem> getPublicHearingsItemList() {
		return publicHearingsItemList;
	}
}
