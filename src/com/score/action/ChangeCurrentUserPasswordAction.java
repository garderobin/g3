package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.SysUserOut;
import com.score.bean.SystemState;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class ChangeCurrentUserPasswordAction extends ActionSupport 
{
	private SysUserService sysUserService;
	private SystemStateService systemStateService;
	
	private String oldPassword, newPassword;
	
	private SystemState systemState;

	@Transactional
	public String execute()
	{
		this.systemState = this.systemStateService.getSystemState();
		if (newPassword.length() < 5 || newPassword.length() > 16)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PASSWORD_LENGTH_WRONG);
			return Action.INPUT;
		}
		SysUserOut user = this.sysUserService.findCurrentUser();
		if (this.sysUserService.changePasswordById(user.getId(), oldPassword, newPassword))
			return Action.SUCCESS;
		else
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.OLD_PASSWORD_WRONG);
			return Action.INPUT;
		}
	}
	
	public String gotoChangePasswordPage()
	{
		this.systemState = this.systemStateService.getSystemState();
		return Action.SUCCESS;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public SystemState getSystemState() {
		return systemState;
	}
}
