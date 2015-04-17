package com.score.action;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.SysUserOut;
import com.score.bean.Thought;
import com.score.dao.CollectionItemDao;
import com.score.dao.ThoughtDao;
import com.score.service.SysUserService;

@SuppressWarnings("serial")
public class CreateThoughtAction extends ActionSupport {
	private CollectionItemDao collectionItemDao;
	private SysUserService sysUserService;
	private ThoughtDao thoughtDao;
	
	private Long itemId;			        //project的id
	private String message;					//感想内容
	private Thought thought;				//当前创建的感想
	private String gitIdentifier;
	private Boolean publish;
	
	@Transactional
	public String execute() {
		
		CollectionItem collectionItem = null;
		SysUserOut infoProvider = this.sysUserService.findCurrentUser();
		
		if(this.itemId != null){
			collectionItem = collectionItemDao.queryItemById(this.itemId);
		}
				
		thought = new Thought();
		thought.setMessage(this.message);
		thought.setCollectionItem(collectionItem);
		thought.setScore(null);
		thought.setInfoProvider(infoProvider);
		thought.setPublish(publish);
		thought.setGitIdentifier(gitIdentifier);
		
		this.thoughtDao.save(thought);
		
		return Action.SUCCESS;
	}

	public void setThoughtDao(ThoughtDao thoughtDao) {
		this.thoughtDao = thoughtDao;
	}

	public Thought getThought() {
		return thought;
	}

	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPublish(Boolean publish) {
		this.publish = publish;
	}

	public void setGitIdentifier(String gitIdentifier) {
		this.gitIdentifier = gitIdentifier;
	}

	
}
