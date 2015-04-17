package com.score.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemInfo;
import com.score.bean.CollectionItemInfoCheck;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemInfoCheckDao;
import com.score.dao.CollectionItemInfoDao;
import com.score.dao.CollectionItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryUserOutWithItemInfoAction extends ActionSupport 
{
	private SysUserService sysUserService;
	private SystemStateService systemStateService;
	private CollectionItemInfoDao collectionItemInfoDao;
	private CollectionItemInfoCheckDao collectionItemInfoCheckDao;
	private CollectionItemDao collectionItemDao;
	
	private Long itemId;
	
	private List<SysUserOut> users;
	
	private CollectionItem collectionItem;

	@Transactional
	public String queryUserOutWithItemInfoByItemId()
	{
		if (this.systemStateService.fillCollectionItemInfoCheck() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		if (itemId != null && itemId > 0)
			this.collectionItem = this.collectionItemDao.queryItemWithCheckOperatorsById(itemId);
		if (this.collectionItem == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		SysUserOut currentUser = this.sysUserService.findCurrentUser();
		
		if (collectionItem.checkCheckOperator(currentUser) == false)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.USER_IS_NOT_IN_THE_CHECKOPERATORS_LIST);
			return Action.INPUT;
		}
		
		boolean t;
		
		this.users = this.sysUserService.queryAllUserOut();
		List<CollectionItemInfo> allInfoList = this.collectionItemInfoDao.queryByItemId(itemId);
		List<CollectionItemInfoCheck> allInfoCheckList = this.collectionItemInfoCheckDao.queryByInfoProviderAndItemId(currentUser.getId(), itemId);
		int i = 0, j = 0, k = 0, ci = users.size(), cj = allInfoList.size(), ck = allInfoCheckList.size();
		CollectionItemInfo itemInfo = null;
		CollectionItemInfoCheck itemInfoCheck = null;
		while (i < ci)
		{
			SysUserOut user = this.users.get(i);
			t = false;
			if (j < cj)
			{
				itemInfo = allInfoList.get(j);
				while (itemInfo.getInfoProvider().getId() < user.getId() && j < cj - 1)
				{
					j++;
					itemInfo = allInfoList.get(j);
				}
				if (itemInfo.getInfoProvider().getId().equals(user.getId()))
				{
					itemInfo.setInfoProvider(user);
					List<CollectionItemInfo> itemInfos = new ArrayList<CollectionItemInfo>();
					itemInfos.add(itemInfo);
					user.setCollectionItemInfo(itemInfos);
					t = true;
					j++;
				}
			}
			if (!t)
				user.setCollectionItemInfo(new ArrayList<CollectionItemInfo>());
			
			t = false;
			if (k < ck)
			{
				itemInfoCheck = allInfoCheckList.get(k);
				while (itemInfoCheck.getInfoTarget().getId() < user.getId() && k < ck - 1)
				{
					k++;
					itemInfoCheck = allInfoCheckList.get(k);
				}
				if (itemInfoCheck.getInfoTarget().getId().equals(user.getId()))
				{
					itemInfoCheck.setInfoTarget(user);
					List<CollectionItemInfoCheck> itemInfoChecks = new ArrayList<CollectionItemInfoCheck>();
					itemInfoChecks.add(itemInfoCheck);
					user.setCollectionItemInfoCheckTarget(itemInfoChecks);
					t = true;
					k++;
				}
			}
			if (!t)
				user.setCollectionItemInfoCheckTarget(new ArrayList<CollectionItemInfoCheck>());
			
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

	public void setCollectionItemInfoDao(
			CollectionItemInfoDao collectionItemInfoDao) {
		this.collectionItemInfoDao = collectionItemInfoDao;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public Long getItemId() {
		return itemId;
	}

	public CollectionItem getCollectionItem() {
		return collectionItem;
	}

	public void setCollectionItemInfoCheckDao(
			CollectionItemInfoCheckDao collectionItemInfoCheckDao) {
		this.collectionItemInfoCheckDao = collectionItemInfoCheckDao;
	}
	
}
