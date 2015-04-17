package com.score.action;

import java.util.ArrayList;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemDao;
import com.score.service.SysUserService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;
import com.score.util.git.FileInfo;
import com.score.util.git.GitUtil;
import com.score.util.git.LogInfo;

@SuppressWarnings("serial")
public class QueryGitAction extends ActionSupport
{
	private SysUserService sysUserService;
	private CollectionItemDao collectionItemDao;
	
	private String rootpath;
	
	private String name;
	private String path;
	private String version;
	
	private Long userId;
	private Long itemId;
	
	private ArrayList<LogInfo> versions = new ArrayList<LogInfo>();
	private ArrayList<FileInfo> files = new ArrayList<FileInfo>();
	private LogInfo log;
	private int count;
	private String currentpath;
	private String upperpath;
	private String content;
		
	public String queryVersions() throws Exception 
	{
		ErrorType errorType = checkRootpath();
		if (errorType != ErrorType.NO_ERROR)
		{
			FieldErrorGenerator.addFieldError(this, errorType);
			return Action.INPUT;
		}
		GitUtil gitUtil = new GitUtil(rootpath);
		versions = gitUtil.getVersions();
		count = versions.size();
		return Action.SUCCESS;
	}
	
	public String queryDirectory() throws Exception 
	{
		ErrorType errorType = checkRootpath();
		if (errorType != ErrorType.NO_ERROR)
		{
			FieldErrorGenerator.addFieldError(this, errorType);
			return Action.INPUT;
		}
		GitUtil gitUtil = new GitUtil(rootpath);
		if (path == null)
			path = "";
		files = gitUtil.showFiles(version, rootpath, path);
		log = gitUtil.showLog(version);
		upperpath = gitUtil.getUpperpath(path);
		currentpath = path;
		return Action.SUCCESS;
	}
	
	public String queryFile() throws Exception {
		ErrorType errorType = checkRootpath();
		if (errorType != ErrorType.NO_ERROR)
		{
			FieldErrorGenerator.addFieldError(this, errorType);
			return Action.INPUT;
		}
		GitUtil gitUtil = new GitUtil(rootpath);
		content = gitUtil.getFileContent(version, name);
		log = gitUtil.showLog(version);
		currentpath = path;

		return Action.SUCCESS;
	}
	
	private ErrorType checkRootpath()
	{
		SysUserOut user = null;
		if (userId != null)
			user = sysUserService.queryUserOutById(userId);
		else
			user = sysUserService.findCurrentUser();
		if (user == null)
			return ErrorType.USER_NOT_EXIST;
		
		if (user.getGitDirectory() == null || "".equals(user.getGitDirectory()))
			return ErrorType.GIT_PATH_NOT_REGISTERED;
		else
			rootpath = rootpath + user.getGitDirectory();
		if (rootpath.charAt(rootpath.length() - 1) != '/')
			rootpath = rootpath + "/";
		
		CollectionItem item = null;
		if (itemId != null)
			item = collectionItemDao.queryItemById(itemId);
		if (item == null)
			return ErrorType.COLLECTION_ITEM_NOT_EXIST;
		
		SysUserOut currentUser = sysUserService.findCurrentUser();
		if ((!item.checkCheckOperator(currentUser)) && 
				!currentUser.getId().equals(userId))
		{
			return ErrorType.USER_IS_NOT_IN_THE_CHECKOPERATORS_LIST_NOR_INFORPROVIDER;
		}
		
		if (item.getCodeDirectory() == null || "".equals(item.getCodeDirectory()))
			return ErrorType.GIT_PATH_NOT_REGISTERED;
		else
			rootpath = rootpath + item.getCodeDirectory();
		if (rootpath.charAt(rootpath.length() - 1) != '/')
			rootpath = rootpath + "/";
			
		return ErrorType.NO_ERROR;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRootpath(String rootpath)
	{
		this.rootpath = rootpath;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ArrayList<LogInfo> getVersions() {
		return versions;
	}
	
	public String getVersion() {
		return version;
	}
	
	public LogInfo getLog() {
		return log;
	}
	
	public ArrayList<FileInfo> getFiles() {
		return files;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getUpperpath() {
		return upperpath;
	}
	
	public String getCurrentpath() {
		return currentpath;
	}

	public String getName() {
		return name;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

}
