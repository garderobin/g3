package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.SystemState;
import com.score.service.SysUserService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class ResetPasswordAction extends ActionSupport
{
	private SysUserService sysUserService;
	
	private String username, newPassword, resetCode;
	
	private SystemState systemState;

	@Transactional
	public String execute()
	{
		if (newPassword.length() < 5 || newPassword.length() > 16)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PASSWORD_LENGTH_WRONG);
			return Action.INPUT;
		}
		ErrorType errorCode;
		if ((errorCode = this.sysUserService.resetUserPassword(username, newPassword, resetCode)) == ErrorType.NO_ERROR)
			return Action.SUCCESS;
		else
		{
			FieldErrorGenerator.addFieldError(this, errorCode);
			return Action.INPUT;
		}
	}
	
	public String gotoResetPasswordPage()
	{
		return Action.SUCCESS;
	}
	
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setResetCode(String resetCode) {
		this.resetCode = resetCode;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public String getResetCode() {
		return resetCode;
	}
}
