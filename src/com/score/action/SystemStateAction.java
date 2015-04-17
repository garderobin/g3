package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.SystemState;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class SystemStateAction extends ActionSupport 
{
	private SystemStateService systemStateService;
	
	private SystemState systemState;

	@Transactional
	public String changeToNextStep()
	{
		ErrorType errorCode;
		this.systemState = this.systemStateService.getSystemState();
		if (!this.systemStateService.isEnabled().equals(1))
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		else
		{
			this.systemStateService.changeToDisabled();
			if ((errorCode = this.systemStateService.changeToNextStep()) == ErrorType.NO_ERROR)
			{
				this.systemStateService.changeToEnabled();
				this.systemState = this.systemStateService.getSystemState();
				return Action.SUCCESS;
			}
			this.systemStateService.changeToEnabled();
		}
		this.systemState = this.systemStateService.getSystemState();
		FieldErrorGenerator.addFieldError(this, errorCode);
		return Action.INPUT;
	}

	@Transactional
	public String querySystemState()
	{
		this.systemState = this.systemStateService.getSystemState();
		return Action.SUCCESS;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

}
