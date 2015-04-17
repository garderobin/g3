package com.score.service;

import org.springframework.transaction.annotation.Transactional;

import com.score.bean.PublicHearingsItem;
import com.score.bean.PublicHearingsItemInfoText;
import com.score.bean.SysUserOut;
import com.score.dao.PublicHearingsItemDao;
import com.score.dao.PublicHearingsItemInfoTextDao;

public class PublicHearingsItemInfoTextService 
{
	private PublicHearingsItemInfoTextDao publicHearingsItemInfoTextDao;
	private PublicHearingsItemDao publicHearingsItemDao;
	
	@Transactional
	public boolean setInfo(SysUserOut user, Long itemId, SysUserOut infoTarget, String text)
	{
		PublicHearingsItem item = this.publicHearingsItemDao.queryItemById(itemId);
		if (item == null || (int)item.getType() != 1)
			return false;
		PublicHearingsItemInfoText info = this.publicHearingsItemInfoTextDao.queryByInfoProviderAndInfoTargetAndItemId(user.getId(), infoTarget.getId(), itemId);
		if (info == null)
			info = new PublicHearingsItemInfoText();
		info.setPublicHearingsItem(item);
		info.setInfoProvider(user);
		info.setInfoTarget(infoTarget);
		info.setText(text);
		this.publicHearingsItemInfoTextDao.merge(info);
		return true;
	}

	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

	public void setPublicHearingsItemInfoTextDao(
			PublicHearingsItemInfoTextDao publicHearingsItemInfoTextDao) {
		this.publicHearingsItemInfoTextDao = publicHearingsItemInfoTextDao;
	}

}
