package com.score.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@Entity
@Table(name="PUB_HEARINGS_ITEM")
public class PublicHearingsItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String itemId;

	@Column(nullable=false)
	private String name;
	
	//type = 0 normal public hearings
	@Column(nullable=false)
	private Short type = 0;
	
	private Integer minScore = 0, maxScore = 0, defaultScore = 0, averageScore = 0, deltaScore = 0;
	
	private String contents;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="publicHearingsItem", fetch=FetchType.LAZY)
	private List<PublicHearingsItemInfo> publicHearingsItemInfo;
	
	private Date startCheckingTime, endCheckingTime;
	
	private Boolean isPublished;
	
	//This method is only used when system is TA systems
	public Boolean ifCheckable()
	{
		Date d = new Date();
		if ((startCheckingTime == null || d.after(startCheckingTime)) && (endCheckingTime == null || d.before(endCheckingTime)))
			return true;
		else
			return false;
	}

	//This method is only used when system is TA systems
	public Boolean ifPublishable()
	{
		return isPublished;
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

	public List<PublicHearingsItemInfo> getPublicHearingsItemInfo() {
		return publicHearingsItemInfo;
	}

	public void setPublicHearingsItemInfo(
			List<PublicHearingsItemInfo> publicHearingsItemInfo) {
		this.publicHearingsItemInfo = publicHearingsItemInfo;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getStartCheckingTime() {
		return startCheckingTime;
	}

	public void setStartCheckingTime(Date startCheckingTime) {
		this.startCheckingTime = startCheckingTime;
	}

	public Date getEndCheckingTime() {
		return endCheckingTime;
	}

	public void setEndCheckingTime(Date endCheckingTime) {
		this.endCheckingTime = endCheckingTime;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

}
