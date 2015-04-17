package com.score.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.SystemState;
import com.score.bean.Thought;
import com.score.dao.CollectionItemDao;
import com.score.dao.ThoughtDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;

//某个学生查询自己的感想
@SuppressWarnings("serial")
public class QueryThoughtAction extends ActionSupport {
	private Long itemId;    			   //当前所在项目
	private Long userQueryId;
	
	private List<Thought> thoughtList;			
	private List<CollectionItem> collectionItemList;
	
	private ThoughtDao thoughtDao;
	private SysUserService sysUserService;	
	private CollectionItemDao collectionItemDao;
	private SystemStateService systemStateService;
	
	private SystemState systemState;
	
	@Transactional
	public String queryByInfoProviderAndItemId()
	{
		this.systemState = this.systemStateService.getSystemState();
		
		if (userQueryId == null) {
			userQueryId = sysUserService.findCurrentUser().getId();
		}
		
		collectionItemList = collectionItemDao.queryItemAll();
		if (this.itemId != null) {
			thoughtList = thoughtDao.queryByInfoProviderAndItemId(userQueryId, this.itemId);
		}else{
			thoughtList = new ArrayList<Thought>(); 
		}
		
		return Action.SUCCESS;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Long getUserQueryId() {
		return userQueryId;
	}

	public void setUserQueryId(Long userQueryId) {
		this.userQueryId = userQueryId;
	}

	public List<Thought> getThoughtList() {
		return thoughtList;
	}

	public void setThoughtDao(ThoughtDao thoughtDao) {
		this.thoughtDao = thoughtDao;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public List<CollectionItem> getCollectionItemList() {
		return collectionItemList;
	}

	public void setCollectionItemList(List<CollectionItem> collectionItemList) {
		this.collectionItemList = collectionItemList;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}
	
}
