package com.score.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemScore;
import com.score.bean.PublicHearingsItem;
import com.score.bean.PublicHearingsItemScore;
import com.score.bean.SysUserOut;
import com.score.bean.TotalScore;
import com.score.dao.CollectionItemDao;
import com.score.dao.CollectionItemScoreDao;
import com.score.dao.PublicHearingsItemDao;
import com.score.dao.PublicHearingsItemScoreDao;
import com.score.util.ReportGenerator;

public class GenerateReportService 
{
	private SysUserService sysUserService;
	
	private CollectionItemDao collectionItemDao;
	
	private PublicHearingsItemDao publicHearingsItemDao;
	
	private CollectionItemScoreDao collectionItemScoreDao;
	
	private PublicHearingsItemScoreDao publicHearingsItemScoreDao;
	
	private TotalScoreService totalScoreService;
	
	public void generateReport(String fileName) throws IOException, RowsExceededException, WriteException
	{
		ReportGenerator reportGenerator = new ReportGenerator(fileName);
		HashMap<Long, Integer> usersHash = new HashMap<Long, Integer>();
		HashMap<Long, Integer> collectionItemsHash = new HashMap<Long, Integer>();
		HashMap<Long, Integer> publicHearingsItemsHash = new HashMap<Long, Integer>();
		List<SysUserOut> users = this.sysUserService.queryAllUserOut();
		reportGenerator.addLabel("学号", 0, 0);
		reportGenerator.addLabel("姓名", 0, 1);
		reportGenerator.addLabel("总分", 0, 2);
		reportGenerator.addLabel("名次", 0, 3);
		int i = 1;
		for (SysUserOut user : users)
		{
			usersHash.put(user.getId(), i);
			reportGenerator.addLabel(user.getUsername(), i, 0);
			reportGenerator.addLabel(user.getName(), i, 1);
			i++;
		}
		i = 4;
		List<CollectionItem> collectionItems = this.collectionItemDao.queryItemAll();
		for (CollectionItem item : collectionItems)
		{
			collectionItemsHash.put(item.getId(), i);
			reportGenerator.addLabel(item.getName(), 0, i);
			i++;
		}
		List<PublicHearingsItem> publicHearingsItems = this.publicHearingsItemDao.queryItemAll();
		for (PublicHearingsItem item : publicHearingsItems)
		{
			publicHearingsItemsHash.put(item.getId(), i);
			reportGenerator.addLabel(item.getName(), 0, i);
			i++;
		}
		Integer targetR, itemC;
		List<CollectionItemScore> collectionItemScores = this.collectionItemScoreDao.queryAll();
		for (CollectionItemScore item : collectionItemScores)
		{
			targetR = usersHash.get(item.getInfoTarget().getId());
			itemC = collectionItemsHash.get(item.getCollectionItem().getId());
			if (targetR == null || itemC == null)
				continue;
			reportGenerator.addNumber(item.getValue(), targetR, itemC);
		}
		List<PublicHearingsItemScore> publicHearingsItemScores = this.publicHearingsItemScoreDao.queryAll();
		for (PublicHearingsItemScore item : publicHearingsItemScores)
		{
			targetR = usersHash.get(item.getInfoTarget().getId());
			itemC = publicHearingsItemsHash.get(item.getPublicHearingsItem().getId());
			if (targetR == null || itemC == null)
				continue;
			reportGenerator.addNumber(item.getValue(), targetR, itemC);
		}
		List<TotalScore> totalScores = this.totalScoreService.queryAll();
		for (TotalScore item : totalScores)
		{
			targetR = usersHash.get(item.getInfoTarget().getId());
			if (targetR == null)
				continue;
			reportGenerator.addNumber(item.getValue(), targetR, 2);
			reportGenerator.addNumber(item.getRank(), targetR, 3);
		}
		reportGenerator.generateReport();
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

	public void setCollectionItemScoreDao(
			CollectionItemScoreDao collectionItemScoreDao) {
		this.collectionItemScoreDao = collectionItemScoreDao;
	}

	public void setPublicHearingsItemScoreDao(
			PublicHearingsItemScoreDao publicHearingsItemScoreDao) {
		this.publicHearingsItemScoreDao = publicHearingsItemScoreDao;
	}

	public void setTotalScoreService(TotalScoreService totalScoreService) {
		this.totalScoreService = totalScoreService;
	}

}
