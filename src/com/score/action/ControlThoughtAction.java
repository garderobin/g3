package com.score.action;


import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.Thought;
import com.score.dao.ThoughtDao;
import com.score.service.SysUserService;

@SuppressWarnings("serial")
public class ControlThoughtAction extends ActionSupport {

	private Long thoughtId;
	private String controlType;
	private Integer controlIntParameter[];
	private String controlStringParameter[];

	private ThoughtDao thoughtDao;
	private SysUserService sysUserService;

	@Transactional
	public String execute() {

		boolean isSuccess = false;

		if ("mark".equalsIgnoreCase(controlType)) {
			Integer thoughtScore = controlIntParameter[0];
			Thought thought = thoughtDao.queryByID(thoughtId);
			thought.setScore(thoughtScore);
			isSuccess = true;
		}else if("addComment".equalsIgnoreCase(controlType)){
			String message = controlStringParameter[0];
			Thought fatherThought = thoughtDao.queryByID(thoughtId);
			
			Thought comment = new Thought();
			comment.setFatherThought(fatherThought);
			comment.setMessage(message);
			comment.setInfoProvider(sysUserService.findCurrentUser());
			
			thoughtDao.save(comment);
			isSuccess = true;
		}else if("publish".equalsIgnoreCase(controlType)){
			Thought thought = thoughtDao.queryByID(thoughtId);
			thought.setPublish(true);
			isSuccess = true;
		}
		return isSuccess ? Action.SUCCESS : Action.INPUT;	
	}

	public void setThoughtId(Long thoughtId) {
		this.thoughtId = thoughtId;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public void setControlIntParameter(Integer[] controlIntParameter) {
		this.controlIntParameter = controlIntParameter;
	}

	public void setControlStringParameter(String[] controlStringParameter) {
		this.controlStringParameter = controlStringParameter;
	}

	public void setThoughtDao(ThoughtDao thoughtDao) {
		this.thoughtDao = thoughtDao;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
}
