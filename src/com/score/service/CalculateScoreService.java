package com.score.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemInfo;
import com.score.bean.CollectionItemInfoCheck;
import com.score.bean.CollectionItemScore;
import com.score.bean.PublicHearingsItem;
import com.score.bean.PublicHearingsItemInfo;
import com.score.bean.PublicHearingsItemScore;
import com.score.bean.SysUserOut;
import com.score.bean.TotalScore;
import com.score.dao.CollectionItemDao;
import com.score.dao.CollectionItemInfoCheckDao;
import com.score.dao.CollectionItemInfoDao;
import com.score.dao.CollectionItemScoreDao;
import com.score.dao.PublicHearingsItemDao;
import com.score.dao.PublicHearingsItemInfoDao;
import com.score.dao.PublicHearingsItemScoreDao;
import com.score.util.ErrorType;

public class CalculateScoreService {
	private CollectionItemDao collectionItemDao;
	private CollectionItemInfoDao collectionItemInfoDao;
	private CollectionItemInfoCheckDao collectionItemInfoCheckDao;
	private CollectionItemScoreDao collectionItemScoreDao;
	private CollectionItemScoreService collectionItemScoreService;
	private PublicHearingsItemDao publicHearingsItemDao;
	private PublicHearingsItemInfoDao publicHearingsItemInfoDao;
	private PublicHearingsItemScoreDao publicHearingsItemScoreDao;
	private PublicHearingsItemScoreService publicHearingsItemScoreService;
	private TotalScoreService totalScoreService;
	private SysUserService sysUserService;

	public class NumList 
	{
		private Integer deltaNum = 0;
		private Integer defaultNum = 0;
		private Integer maxNum = 0;
		private Integer minNum = 0;
		private Integer averageNum;
		
		private Double averageCurrent;
		private Double averageInstance;
		private Double deltaCurrent;
		private Double deltaInstance;
		
		private List<Double> numSet = new ArrayList<Double>();
		
		public NumList(Integer defaultNum, Integer maxNum, Integer minNum, Integer averageNum, Integer deltaNum)
		{
			this.defaultNum = defaultNum;
			this.maxNum = maxNum;
			this.minNum = minNum;
			this.averageNum = averageNum;
			this.deltaNum = deltaNum;
		}
		
		public void addNum(Double num)
		{
			this.numSet.add(validateNum(num));
		}
		
		public void addNum(Integer num)
		{
			this.numSet.add(validateNum(num));
		}
		
		public void addDefaultNum()
		{
			this.numSet.add((double)defaultNum);
		}
		
		public void calculate()
		{
			calculateAverage();
		}
		
		public void calculateAverage()
		{
			double sum = 0;
			double delta_sum = 0;
			int count = this.numSet.size();
			for (Double i : this.numSet)
			{
				sum += i;
			}
			if (count == 0)
				this.averageCurrent = (double)this.defaultNum;
			else
				this.averageCurrent = sum / count;
			if (this.averageNum == null)
				this.averageInstance = 0D;
			else
				this.averageInstance = this.averageNum - this.averageCurrent;

			if (this.averageNum != null)
			{
				for (Double i : this.numSet)
				{
					delta_sum += (i - this.averageCurrent) * (i - this.averageCurrent);
				}
				if (count == 0)
					this.deltaCurrent = 1.0;
				else
					this.deltaCurrent = delta_sum / count;
				if (this.deltaCurrent == 0)
					this.deltaCurrent = (double)this.deltaNum;
				this.deltaInstance = Math.sqrt(this.deltaNum / this.deltaCurrent);
			}
		}
		
		public Double getResult(Integer originalNum)
		{
			return getResult((double)originalNum);
		}
		
		public Double getResult(Double originalNum)
		{
			if (originalNum == null)
				originalNum = (double)this.defaultNum;
			Double result = originalNum;
			if (this.averageNum != null)
			{
				result += this.averageInstance;
				if (this.deltaNum != null)
				{
					result = (result - this.averageNum) * this.deltaInstance + this.averageNum;
				}
			}
			return validateNum(result);
		}
		
