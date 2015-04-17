package com.score.action;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class CreateCollectionItemCheckAction extends ActionSupport 
{
	private SystemStateService systemStateService;
	private CollectionItemDao collectionItemDao;
	private SysUserService sysUserService;
	
	private Long id;
	
	private Long[] checkOperatorId;
	
	@Transactional
	public String execute()
	{
		if (this.systemStateService.editCollectionItemCheck() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		CollectionItem item = null;
		if (this.id != null && this.id > 0)
			item = this.collectionItemDao.queryItemById(this.id);
		if (item == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		
		List<SysUserOut> checkOperator = item.getCheckOperator();
		if (this.checkOperatorId == null || this.checkOperatorId.length == 0)
		{
			checkOperator.clear();
			this.collectionItemDao.merge(item);
			return Action.SUCCESS;
		}
		
		int k = this.checkOperatorId.length;
		int k2 = checkOperator.size();
		if (k2 < k) k = k2;
		int i;
		SysUserOut tempUser;
		for (i = 0; i < k; i++)
			if (checkOperator.get(i).getId() != this.checkOperatorId[i])
			{
				tempUser = this.sysUserService.queryUserOutById(this.checkOperatorId[i]);
				//tempUser.getCollectionItems().add(itemWithInfo);
				checkOperator.set(i, tempUser);
			}
		if (k2 > k)
			for (; i < k2; i++)
				checkOperator.remove(k);
		else
			for (; i < this.checkOperatorId.length; i++)
			{
				tempUser = this.sysUserService.queryUserOutById(this.checkOperatorId[i]);
				//tempUser.getCollectionItems().add(itemWithInfo);
				checkOperator.add(tempUser);
			}
				
		this.collectionItemDao.merge(item);
		return Action.SUCCESS;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setCheckOperatorId(Long[] checkOperatorId) {
		this.checkOperatorId = checkOperatorId;
	}
}
