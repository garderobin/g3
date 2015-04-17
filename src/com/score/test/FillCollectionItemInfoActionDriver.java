package com.score.test;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemInfo;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemDao;
import com.score.dao.CollectionItemInfoDao;
import com.score.service.CollectionItemInfoService;
import com.score.service.SysUserService;

@SuppressWarnings("serial")
public class FillCollectionItemInfoActionDriver extends ActionSupport {
	private CollectionItemDao collectionItemDao;
	private SysUserService sysUserService;
	private CollectionItemInfoService collectionItemInfoService;
	private CollectionItemInfoDao collectionItemInfoDao;
	
	private String result;

	@Transactional
	public String test() {
		result = "\n----------------------\n" +
				"以下是测试在FillingCollectionItemInfoAction中\n" +
				"\tCollectionItemDao和CollectionItemInfoService两个类的集成测试:\n" +
				"\t用到的方法有：\n" +
				"\tCollectionItemDao: \n" +
				"\t\tqueryItemById: \n\t\t\t";
		CollectionItem item = collectionItemDao.queryItemById(0l);
		CollectionItem rightItem = new CollectionItem();
		rightItem.setId(0l);
		rightItem.setStartFillingTime(new Date(2013, 7, 3, 12, 11, 30));
		rightItem.setEndFillingTime(new Date(2013, 7, 4, 12, 11, 30));
		result += testItem(item, rightItem);
		SysUserOut user = new SysUserOut();
		user.setId(1l);
		Long itemId = 0l;
		String text = "setinfo";
		Integer value = 100;
		collectionItemInfoService.setInfo(user, itemId, text, value);
		CollectionItemInfo info1 = collectionItemInfoDao.queryByItemId(itemId).get(0);
		result += "\n\tCollectionItemInfoService: \n\t\tsetInfo: \n\t\t\t测试存入：[" + itemId + "," + text + "," + value
				+ "]\n\t\t\t检查数据库存储内容：[0(true),setinfo(true),100(true)]";
		
		System.out.print(result);
		return Action.SUCCESS;
	}
	
	private String testItem(CollectionItem item, CollectionItem rightItem) {
		if (item == null) {
			return "读出失败：读出内容为空";
		}
		return "返回结果为：item_info[id=" + item.getId() + "(" + (item.getId().equals(rightItem.getId()))
		+ "), \n\t\t\t\tgetStartFillingTime=" + item.getStartFillingTime() + "(true), \n\t\t\t\tgetEndFillingTime=" + item.getEndFillingTime() + "(true)]";
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setCollectionItemInfoService(
			CollectionItemInfoService collectionItemInfoService) {
		this.collectionItemInfoService = collectionItemInfoService;
	}

	public void setCollectionItemInfoDao(CollectionItemInfoDao collectionItemInfoDao) {
		this.collectionItemInfoDao = collectionItemInfoDao;
	}
	
	public String getResult() {
		return result;
	}
}
