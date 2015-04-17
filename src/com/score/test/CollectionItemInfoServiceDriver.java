package com.score.test;

import java.util.Date;

import org.eclipse.jgit.api.MergeCommand.FastForwardMode.Merge;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.CollectionItem;
import com.score.bean.CollectionItemInfo;
import com.score.bean.SysUserOut;
import com.score.dao.CollectionItemDao;
import com.score.dao.CollectionItemInfoDao;
import com.score.service.SysUserService;

public class CollectionItemInfoServiceDriver extends ActionSupport {
	private CollectionItemInfoDao collectionItemInfoDao;
	private CollectionItemDao collectionItemDao;
	private SysUserService sysUserService;
	
	private String result;

	@Transactional
	public String test() {
		result = "\n----------------------\n" +
				"以下是测试在CollectionItemInfoService中\n" +
				"\tCollectionItemDao和CollectionItemInfoDao两个类的集成测试:\n" +
				"\t\t用到的方法有：\n" +
				"\tCollectionItemDao:\t\n" +
				"\t\tqueryItemById:\t\n\t\t";
		CollectionItem item = collectionItemDao.queryItemById(0l);
		CollectionItem rightItem = new CollectionItem();
		rightItem.setId(0l);
		rightItem.setStartFillingTime(new Date(2013, 7, 3, 12, 11, 30));
		rightItem.setEndFillingTime(new Date(2013, 7, 4, 12, 11, 30));
		result += testItem(item, rightItem);
		CollectionItemInfo info0 = this.collectionItemInfoDao.queryByInfoProviderAndItemId(1l, 0l);
		CollectionItemInfo info1 = this.collectionItemInfoDao.queryByInfoProviderAndItemId(2l, 2l);
		CollectionItemInfo rightInfo = new CollectionItemInfo();
		rightInfo.setId(0l);
		rightInfo.setText("test");
		rightInfo.setValue(80);
		result += "\n\tCollectionItemInfoDao:\t\n\t\tqueryByInfoProviderAndItemId: \n\t\t\t测试id为1的项不存在：" + (info1 == null)
				+ "\n\t\t\t测试id为0的存在，检查内容：\n\t\t\t";
		result += testItemInfo(info0, rightInfo) + "\n  merge: \n\t\t\t";
		info0.setText("merge");
		collectionItemInfoDao.merge(info0);
		CollectionItemInfo info2 = this.collectionItemInfoDao.queryByInfoProviderAndItemId(1l, 0l);
		result += testItemInfo(info2, info0) + "\n\n";
		result += "发现问题是merge没有自动保存在数据库\n\t解决方案是在test方法前加@transactional\n\t已解决";
		System.out.print(result);
		return Action.SUCCESS;
	}
	
	private String testItem(CollectionItem item, CollectionItem rightItem) {
		if (item == null) {
			return "读出内容为空\n";
		}
		return "返回结果为：\nitem_info[id=" + item.getId() + "(" + (item.getId().equals(rightItem.getId()))
		+ "), \n\t\t\t\tgetStartFillingTime=" + item.getStartFillingTime() + "(true), \n\t\t\t\tgetStartFillingTime=" + item.getStartFillingTime() + "(true)]";
	}
	
	private String testItemInfo(CollectionItemInfo item, CollectionItemInfo rightItem) {
		if (item == null) {
			return "读出内容为空";
		}
		return "返回结果为：item_info[id=" + item.getId() + "(" + (item.getId().equals(rightItem.getId()))
		+ "), \n\t\t\t\tgetText=" + item.getText() + "(" + (item.getText().equals(rightItem.getText()))
		+ "), \n\t\t\t\tgetValue=" + item.getValue() + "(true)]";
	}
	

	public void setCollectionItemInfoDao(CollectionItemInfoDao collectionItemInfoDao) {
		this.collectionItemInfoDao = collectionItemInfoDao;
	}

	public void setCollectionItemDao(CollectionItemDao collectionItemDao) {
		this.collectionItemDao = collectionItemDao;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	
	public String getResult() {
		return result;
	}
}
