package com.score.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.PublicHearingsItem;
import com.score.bean.PublicHearingsItemInfo;
import com.score.bean.PublicHearingsItemInfoText;
import com.score.bean.SysUserOut;
import com.score.bean.SystemState;
import com.score.dao.PublicHearingsItemInfoDao;
import com.score.dao.PublicHearingsItemInfoTextDao;
import com.score.dao.PublicHearingsItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryUserOutWithPublicHearingsItemInfoAndTextAction extends ActionSupport 
{
	private SysUserService sysUserService;
	private SystemStateService systemStateService;
	private PublicHearingsItemInfoDao publicHearingsItemInfoDao;
	private PublicHearingsItemInfoTextDao publicHearingsItemInfoTextDao;
	private PublicHearingsItemDao publicHearingsItemDao;
	
	private Long itemId;
	
	private List<SysUserOut> users;
	
	private PublicHearingsItem publicHearingsItem;
	private SystemState systemState;

	@Transactional
	public String queryItemInfoAndTextByItemId()
	{
		if (itemId != null && itemId > 0)
			this.publicHearingsItem = this.publicHearingsItemDao.queryItemById(itemId);
		if (this.publicHearingsItem == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PUBLIC_HEARINGS_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		this.systemState = this.systemStateService.getSystemState();
		if (this.systemStateService.fillPublicHearingsItemInfo(publicHearingsItem) != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		SysUserOut currentUser = this.sysUserService.findCurrentUser();

		boolean t = false;		
		this.users = this.sysUserService.queryAllUserOut();
		List<PublicHearingsItemInfo> allInfoList = this.publicHearingsItemInfoDao.queryByInfoProviderAndItemId(currentUser.getId(), itemId);
		List<PublicHearingsItemInfoText> allInfoTextList = this.publicHearingsItemInfoTextDao.queryByInfoProviderAndItemId(currentUser.getId(), itemId);
		int i = 0, j = 0, k = 0, ci = users.size(), cj = allInfoList.size(), ck = allInfoTextList.size();
		PublicHearingsItemInfo itemInfo = null;
		PublicHearingsItemInfoText itemInfoText = null;
		while (i < ci)
		{
			SysUserOut user = this.users.get(i);
			t = false;
			if (j < cj)
			{
				itemInfo = allInfoList.get(j);
				while (itemInfo.getInfoTarget().getId() < user.getId() && j < cj - 1)
				{
					j++;
					itemInfo = allInfoList.get(j);
				}
				if (itemInfo.getInfoTarget().getId().equals(user.getId()))
				{
					itemInfo.setInfoTarget(user);
					List<PublicHearingsItemInfo> itemInfos = new ArrayList<PublicHearingsItemInfo>();
					itemInfos.add(itemInfo);
					user.setPublicHearingsItemInfoTarget(itemInfos);
					t = true;
					j++;
				}
			}
			if (!t)
				user.setPublicHearingsItemInfoTarget(new ArrayList<PublicHearingsItemInfo>());

			t = false;
			if (k < ck)
			{
				itemInfoText = allInfoTextList.get(k);
				while (itemInfoText.getInfoTarget().getId() < user.getId() && k < ck - 1)
				{
					k++;
					itemInfoText = allInfoTextList.get(k);
				}
				if (itemInfoText.getInfoTarget().getId().equals(user.getId()))
				{
					itemInfoText.setInfoTarget(user);
					List<PublicHearingsItemInfoText> itemInfoTexts = new ArrayList<PublicHearingsItemInfoText>();
					itemInfoTexts.add(itemInfoText);
					user.setPublicHearingsItemInfoTextTarget(itemInfoTexts);
					t = true;
					k++;
				}
			}
			if (!t)
				user.setPublicHearingsItemInfoTextTarget(new ArrayList<PublicHearingsItemInfoText>());

			i++;
		}
		return Action.SUCCESS;
	}
	
	public List<SysUserOut> getUsers() {
		return users;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setPublicHearingsItemInfoDao(
			PublicHearingsItemInfoDao publicHearingsItemInfoDao) {
		this.publicHearingsItemInfoDao = publicHearingsItemInfoDao;
	}

	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

	public PublicHearingsItem getPublicHearingsItem() {
		return publicHearingsItem;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setPublicHearingsItemInfoTextDao(
			PublicHearingsItemInfoTextDao publicHearingsItemInfoTextDao) {
		this.publicHearingsItemInfoTextDao = publicHearingsItemInfoTextDao;
	}
	
}
