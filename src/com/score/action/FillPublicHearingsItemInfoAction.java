package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.PublicHearingsItem;
import com.score.bean.SysUserOut;
import com.score.service.PublicHearingsItemInfoService;
import com.score.service.PublicHearingsItemInfoTextService;
import com.score.dao.PublicHearingsItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class FillPublicHearingsItemInfoAction extends ActionSupport 
{
	private SystemStateService systemStateService;
	private PublicHearingsItemDao publicHearingsItemDao;
	private PublicHearingsItemInfoService publicHearingsItemInfoService;
	private PublicHearingsItemInfoTextService publicHearingsItemInfoTextService;
	private SysUserService sysUserService;
	
	private Long itemId;
	
	private Long infoTarget;
	
	private Integer value;
	
	private String text;

	@Transactional
	public String fillInfoAndText()
	{
		String result1 = fillInfo();
		String result2 = fillText();
		if (result1.equals(Action.SUCCESS) || result2.equals(Action.SUCCESS))
			return Action.SUCCESS;
		if (result1.equals(Action.INPUT) || result2.equals(Action.INPUT))
			return Action.INPUT;
		return Action.ERROR;
	}

	@Transactional
	public String fillInfo()
	{
		PublicHearingsItem item = null;
		if (this.itemId != null)
			item = this.publicHearingsItemDao.queryItemById(itemId);
		if (this.itemId == null || item == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PUBLIC_HEARINGS_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		if (this.systemStateService.fillPublicHearingsItemInfo(item) != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		if (this.value == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		SysUserOut user = this.sysUserService.findCurrentUser();
		SysUserOut infoTargetUser = this.sysUserService.queryUserOutById(this.infoTarget);
		if (infoTargetUser == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.USER_NOT_EXIST);
			return Action.INPUT;
		}
		if (this.publicHearingsItemInfoService.setInfo(user, this.itemId, infoTargetUser, this.value))
			return Action.SUCCESS;
		else 
			return Action.ERROR;
	}

	@Transactional
	public String fillText()
	{
		PublicHearingsItem item = null;
		if (this.itemId != null)
			item = this.publicHearingsItemDao.queryItemById(itemId);
		if (this.itemId == null || item == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PUBLIC_HEARINGS_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		if (this.systemStateService.fillPublicHearingsItemInfo(item) != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		if (this.itemId == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		if (this.text == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		SysUserOut user = this.sysUserService.findCurrentUser();
		SysUserOut infoTargetUser = this.sysUserService.queryUserOutById(this.infoTarget);
		if (infoTargetUser == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.USER_NOT_EXIST);
			return Action.INPUT;
		}
		if (this.publicHearingsItemInfoTextService.setInfo(user, this.itemId, infoTargetUser, this.text))
			return Action.SUCCESS;
		else 
			return Action.ERROR;
	}
	
	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}
	
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public void setInfoTarget(Long infoTarget) {
		this.infoTarget = infoTarget;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public Long getInfoTarget() {
		return infoTarget;
	}

	public void setPublicHearingsItemInfoService(
			PublicHearingsItemInfoService publicHearingsItemInfoService) {
		this.publicHearingsItemInfoService = publicHearingsItemInfoService;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setPublicHearingsItemInfoTextService(
			PublicHearingsItemInfoTextService publicHearingsItemInfoTextService) {
		this.publicHearingsItemInfoTextService = publicHearingsItemInfoTextService;
	}

	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}
}
