package com.score.action;

import com.opensymphony.xwork2.Action;
import com.score.bean.SysUser;
import com.score.bean.SysUserOut;
import com.score.service.SysUserService;
import com.score.util.EmailData;
import com.score.util.MailSender;

public class SendEmailAction {

	private SysUserService sysUserService;
	private MailSender mailSender;
	private String messageText;   //站内信正文
	private String messageTitle;  //站内信标题
	private String infoTargetUsername;
	public String execute() throws Exception
	{
		SysUser infoTarget = this.sysUserService.findByUsername(infoTargetUsername);
		SysUserOut infoProviderOut = this.sysUserService.findCurrentUser();
		String providerName = infoProviderOut.getName();
		String providerUsername = infoProviderOut.getUsername();
		SysUser infoProvider = this.sysUserService.findByUsername(providerUsername);
		String providerMail = infoProvider.getMailAddress();
		String infoProviderStr = providerName+"("+providerUsername+") ("+providerMail+")";
		
		EmailData emailData = new EmailData();
		emailData.setRecipients(new String[]{infoTarget.getMailAddress()});
		emailData.setSubject("【10SE】私信提醒："+ messageTitle);
		emailData.setContentType("html");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("私信来自： "+infoProviderStr + "<br/> ");
		stringBuilder.append("私信内容：<br/>"+messageText+"<br/>");
		stringBuilder.append("<br/>(友情提示：系统自动发送，请勿直接回复本邮件。)");
		emailData.setContent(stringBuilder.toString());
		mailSender.postMail(emailData);
		return Action.SUCCESS;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}


	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public void setInfoTargetUsername(String infoTargetUsername) {
		this.infoTargetUsername = infoTargetUsername;
	}

	public SysUserService getSysUserService() {
		return sysUserService;
	}
}