		public Double getDefaultResult()
		{
			return validateNum(this.defaultNum + this.averageInstance);
		}
		
		public Double validateNum(Integer num)
		{
			if (num > this.maxNum)
				return (double)this.maxNum;
			else if (num < this.minNum)
				return (double)this.minNum;
			else
				return (double)num;
		}
		
		public Double validateNum(Double num)
		{
			if (num > this.maxNum)
				return (double)this.maxNum;
			else if (num < this.minNum)
				return (double)this.minNum;
			else
				return num;
		}
		
		public Double getAverageCurrent()
		{
			if (this.averageCurrent == null)
				calculateAverage();
			return this.averageCurrent;
		}
	}
	
	
	
	@Transactional
	public ErrorType calculateAll()
	{
		ErrorType errorCode;
		List<CollectionItem> collectionItems = this.collectionItemDao.queryItemAll();
		for (CollectionItem item : collectionItems)
		{
			if ((errorCode = calculateCollectionItemByItemId(item.getId())) != ErrorType.NO_ERROR)
			{
				return errorCode;
			}
		}
		List<PublicHearingsItem> publicHearingsItems = this.publicHearingsItemDao.queryItemAll();
		for (PublicHearingsItem item : publicHearingsItems)
		{
			if ((errorCode = calculatePublicHearingsItemByItemId(item.getId())) != ErrorType.NO_ERROR)
			{
				return errorCode;
			}
		}
		if ((errorCode = calculateTotalScore()) != ErrorType.NO_ERROR)
			return errorCode;
		else 
			return ErrorType.NO_ERROR;
	}
	
	@Transactional
	public ErrorType calculateCollectionItemByItemId(Long itemId)
	{
		CollectionItem item = this.collectionItemDao.queryItemWithCheckOperatorsById(itemId);
		int maxScore = item.getMaxScore(), minScore = item.getMinScore(), defaultScore = item.getDefaultScore();
		Integer averageScore = item.getAverageScore(), deltaScore = item.getDeltaScore();
		NumList numList;
		
		List<SysUserOut> users = this.sysUserService.queryAllUserOut();
		List<CollectionItemScore> itemScores = new ArrayList<CollectionItemScore>();
		
		if (item.getIfCheck())
		{
			HashMap<Long, NumList> hashMap = new HashMap<Long, NumList>();
			for (SysUserOut user : users)
			{
				NumList tempList = new NumList(defaultScore, maxScore, minScore, averageScore, deltaScore);
				hashMap.put(user.getId(), tempList);
			}
			
			for (SysUserOut user : item.getCheckOperator())
			{
				List<CollectionItemInfoCheck> oneInfoChecks = this.collectionItemInfoCheckDao.queryByInfoProviderAndItemId(user.getId(), itemId);
				numList = new NumList(defaultScore, maxScore, minScore, averageScore, deltaScore);
				for (CollectionItemInfoCheck info : oneInfoChecks)
				{
					if (info.getValue() != null)
						numList.addNum(info.getValue());
					else
						numList.addDefaultNum();
				}
				numList.calculate();
				for (CollectionItemInfoCheck info : oneInfoChecks)
				{
					Long targetId = info.getInfoTarget().getId();
					NumList tempList = hashMap.get(targetId);
					if (tempList == null)
						continue;
					if (info.getValue() != null)
					{
						tempList.addNum(numList.getResult(info.getValue()));
					}
					else
					{
						tempList.addNum(numList.getDefaultResult());
					}
				}
			}
			
			numList = new NumList(defaultScore, maxScore, minScore, averageScore, deltaScore);
			double tempI;
			for (SysUserOut user : users)
			{
				NumList tempList = (NumList)hashMap.get(user.getId());
				CollectionItemScore itemScore = this.collectionItemScoreDao.queryByInfoTargetAndItemId(user.getId(), itemId);
				if (itemScore == null)
					itemScore = new CollectionItemScore();
				tempI = tempList.getAverageCurrent();
				itemScore.setCollectionItem(item);
				itemScore.setInfoTarget(user);
				itemScore.setValue(tempI);
				itemScores.add(itemScore);
				numList.addNum(tempI);
			}
		}
		else
		{
			numList = new NumList(defaultScore, maxScore, minScore, averageScore, deltaScore);
			double tempI;
			for (SysUserOut user : users)
			{
				CollectionItemInfo tempInfo = this.collectionItemInfoDao.queryByInfoProviderAndItemId(user.getId(), itemId);
				if (tempInfo == null || tempInfo.getValue() == null)
					tempI = defaultScore;
				else
					tempI = tempInfo.getValue();
				CollectionItemScore itemScore = this.collectionItemScoreDao.queryByInfoTargetAndItemId(user.getId(), itemId);
				if (itemScore == null)
					itemScore = new CollectionItemScore();
				itemScore.setCollectionItem(item);
				itemScore.setInfoTarget(user);
				itemScore.setValue(tempI);
				itemScores.add(itemScore);
				numList.addNum(tempI);
			}
		}
		numList.calculate();
		double min = Double.MAX_VALUE;
		long range = 0;
		long rangeS = 0;
		
		Collections.sort(itemScores, new ScoreComparator<CollectionItemScore>());
		
		for (CollectionItemScore itemScore : itemScores)
		{
			double scoreT = itemScore.getValue();
			if (scoreT == min)
				rangeS++;
			else
			{
				min = scoreT;
				rangeS++;
				range = rangeS;
			}
			this.collectionItemScoreService.setInfo(itemScore.getInfoTarget(), itemId, numList.getResult(scoreT), range);
		}
		return ErrorType.NO_ERROR;
	}
	
