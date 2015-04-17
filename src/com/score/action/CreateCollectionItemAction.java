package com.score.action;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.dao.CollectionItemDao;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class CreateCollectionItemAction extends ActionSupport 
{
	private SystemStateService systemStateService;
	private CollectionItemDao collectionItemDao;
	
	private Long id;
	private String itemId;
	private String name;
	private String contents;
	private String checkRequirement;
	private String codeDirectory;
	private Short type;
	private Integer minScore, maxScore, defaultScore, averageScore, deltaScore;
	private String ifUserSubmitText, ifUserSubmitPic, ifUserSubmitCode, ifCollection, ifCheck;
	private Date startFillingTime, endFillingTime;
	
	private CollectionItem collectionItem;

	@Transactional
	public String execute()
	{
		if (this.systemStateService.editCollectionItem() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		this.collectionItem = null;
		if (id != null && id > 0)
			collectionItem = this.collectionItemDao.queryItemById(id);
		if (collectionItem == null)
			collectionItem = new CollectionItem();
		collectionItem.setItemId(itemId);
		collectionItem.setName(name);
		collectionItem.setContents(contents);
		collectionItem.setCheckRequirement(checkRequirement);
		collectionItem.setCodeDirectory(codeDirectory);
		collectionItem.setType(type);
		collectionItem.setAverageScore(averageScore);
		collectionItem.setMaxScore(maxScore);
		collectionItem.setMinScore(minScore);
		collectionItem.setDefaultScore(defaultScore);
		collectionItem.setDeltaScore(deltaScore);
		collectionItem.setStartFillingTime(startFillingTime);
		collectionItem.setEndFillingTime(endFillingTime);
		if (ifUserSubmitText != null && ifUserSubmitText.equals("on"))
			collectionItem.setIfUserSubmitText(true);
		else
			collectionItem.setIfUserSubmitText(false);
		if (ifUserSubmitPic != null && ifUserSubmitPic.equals("on"))
			collectionItem.setIfUserSubmitPic(true);
		else
			collectionItem.setIfUserSubmitPic(false);
		if (ifUserSubmitCode != null && ifUserSubmitCode.equals("on"))
			collectionItem.setIfUserSubmitCode(true);
		else
			collectionItem.setIfUserSubmitCode(false);
		if (ifCollection != null && ifCollection.equals("on"))
			collectionItem.setIfCollection(true);
		else
			collectionItem.setIfCollection(false);
		if (ifCheck != null && ifCheck.equals("on"))
			collectionItem.setIfCheck(true);
		else
			collectionItem.setIfCheck(false);
		if (collectionItem.getId() != null && collectionItem.getId() > 0)
			this.collectionItemDao.merge(collectionItem);
		else
			this.collectionItemDao.save(collectionItem);
		return Action.SUCCESS;
	}

	public CollectionItem getCollectionItem() {
		return collectionItem;
	}

	public void setCollectionItem(CollectionItem collectionItem) {
		this.collectionItem = collectionItem;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setCheckRequirement(String checkRequirement) {
		this.checkRequirement = checkRequirement;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public void setMinScore(Integer minScore) {
		this.minScore = minScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	public void setDefaultScore(Integer defaultScore) {
		this.defaultScore = defaultScore;
	}

	public void setAverageScore(Integer averageScore) {
		this.averageScore = averageScore;
	}

	public void setDeltaScore(Integer deltaScore) {
		this.deltaScore = deltaScore;
	}

	public void setIfUserSubmitText(String ifUserSubmitText) {
		this.ifUserSubmitText = ifUserSubmitText;
	}

	public void setIfUserSubmitPic(String ifUserSubmitPic) {
		this.ifUserSubmitPic = ifUserSubmitPic;
	}

	public void setCodeDirectory(String codeDirectory) {
		this.codeDirectory = codeDirectory;
	}

	public void setIfUserSubmitCode(String ifUserSubmitCode) {
		this.ifUserSubmitCode = ifUserSubmitCode;
	}

	public void setIfCollection(String ifCollection) {
		this.ifCollection = ifCollection;
	}

	public void setIfCheck(String ifCheck) {
		this.ifCheck = ifCheck;
	}

	public void setStartFillingTime(Date startFillingTime) {
		this.startFillingTime = startFillingTime;
	}

	public void setEndFillingTime(Date endFillingTime) {
		this.endFillingTime = endFillingTime;
	}

}
