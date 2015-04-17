package com.score.action;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.Message;
import com.score.bean.SysUserOut;
import com.score.dao.MessageDao;
import com.score.service.SysUserService;

@SuppressWarnings("serial")
public class QueryMessageAction extends ActionSupport 
{
	private SysUserService sysUserService;
	private MessageDao messageDao;		
	private Message message;
	private List<Message> messageList;
	
	@Transactional
	public String execute()//这一段纯属写来测试的= =
	{
		queryByInfoProvider();
		for(int i = 0; i<this.messageList.size(); i++)
		{
			this.message = this.messageList.get(i);
			System.out.println("check infoProvider:\nmessage: \t" + "id=" + this.message.getId());
		}
		queryByInfoTarget();
		for(int i = 0; i<this.messageList.size(); i++)
		{
			this.message = this.messageList.get(i);
			System.out.println("check infoTarget:\nmessage: \t" + "id=" + this.message.getId());
		}
		return Action.SUCCESS;
	}
	
	@Transactional
	public String queryByInfoTarget() //查看所有发给自己的信，相当于查看“收件”
	{
		SysUserOut infoTarget = this.sysUserService.findCurrentUser();
		this.messageList = this.messageDao.queryByInfoTarget(infoTarget);
		return Action.SUCCESS;
	}
	
	@Transactional
	public String queryByInfoProvider() //查看所有自己发出的信,相当于查看“已发送”
	{
		SysUserOut infoProvider = this.sysUserService.findCurrentUser();
		this.messageList = this.messageDao.queryByInfoProvider(infoProvider);
		return Action.SUCCESS;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

}