	@Transactional
	public ErrorType calculatePublicHearingsItemByItemId(Long itemId)
	{
		PublicHearingsItem item = this.publicHearingsItemDao.queryItemById(itemId);
		if ((int)item.getType() == 0)
		{
			int maxScore = item.getMaxScore(), minScore = item.getMinScore(), defaultScore = item.getDefaultScore();
			Integer averageScore = item.getAverageScore(), deltaScore = item.getDeltaScore();
			NumList numList;
		
			List<SysUserOut> users = this.sysUserService.queryAllUserOut();
			
			HashMap<Long, NumList> hashMap = new HashMap<Long, NumList>();
			for (SysUserOut user : users)
			{
				NumList tempList = new NumList(defaultScore, maxScore, minScore, averageScore, deltaScore);
				hashMap.put(user.getId(), tempList);
			}
			for (SysUserOut user : users)
			{
				List<PublicHearingsItemInfo> oneInfos = this.publicHearingsItemInfoDao.queryByInfoProviderAndItemId(user.getId(), itemId);
				numList = new NumList(defaultScore, maxScore, minScore, averageScore, deltaScore);
				for (PublicHearingsItemInfo info : oneInfos)
				{
					if (info.getValue() != null)
						numList.addNum(info.getValue());
					else
						numList.addDefaultNum();
				}
				numList.calculate();
				for (PublicHearingsItemInfo info : oneInfos)
				{
					Long targetId = info.getInfoTarget().getId();
					NumList tempList = hashMap.get(targetId);
					if (info.getValue() != null)
					{
						tempList.addNum(numList.getResult(info.getValue()));
					}
					else
					{
						tempList.addNum(numList.getDefaultResult());
					}
				}
			}
			
			List<PublicHearingsItemScore> itemScores = new ArrayList<PublicHearingsItemScore>();
			numList = new NumList(defaultScore, maxScore, minScore, averageScore, deltaScore);
			double tempI;
			for (SysUserOut user : users)
			{
				NumList tempList = (NumList)hashMap.get(user.getId());
				PublicHearingsItemScore itemScore = this.publicHearingsItemScoreDao.queryByInfoTargetAndItemId(user.getId(), itemId);
				if (itemScore == null)
					itemScore = new PublicHearingsItemScore();
				tempI = tempList.getAverageCurrent();
				itemScore.setPublicHearingsItem(item);
				itemScore.setInfoTarget(user);
				itemScore.setValue(tempI);
				itemScores.add(itemScore);
				numList.addNum(tempI);
			}
			numList.calculate();
			Collections.sort(itemScores, new ScoreComparator<PublicHearingsItemScore>());
			double min = Double.MAX_VALUE;
			long range = 0;
			long rangeS = 0;
			for (PublicHearingsItemScore itemScore : itemScores)
			{
				double scoreT = itemScore.getValue();
				if (scoreT == min)
					rangeS++;
				else
				{
					min = scoreT;
					rangeS++;
					range = rangeS;
				}
				this.publicHearingsItemScoreService.setInfo(itemScore.getInfoTarget(), itemId, numList.getResult(scoreT), range);
			}
		}
		return ErrorType.NO_ERROR;
	}
	
