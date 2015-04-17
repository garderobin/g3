package com.score.action;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.SysUserOut;
import com.score.service.SysUserService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryUserOutAction extends ActionSupport 
{
	private SysUserService sysUserService;
	
	private Long id;
	
	private SysUserOut user;
	
	private List<SysUserOut> users;

	@Transactional
	public String queryUserOutById()
	{
		if (id == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		this.user = this.sysUserService.queryUserOutById(id);
		return Action.SUCCESS;
	}

	@Transactional
	public String queryAllUserOut()
	{
		this.users = this.sysUserService.queryAllUserOut();
		return Action.SUCCESS;
	}

	@Transactional
	public String queryAllUser()
	{
		this.users = this.sysUserService.queryAllUser();
		return Action.SUCCESS;
	}

	@Transactional
	public String queryUserOutWithCheckItemById()
	{
		if (id == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		this.user = this.sysUserService.queryUserOutWithCheckItemById(id);
		return Action.SUCCESS;
	}

	public SysUserOut getUser() {
		return this.user;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SysUserOut> getUsers() {
		return users;
	}
}
