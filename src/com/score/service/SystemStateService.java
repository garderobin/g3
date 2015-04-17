package com.score.service;

import org.springframework.transaction.annotation.Transactional;

import com.score.bean.CollectionItem;
import com.score.bean.PublicHearingsItem;
import com.score.bean.SystemState;
import com.score.dao.SystemStateDao;
import com.score.util.ErrorType;

@Transactional
public class SystemStateService 
{
	public enum CheckResult
	{
		NotSuitable,
		Suitable
	}
	
	private SystemStateDao systemStateDao;
	
	private CalculateScoreService calculateScoreService;
	
	/*
	 * 0 评分软件
	 * 1 助教软件
	 */
	
	private int systemType;
	
	/*
	1 设定阶段
	2 收集阶段
	3 认定阶段
	4 预留阶段
	5 预留阶段
	6 公示阶段
	7 生成报表
	8 运行结束
	*/
	
	@Transactional
	public SystemState getSystemState()
	{
		return this.systemStateDao.query();
	}

	@Transactional
	public ErrorType changeToNextStep()
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getState() == 8)
		{
			return ErrorType.SYSTEM_STATE_NOT_SUITABLE;
		}
		if (systemState.getState() == 3)
		{
			this.calculateScoreService.calculateAll();
			systemState.setState(6);
		}
		else
		{
			systemState.setState(systemState.getState() + 1);
		}
		this.systemStateDao.merge(systemState);
		return ErrorType.NO_ERROR;
	}
	
	@Transactional
	public Integer isEnabled()
	{
		SystemState systemState = this.systemStateDao.query();
		return systemState.getEnabled();
	}
	
	@Transactional
	public void changeToDisabled()
	{
		SystemState systemState = this.systemStateDao.query();
		systemState.setEnabled(0);
		this.systemStateDao.merge(systemState);
	}
	
	@Transactional
	public void changeToEnabled()
	{
		SystemState systemState = this.systemStateDao.query();
		systemState.setEnabled(1);
		this.systemStateDao.merge(systemState);
	}
	
	@Transactional
	public CheckResult editCollectionItem()
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
			return CheckResult.Suitable;
		else if (systemState.getState() == 1 && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		return CheckResult.NotSuitable;
	}
	
	@Transactional
	public CheckResult editPublicHearingsItem()
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
			return CheckResult.Suitable;
		else if (systemState.getState() == 1 && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		return CheckResult.NotSuitable;
	}
	
	@Transactional
	public CheckResult editCollectionItemCheck()
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
			return CheckResult.Suitable;
		else if (systemState.getState() <= 2 && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		return CheckResult.NotSuitable;
	}
	
	@Transactional
	public CheckResult editCollectionItemPublicity()
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
			return CheckResult.Suitable;
		else if (systemState.getState() < 6 && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		return CheckResult.NotSuitable;
	}
	
	@Transactional
	public CheckResult fillCollectionItemInfo(CollectionItem item)
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
		{
			if (item != null && item.ifFillable())
				return CheckResult.Suitable;
			else if (item == null)
				return CheckResult.Suitable;
			else
				return CheckResult.NotSuitable;
		}
		else if (systemState.getState() == 2 && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		else
			return CheckResult.NotSuitable;
	}
	
	@Transactional
	public CheckResult fillCollectionItemInfoCheck()
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
			return CheckResult.Suitable;
		else if (systemState.getState() == 3 && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		return CheckResult.NotSuitable;
	}
	
	@Transactional
	public CheckResult fillPublicHearingsItemInfo(PublicHearingsItem item)
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
		{
			if (item.ifCheckable())
				return CheckResult.Suitable;
			else
				return CheckResult.NotSuitable;
		}
		else if ((systemState.getState() == 2 || systemState.getState() == 3) && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		else
			return CheckResult.NotSuitable;
	}
	
	@Transactional
	public CheckResult queryPublicityInformation()
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
			return CheckResult.Suitable;
		else if (systemState.getState() == 6 && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		return CheckResult.NotSuitable;
	}
	
	@Transactional
	public CheckResult queryPublicHearingsItemInfoText()
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
			return CheckResult.Suitable;
		else if ((systemState.getState() == 6 || systemState.getState() == 7) && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		return CheckResult.NotSuitable;
	}
	
	@Transactional
	public CheckResult generateReport()
	{
		SystemState systemState = this.systemStateDao.query();
		if (systemState.getSystemType() == 1)
			return CheckResult.Suitable;
		else if ((systemState.getState() == 6 || systemState.getState() == 7) && systemState.getEnabled() == 1)
			return CheckResult.Suitable;
		return CheckResult.NotSuitable;
	}

	public void setCalculateScoreService(CalculateScoreService calculateScoreService) {
		this.calculateScoreService = calculateScoreService;
	}
	
	public void setSystemStateDao(SystemStateDao systemStateDao) {
		this.systemStateDao = systemStateDao;
	}

	public int getSystemType() {
		return systemType;
	}

	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}
}