	@Transactional
	public ErrorType calculateTotalScore()
	{
		List<SysUserOut> sysUserOut = this.sysUserService.queryAllUserOut();
		List<TotalScore> totalScores = new ArrayList<TotalScore>();
		double totalScore = 0;
		for (SysUserOut user : sysUserOut)
		{
			totalScore = 0;
			List<PublicHearingsItemScore> publicHearingsItemScores = this.publicHearingsItemScoreDao.queryByInfoTarget(user.getId());
			List<CollectionItemScore> collectionItemScores = this.collectionItemScoreDao.queryByInfoTarget(user.getId());
			for (PublicHearingsItemScore score : publicHearingsItemScores)
				totalScore += score.getValue();
			for (CollectionItemScore score : collectionItemScores)
				totalScore += score.getValue();
			TotalScore tempScore = new TotalScore();
			tempScore.setInfoTarget(user);
			tempScore.setValue(totalScore);
			totalScores.add(tempScore);
		}
		Collections.sort(totalScores, new ScoreComparator<TotalScore>());
		double min = Double.MAX_VALUE;
		long range = 0;
		long rangeS = 0;
		for (TotalScore score : totalScores)
		{
			double scoreT = score.getValue();
			if (scoreT == min)
				rangeS++;
			else
			{
				min = scoreT;
				rangeS++;
				range = rangeS;
			}
			this.totalScoreService.setInfo(score.getInfoTarget(), scoreT, range);
		}
		return ErrorType.NO_ERROR;
	}
	
	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setCollectionItemInfoDao(
			CollectionItemInfoDao collectionItemInfoDao) {
		this.collectionItemInfoDao = collectionItemInfoDao;
	}

	public void setCollectionItemInfoCheckDao(
			CollectionItemInfoCheckDao collectionItemInfoCheckDao) {
		this.collectionItemInfoCheckDao = collectionItemInfoCheckDao;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setCollectionItemScoreDao(
			CollectionItemScoreDao collectionItemScoreDao) {
		this.collectionItemScoreDao = collectionItemScoreDao;
	}
	
	public void setPublicHearingsItemDao(
			PublicHearingsItemDao publicHearingsItemDao) {
		this.publicHearingsItemDao = publicHearingsItemDao;
	}

	public void setPublicHearingsItemInfoDao(
			PublicHearingsItemInfoDao publicHearingsItemInfoDao) {
		this.publicHearingsItemInfoDao = publicHearingsItemInfoDao;
	}

	public void setPublicHearingsItemScoreDao(
			PublicHearingsItemScoreDao publicHearingsItemScoreDao) {
		this.publicHearingsItemScoreDao = publicHearingsItemScoreDao;
	}

	public void setTotalScoreService(TotalScoreService totalScoreService) {
		this.totalScoreService = totalScoreService;
	}

	public void setCollectionItemScoreService(
			CollectionItemScoreService collectionItemScoreService) {
		this.collectionItemScoreService = collectionItemScoreService;
	}

	public void setPublicHearingsItemScoreService(
			PublicHearingsItemScoreService publicHearingsItemScoreService) {
		this.publicHearingsItemScoreService = publicHearingsItemScoreService;
	}
}
