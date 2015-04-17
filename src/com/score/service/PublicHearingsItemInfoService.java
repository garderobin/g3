package com.score.service;

import org.springframework.transaction.annotation.Transactional;

import com.score.bean.PublicHearingsItem;
import com.score.bean.PublicHearingsItemInfo;
import com.score.bean.SysUserOut;
import com.score.dao.PublicHearingsItemDao;
import com.score.dao.PublicHearingsItemInfoDao;

public class PublicHearingsItemInfoService 
{
	private PublicHearingsItemInfoDao publicHearingsItemInfoDao;
	private PublicHearingsItemDao publicHearingsItemDao;
	
	@Transactional
	public boolean setInfo(SysUserOut user, Long itemId, SysUserOut infoTarget, Integer value)
	{
		PublicHearingsItem item = this.publicHearingsItemDao.queryItemById(itemId);
		if (item == null || (int)item.getType() != 0)
			return false;
		PublicHearingsItemInfo info = this.publicHearingsItemInfoDao.queryByInfoProviderAndInfoTargetAndItemId(user.getId(), infoTarget.getId(), itemId);
		if (info == null)
			info = new PublicHearingsItemInfo();
		info.setPublicHearingsItem(item);
		info.setInfoProvider(user);
		info.setInfoTarget(infoTarget);
		info.setValue(value);
		this.publicHearingsItemInfoDao.merge(info);
		return true;
	}

	public void setPublicHearingsItemInfoDao(
			PublicHearingsItemInfoDao publicHearingsItemInfoDao) {
		this.publicHearingsItemInfoDao = publicHearingsItemInfoDao;
	}

	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

}
