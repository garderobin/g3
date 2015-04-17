package com.score.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.SysUser;
import com.score.service.SysUserService;
import com.score.util.EmailData;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;
import com.score.util.MailSender;

@SuppressWarnings("serial")
public class AppToResetPasswordAction extends ActionSupport
{
	private SysUserService sysUserService;
	
	private MailSender mailSender;
	
	private String username;
	
	public String execute() throws Exception
	{
		ErrorType errorCode = this.sysUserService.appToResetUserPassword(username);
		if (errorCode != ErrorType.NO_ERROR)
		{
			FieldErrorGenerator.addFieldError(this, errorCode);
			return Action.INPUT;
		}
		SysUser sysUser = this.sysUserService.findByUsername(username);
		HttpServletRequest request = ServletActionContext.getRequest();
		EmailData emailData = new EmailData();
		emailData.setRecipients(new String[]{sysUser.getMailAddress()});
		emailData.setSubject("您的找回密码链接");
		emailData.setContentType("html");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("您刚才申请重置您在geniuye.com中的评价系统中的密码，重置密码链接为");
		String href = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() + "/pwd/gotoResetPasswordPage.action?resetCode=" + sysUser.getResetPasswordCode();
		stringBuilder.append("<a href='" + href + "'>" + href + "</a>");
		emailData.setContent(stringBuilder.toString());
		mailSender.postMail(emailData);
		return Action.SUCCESS;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
}
