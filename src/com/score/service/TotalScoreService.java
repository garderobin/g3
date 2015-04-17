package com.score.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.score.bean.SysUserOut;
import com.score.bean.TotalScore;
import com.score.dao.TotalScoreDao;

public class TotalScoreService 
{
	private TotalScoreDao totalScoreDao;
	
	@Transactional
	public boolean setInfo(SysUserOut user, Double value, Long rank)
	{
		TotalScore info = queryByInfoTarget(user.getId());
		if (info == null)
			info = new TotalScore();
		info.setInfoTarget(user);
		info.setValue(value);
		info.setRank(rank);
		this.totalScoreDao.merge(info);
		return true;
	}
	
	//use when generate report
	@Transactional
	public List<TotalScore> queryAll()
	{
		return this.totalScoreDao.queryAll();
	}
	
	//Use when setInfo
	@Transactional
	public TotalScore queryByInfoTarget(Long infoTarget)
	{
		return this.totalScoreDao.queryByInfoTarget(infoTarget);
	}

	public void setPublicHearingsItemScoreDao(TotalScoreDao totalScoreDao) {
		this.totalScoreDao = totalScoreDao;
	}

	public void setTotalScoreDao(TotalScoreDao totalScoreDao) {
		this.totalScoreDao = totalScoreDao;
	}
}
