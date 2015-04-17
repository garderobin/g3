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
@Table(name="PUB_HEARINGS_ITEM_SCORE")
public class PublicHearingsItemScore implements ScoreInterface
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@ForeignKey(name = "PUB_HEARINGS_SCORE_INFO_TARGET")
	private SysUserOut infoTarget;

	@ManyToOne
	@ForeignKey(name = "PUB_HEARINGS_SCORE_ITEM")
	private PublicHearingsItem publicHearingsItem;
	
	@Column(nullable=false)
	private Double value;
	
	@Column(nullable=false)
	private Long rank;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
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

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}
	
}
