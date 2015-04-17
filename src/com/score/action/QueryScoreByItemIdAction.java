package com.score.action;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemScore;
import com.score.bean.PublicHearingsItem;
import com.score.bean.PublicHearingsItemScore;
import com.score.bean.SystemState;
import com.score.bean.TotalScore;
import com.score.dao.CollectionItemScoreDao;
import com.score.dao.CollectionItemDao;
import com.score.dao.PublicHearingsItemScoreDao;
import com.score.dao.PublicHearingsItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.service.TotalScoreService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class QueryScoreByItemIdAction extends ActionSupport 
{
	private Integer type;
	/*
	 * 0 all
	 * 1 collectionItem
	 * 2 publicHearingsItem
	 */
	
	private Long itemId;
	
	private Long totalUserSize;
	
	private List<CollectionItemScore> collectionItemScores;
	
	private CollectionItemScore collectionItemScore;
	
	private PublicHearingsItemScore publicHearingsItemScore;
	
	private SystemState systemState;
	
	private CollectionItem collectionItem;
	
	private PublicHearingsItem publicHearingsItem;
	
	private TotalScore totalScore;
	
	private CollectionItemDao collectionItemDao;
	
	private CollectionItemScoreDao collectionItemScoreDao;
	
	private PublicHearingsItemDao publicHearingsItemDao;
	
	private PublicHearingsItemScoreDao publicHearingsItemScoreDao;
	
	private TotalScoreService totalScoreService;
	
	private SystemStateService systemStateService;
	
	private SysUserService sysUserService;

	@Transactional
	public String execute()
	{
		this.systemState = this.systemStateService.getSystemState();
		if (this.systemStateService.queryPublicityInformation() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		if (type == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		if (itemId == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		Integer publicityType;
		if (type == 0)
		{
			this.totalScore = this.totalScoreService.queryByInfoTarget(this.sysUserService.findCurrentUser().getId());
		}
		else if (type == 1)
		{
			this.collectionItem = this.collectionItemDao.queryItemById(itemId);
			publicityType = this.collectionItem.getPublicityType();
			if (publicityType.equals(1) || publicityType.equals(2))
			{
				this.collectionItemScore = this.collectionItemScoreDao.queryByInfoTargetAndItemId(this.sysUserService.findCurrentUser().getId(), itemId);
				if (publicityType.equals(1))
					this.collectionItemScore.setRank(null);
			}
			else if (publicityType.equals(3))
			{
				this.collectionItemScores = this.collectionItemScoreDao.queryByItemId(itemId);
			}
		}
		else if (type == 2)
		{
			this.publicHearingsItem = this.publicHearingsItemDao.queryItemById(itemId);
			this.publicHearingsItemScore = this.publicHearingsItemScoreDao.queryByInfoTargetAndItemId(this.sysUserService.findCurrentUser().getId(), itemId);
		}
		this.totalUserSize = this.sysUserService.queryUserNumber();
		return Action.SUCCESS;
	}

	public List<CollectionItemScore> getCollectionItemScores() {
		return collectionItemScores;
	}

	public CollectionItemScore getCollectionItemScore() {
		return collectionItemScore;
	}

	public PublicHearingsItemScore getPublicHearingsItemScore() {
		return publicHearingsItemScore;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public CollectionItem getCollectionItem() {
		return collectionItem;
	}

	public PublicHearingsItem getPublicHearingsItem() {
		return publicHearingsItem;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setCollectionItemScoreDao(
			CollectionItemScoreDao collectionItemScoreDao) {
		this.collectionItemScoreDao = collectionItemScoreDao;
	}

	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

	public void setPublicHearingsItemScoreDao(
			PublicHearingsItemScoreDao publicHearingsItemScoreDao) {
		this.publicHearingsItemScoreDao = publicHearingsItemScoreDao;
	}
	
	public void setTotalScoreService(TotalScoreService totalScoreService) {
		this.totalScoreService = totalScoreService;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public Integer getType() {
		return type;
	}

	public Long getItemId() {
		return itemId;
	}

	public Long getTotalUserSize() {
		return totalUserSize;
	}

	public TotalScore getTotalScore() {
		return totalScore;
	}
}
