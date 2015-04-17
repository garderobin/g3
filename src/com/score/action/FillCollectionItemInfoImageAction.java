package com.score.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.SystemState;
import com.score.service.CollectionItemInfoImageService;
import com.score.dao.CollectionItemDao;
import com.score.service.SysUserService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class FillCollectionItemInfoImageAction extends ActionSupport
{
	private SysUserService sysUserService;
	
	private SystemStateService systemStateService;

	private CollectionItemInfoImageService collectionItemInfoImageService;
	
	private CollectionItemDao collectionItemDao;
	
	private Long itemId;
	
	private Long id;		//itemInfoImage id
	
	private File upload;
	
	private String uploadContentType;

	private SystemState systemState;

	@Transactional
	public String execute() throws IOException, SQLException
	{
		CollectionItem item = null;
		if (this.itemId != null && this.itemId > 0)
			item = this.collectionItemDao.queryItemById(this.itemId);
		if (item == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_NOT_EXIST);
			return Action.INPUT;
		}
		this.systemState = this.systemStateService.getSystemState();
		if (this.systemStateService.fillCollectionItemInfo(item) != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		if (upload == null || uploadContentType == null)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.PARAMETER_NO_GIVEN);
			return Action.INPUT;
		}
		if (item.getStartFillingTime() != null)
		{
			Date date = new Date();
			if (date.before(item.getStartFillingTime()) || date.after(item.getEndFillingTime()))
			{
				FieldErrorGenerator.addFieldError(this, ErrorType.COLLECTION_ITEM_FILLING_IS_NOT_AT_A_PROPER_ITEM_STATE);
			}
		}
		FileInputStream inputStream = new FileInputStream(this.upload);
		this.id = this.collectionItemInfoImageService.createCollectionItemInfoImage(this.sysUserService.findCurrentUser(), itemId, inputStream, uploadContentType);
		
		return Action.SUCCESS;
	}

	public Long getId() {
		return id;
	}

	public Long getItemId() {
		return itemId;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setCollectionItemInfoImageService(
			CollectionItemInfoImageService collectionItemInfoImageService) {
		this.collectionItemInfoImageService = collectionItemInfoImageService;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}
