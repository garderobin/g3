package com.score.action;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.PublicHearingsItemInfoText;
import com.score.bean.SystemState;
import com.score.dao.PublicHearingsItemInfoTextDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryPublicHearingsTextAction extends ActionSupport 
{
	private PublicHearingsItemInfoTextDao publicHearingsItemInfoTextDao;
	
	private SystemStateService systemStateService;
	
	private SysUserService sysUserService;
	
	private Long itemId;
	
	private List<PublicHearingsItemInfoText> publicHearingsItemInfoTextList;

	private SystemState systemState;

	@Transactional
	public String QueryPublicHearingsTextByItemId()
	{
		this.systemState = this.systemStateService.getSystemState();
		if (this.systemStateService.queryPublicHearingsItemInfoText() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		if (itemId == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		this.publicHearingsItemInfoTextList = this.publicHearingsItemInfoTextDao.queryByInfoTargetAndItemId(this.sysUserService.findCurrentUser().getId(), itemId);
		for (PublicHearingsItemInfoText item : this.publicHearingsItemInfoTextList)
			item.setInfoProvider(null);
		return Action.SUCCESS;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public List<PublicHearingsItemInfoText> getPublicHearingsItemInfoTextList() {
		return publicHearingsItemInfoTextList;
	}
	
	public void setPublicHearingsItemInfoTextDao(
			PublicHearingsItemInfoTextDao publicHearingsItemInfoTextDao) {
		this.publicHearingsItemInfoTextDao = publicHearingsItemInfoTextDao;
	}
}
