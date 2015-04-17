package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.score.bean.Message;
import com.score.bean.SysUserOut;
import com.score.dao.MessageDao;
import com.score.service.SysUserService;

public class CreateMessageAction {
	private SysUserService sysUserService;
	private MessageDao messageDao;		
	private Message message;
	private Long infoTargetId; //收件人id 用户id是long型，message的id是Integer型
	private String messageText;   //站内信正文
	private String messageTitle;  //站内信标题
	private String infoTargetUsername;
	
	@Transactional
	public String execute(){
		//SysUserOut infoTarget = this.sysUserService.queryUserOutWithCheckItemById(this.infoTargetId);
		SysUserOut infoTarget = this.sysUserService.findUserOutByUsername(this.infoTargetUsername);
		SysUserOut infoProvider = this.sysUserService.findCurrentUser();		
		message = new Message();
		message.setInfoTarget(infoTarget);
		message.setInfoProvider(infoProvider);
		message.setMessage(messageText);
		message.setTitle(messageTitle);
		if (message.getId() != null && message.getId() > 0)
			this.messageDao.merge(message);
		else
			this.messageDao.save(message);
		return Action.SUCCESS;
	}
	
	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public Long getInfoTargetId() {
		return infoTargetId;
	}

	public void setInfoTargetId(Long infoTargetId) {
		this.infoTargetId = infoTargetId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getInfoTargetUsername() {
		return infoTargetUsername;
	}

	public void setInfoTargetUsername(String infoTargetUsername) {
		this.infoTargetUsername = infoTargetUsername;
	}


}
