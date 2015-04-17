package com.score.action;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.SystemState;
import com.score.bean.Thought;
import com.score.dao.ThoughtDao;
import com.score.service.SystemStateService;


@SuppressWarnings("serial")
public class QueryThoughtByPublishAction extends ActionSupport 
{
	private ThoughtDao thoughtDao;		
	private List<Thought> thoughtList;
	private SystemStateService systemStateService;
	private SystemState systemState;
	
	@Transactional
	public String queryByPublish() //查看所有发布的感想
	{
		systemState = systemStateService.getSystemState();
		this.thoughtList = this.thoughtDao.queryByPublish();
		return Action.SUCCESS;
	}

	public List<Thought> getThoughtList() {
		return thoughtList;
	}

	public void setThoughtDao(ThoughtDao thoughtDao) {
		this.thoughtDao = thoughtDao;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

}
