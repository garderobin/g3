package com.score.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemInfo;
import com.score.bean.SysUserOut;
import com.score.bean.SystemState;
import com.score.dao.CollectionItemInfoDao;
import com.score.dao.CollectionItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryCollectionItemAction extends ActionSupport 
{
	private CollectionItemDao collectionItemDao;
	private CollectionItemInfoDao collectionItemInfoDao;
	private SystemStateService systemStateService;
	private SysUserService sysUserService;
	
	private List<CollectionItem> collectionItemList;
	private SystemState systemState;

	@Transactional
	public String queryItemAll()
	{
		this.systemState = this.systemStateService.getSystemState();
		this.collectionItemList = this.collectionItemDao.queryItemAll();
		return Action.SUCCESS;
	}

	@Transactional
	public String queryItemWhichNeedCheck()
	{
		this.systemState = this.systemStateService.getSystemState();
		this.collectionItemList = this.collectionItemDao.queryItemWhichNeedCheck();
		return Action.SUCCESS;
	}

	@Transactional
	public String queryItemWhichShallBePublicized()
	{
		this.systemState = this.systemStateService.getSystemState();
		this.collectionItemList = this.collectionItemDao.queryItemWhichShallBePublicized();
		return Action.SUCCESS;
	}

	@Transactional
	public String queryItemWithPersonalInfoAll()
	{
		this.systemState = this.systemStateService.getSystemState();
		if (this.systemStateService.fillCollectionItemInfo(null) != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		SysUserOut user = this.sysUserService.findCurrentUser();
		this.collectionItemList = this.collectionItemDao.queryItemAll();
		List<CollectionItemInfo> allItemInfoList = this.collectionItemInfoDao.queryByInfoProvider(user.getId());
		
		int i = 0, j = 0, ci = this.collectionItemList.size(), cj = allItemInfoList.size();
		boolean t;
		CollectionItemInfo itemInfo = null;
		while (i < ci)
		{
			CollectionItem item = this.collectionItemList.get(i);
			
			t = false;
			if (j < cj)
			{
				itemInfo = allItemInfoList.get(j);
				while (itemInfo.getCollectionItem().getId() < item.getId() && j < cj - 1)
				{
					j++;
					itemInfo = allItemInfoList.get(j);
				}
				if (itemInfo.getCollectionItem().getId().equals(item.getId()))
				{
					itemInfo.setCollectionItem(item);
					List<CollectionItemInfo> itemInfos = new ArrayList<CollectionItemInfo>();
					itemInfos.add(itemInfo);
					item.setCollectedItemInfo(itemInfos);
					j++;
					t = true;
				}
			}
			if (!t)
				item.setCollectedItemInfo(new ArrayList<CollectionItemInfo>());
			
			i++;
		}
		return Action.SUCCESS;
	}
	
	@Transactional
	public String queryItemByCurrentUser()
	{
		this.systemState = this.systemStateService.getSystemState();
		if (this.systemStateService.fillCollectionItemInfoCheck() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		SysUserOut user = this.sysUserService.findCurrentUser();
		if (user == null)
			return Action.ERROR;
		
		this.collectionItemList = this.collectionItemDao.queryItemByCheckOperatorId(user.getId());
		return Action.SUCCESS;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public List<CollectionItem> getCollectionItemList() {
		return this.collectionItemList;
	}

	public SystemState getSystemState() {
		return this.systemState;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setCollectionItemInfoDao(
			CollectionItemInfoDao collectionItemInfoDao) {
		this.collectionItemInfoDao = collectionItemInfoDao;
	}
}
