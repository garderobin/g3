package com.score.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@Entity
@Table(name="COLLECTION_ITEM")
public class CollectionItem 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String itemId ;

	@Column(nullable=false)
	private String name;
	
	private String contents;
	
	private String checkRequirement;
	
	private String codeDirectory;
	
	@Column(nullable=false)
	private Short type = 0;
	
	@Column(nullable=false)
	private Integer minScore = 0, maxScore = 0, defaultScore = 0;
	
	private Integer averageScore = 0, deltaScore = 0;
	
	@Column(nullable=false)
	private Boolean ifUserSubmitText = false, ifUserSubmitPic = false, ifUserSubmitCode = false, ifCollection = false, ifCheck = false;

	@Column(nullable=false)
	private Integer publicityType = 0;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="collectionItem", fetch=FetchType.LAZY)
	private List<CollectionItemInfo> collectedItemInfo;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="collectionItem", fetch=FetchType.LAZY)
	private List<CollectionItemInfoCheck> collectedItemInfoCheck;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<SysUserOut> checkOperator;
	
	private Date startFillingTime, endFillingTime;
	
	//This method is only used when system is TA systems
	public Boolean ifFillable()
	{
		Date d = new Date();
		if (startFillingTime != null && endFillingTime != null && d.after(startFillingTime) && d.before(endFillingTime))
			return true;
		else
			return false;
	}
	
	public boolean checkCheckOperator(SysUserOut sysUser)
	{
		CollectionItem item = this;
		List<SysUserOut> checkOperators;
		if (item == null || (checkOperators = item.getCheckOperator()) == null || sysUser == null)
			return false;
		boolean t = false;
		for (SysUserOut user : checkOperators)
		{
			if (user.getId().equals(sysUser.getId()))
			{
				t = true;
				break;
			}
		}
		return t;
	}

	//This method is only used when system is TA systems
	public Boolean ifCheckable()
	{
		return true;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeDirectory() {
		return codeDirectory;
	}

	public void setCodeDirectory(String codeDirectory) {
		this.codeDirectory = codeDirectory;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCheckRequirement() {
		return checkRequirement;
	}

	public void setCheckRequirement(String checkRequirement) {
		this.checkRequirement = checkRequirement;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getMinScore() {
		return minScore;
	}

	public void setMinScore(Integer minScore) {
		this.minScore = minScore;
	}

	public Integer getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	public Integer getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(Integer defaultScore) {
		this.defaultScore = defaultScore;
	}

	public Integer getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(Integer averageScore) {
		this.averageScore = averageScore;
	}

	public Integer getDeltaScore() {
		return deltaScore;
	}

	public void setDeltaScore(Integer deltaScore) {
		this.deltaScore = deltaScore;
	}

	public Boolean getIfUserSubmitText() {
		return ifUserSubmitText;
	}

	public void setIfUserSubmitText(Boolean ifUserSubmitText) {
		this.ifUserSubmitText = ifUserSubmitText;
	}

	public Boolean getIfUserSubmitPic() {
		return ifUserSubmitPic;
	}

	public void setIfUserSubmitPic(Boolean ifUserSubmitPic) {
		this.ifUserSubmitPic = ifUserSubmitPic;
	}

	public Boolean getIfUserSubmitCode() {
		return ifUserSubmitCode;
	}

	public void setIfUserSubmitCode(Boolean ifUserSubmitCode) {
		this.ifUserSubmitCode = ifUserSubmitCode;
	}

	public Boolean getIfCollection() {
		return ifCollection;
	}

	public void setIfCollection(Boolean ifCollection) {
		this.ifCollection = ifCollection;
	}

	public Boolean getIfCheck() {
		return ifCheck;
	}

	public void setIfCheck(Boolean ifCheck) {
		this.ifCheck = ifCheck;
	}

	public List<SysUserOut> getCheckOperator() {
		return checkOperator;
	}

	public void setCheckOperator(List<SysUserOut> checkOperator) {
		this.checkOperator = checkOperator;
	}

	public Integer getPublicityType() {
		return publicityType;
	}

	public void setPublicityType(Integer publicityType) {
		this.publicityType = publicityType;
	}

	public List<CollectionItemInfo> getCollectedItemInfo() {
		return collectedItemInfo;
	}

	public void setCollectedItemInfo(List<CollectionItemInfo> collectedItemInfo) {
		this.collectedItemInfo = collectedItemInfo;
	}

	public List<CollectionItemInfoCheck> getCollectedItemInfoCheck() {
		return collectedItemInfoCheck;
	}

	public void setCollectedItemInfoCheck(
			List<CollectionItemInfoCheck> collectedItemInfoCheck) {
		this.collectedItemInfoCheck = collectedItemInfoCheck;
	}

	public Date getStartFillingTime() {
		return startFillingTime;
	}

	public void setStartFillingTime(Date startFillingTime) {
		this.startFillingTime = startFillingTime;
	}

	public Date getEndFillingTime() {
		return endFillingTime;
	}

	public void setEndFillingTime(Date endFillingTime) {
		this.endFillingTime = endFillingTime;
	}

}
