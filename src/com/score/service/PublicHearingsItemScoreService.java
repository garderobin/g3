package com.score.service;

import org.springframework.transaction.annotation.Transactional;

import com.score.bean.PublicHearingsItem;
import com.score.bean.PublicHearingsItemScore;
import com.score.bean.SysUserOut;
import com.score.dao.PublicHearingsItemDao;
import com.score.dao.PublicHearingsItemScoreDao;

public class PublicHearingsItemScoreService 
{
	private PublicHearingsItemScoreDao publicHearingsItemScoreDao;
	private PublicHearingsItemDao publicHearingsItemDao;
	
	@Transactional
	public boolean setInfo(SysUserOut user, Long itemId, Double value, Long rank)
	{
		PublicHearingsItem item = this.publicHearingsItemDao.queryItemById(itemId);
		if (item == null)
			return false;
		PublicHearingsItemScore info = this.publicHearingsItemScoreDao.queryByInfoTargetAndItemId(user.getId(), itemId);
		if (info == null)
			info = new PublicHearingsItemScore();
		info.setPublicHearingsItem(item);
		info.setInfoTarget(user);
		info.setValue(value);
		info.setRank(rank);
		this.publicHearingsItemScoreDao.merge(info);
		return true;
	}

	public void setPublicHearingsItemScoreDao(PublicHearingsItemScoreDao publicHearingsItemScoreDao) {
		this.publicHearingsItemScoreDao = publicHearingsItemScoreDao;
	}

	public void setPublicHearingsItemDao(PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}
}
