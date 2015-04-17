package com.score.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SYS_USER")
public class SysUserOut
{	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private Boolean ifTest;
	
	@Column(nullable=false)
	private String gitDirectory;
	
	@ManyToMany(mappedBy="checkOperator", fetch=FetchType.LAZY)
	private List<CollectionItem> checkItems;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoProvider", fetch=FetchType.LAZY)
	private List<CollectionItemInfo> collectionItemInfo;
	
	//the CollectionItemInfoCheckProvider
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoProvider", fetch=FetchType.LAZY)
	private List<CollectionItemInfoCheck> collectionItemInfoCheckProvider;

	//the CollectionItemInfoCheckTarget
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoTarget", fetch=FetchType.LAZY)
	private List<CollectionItemInfoCheck> collectionItemInfoCheckTarget;
	
	//the CollectionItemScoreTarget
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoTarget", fetch=FetchType.LAZY)
	private List<CollectionItemScore> collectionItemScoreTarget;
	
	//the PublicItemInfoProvider
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoProvider", fetch=FetchType.LAZY)
	private List<PublicHearingsItemInfo> publicHearingsItemInfoProvider;
	
	//the PublicItemInfoTarget
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoTarget", fetch=FetchType.LAZY)
	private List<PublicHearingsItemInfo> publicHearingsItemInfoTarget;
	
	//the PublicItemInfoTestProvider
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoProvider", fetch=FetchType.LAZY)
	private List<PublicHearingsItemInfoText> publicHearingsItemInfoTextProvider;
	
	//the PublicItemInfoTextTarget
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoTarget", fetch=FetchType.LAZY)
	private List<PublicHearingsItemInfoText> publicHearingsItemInfoTextTarget;
	
	//the PublicHearingsItemScoreTarget
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoTarget", fetch=FetchType.LAZY)
	private List<PublicHearingsItemScore> publicHearingsItemScoreTarget;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="infoTarget", fetch=FetchType.LAZY)
	private List<TotalScore> totalScoreTarget;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CollectionItem> getCheckItems() {
		return checkItems;
	}

	public Boolean getIfTest() {
		return ifTest;
	}

	public void setIfTest(Boolean ifTest) {
		this.ifTest = ifTest;
	}

	public String getGitDirectory() {
		return gitDirectory;
	}

	public void setGitDirectory(String gitDirectory) {
		this.gitDirectory = gitDirectory;
	}

	public void setCheckItems(List<CollectionItem> checkItems) {
		this.checkItems = checkItems;
	}

	public List<CollectionItemInfo> getCollectionItemInfo() {
		return collectionItemInfo;
	}

	public void setCollectionItemInfo(List<CollectionItemInfo> collectionItemInfo) {
		this.collectionItemInfo = collectionItemInfo;
	}

	public List<CollectionItemInfoCheck> getCollectionItemInfoCheckProvider() {
		return collectionItemInfoCheckProvider;
	}

	public void setCollectionItemInfoCheckProvider(
			List<CollectionItemInfoCheck> collectionItemInfoCheckProvider) {
		this.collectionItemInfoCheckProvider = collectionItemInfoCheckProvider;
	}

	public List<CollectionItemInfoCheck> getCollectionItemInfoCheckTarget() {
		return collectionItemInfoCheckTarget;
	}

	public void setCollectionItemInfoCheckTarget(
			List<CollectionItemInfoCheck> collectionItemInfoCheckTarget) {
		this.collectionItemInfoCheckTarget = collectionItemInfoCheckTarget;
	}

	public List<PublicHearingsItemInfo> getPublicHearingsItemInfoProvider() {
		return publicHearingsItemInfoProvider;
	}

	public void setPublicHearingsItemInfoProvider(
			List<PublicHearingsItemInfo> publicHearingsItemInfoProvider) {
		this.publicHearingsItemInfoProvider = publicHearingsItemInfoProvider;
	}

	public List<PublicHearingsItemInfo> getPublicHearingsItemInfoTarget() {
		return publicHearingsItemInfoTarget;
	}

	public void setPublicHearingsItemInfoTarget(
			List<PublicHearingsItemInfo> publicHearingsItemInfoTarget) {
		this.publicHearingsItemInfoTarget = publicHearingsItemInfoTarget;
	}

	public List<PublicHearingsItemInfoText> getPublicHearingsItemInfoTextProvider() {
		return publicHearingsItemInfoTextProvider;
	}

	public void setPublicHearingsItemInfoTextProvider(
			List<PublicHearingsItemInfoText> publicHearingsItemInfoTextProvider) {
		this.publicHearingsItemInfoTextProvider = publicHearingsItemInfoTextProvider;
	}

	public List<PublicHearingsItemInfoText> getPublicHearingsItemInfoTextTarget() {
		return publicHearingsItemInfoTextTarget;
	}

	public void setPublicHearingsItemInfoTextTarget(
			List<PublicHearingsItemInfoText> publicHearingsItemInfoTextTarget) {
		this.publicHearingsItemInfoTextTarget = publicHearingsItemInfoTextTarget;
	}

	public List<CollectionItemScore> getCollectionItemScoreTarget() {
		return collectionItemScoreTarget;
	}

	public void setCollectionItemScoreTarget(
			List<CollectionItemScore> collectionItemScoreTarget) {
		this.collectionItemScoreTarget = collectionItemScoreTarget;
	}

	public List<PublicHearingsItemScore> getPublicHearingsItemScoreTarget() {
		return publicHearingsItemScoreTarget;
	}

	public void setPublicHearingsItemScoreTarget(
			List<PublicHearingsItemScore> publicHearingsItemScoreTarget) {
		this.publicHearingsItemScoreTarget = publicHearingsItemScoreTarget;
	}

	public List<TotalScore> getTotalScoreTarget() {
		return totalScoreTarget;
	}

	public void setTotalScoreTarget(List<TotalScore> totalScoreTarget) {
		this.totalScoreTarget = totalScoreTarget;
	}
}
