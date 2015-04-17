package com.score.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="PUB_HEARINGS_ITEM_INFO")
public class PublicHearingsItemInfo 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@ForeignKey(name = "PUB_HEARINGS_INFO_PROVIDER")
	private SysUserOut infoProvider;
	
	@ManyToOne
	@ForeignKey(name = "PUB_HEARINGS_INFO_TARGET")
	private SysUserOut infoTarget;

	@ManyToOne
	@ForeignKey(name = "PUB_HEARINGS_ITEM")
	private PublicHearingsItem publicHearingsItem;
	
	@Column(nullable=false)
	private Integer value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public SysUserOut getInfoProvider() {
		return infoProvider;
	}

	public void setInfoProvider(SysUserOut infoProvider) {
		this.infoProvider = infoProvider;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public SysUserOut getInfoTarget() {
		return infoTarget;
	}

	public void setInfoTarget(SysUserOut infoTarget) {
		this.infoTarget = infoTarget;
	}

	public PublicHearingsItem getPublicHearingsItem() {
		return publicHearingsItem;
	}

	public void setPublicHearingsItem(PublicHearingsItem publicHearingsItem) {
		this.publicHearingsItem = publicHearingsItem;
	}
	
}
