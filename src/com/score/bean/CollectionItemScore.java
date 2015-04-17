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
@Table(name="COLLECTION_ITEM_SCORE")
public class CollectionItemScore implements ScoreInterface
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@ForeignKey(name = "ITEM_SCORE_INFO_TARGET")
	private SysUserOut infoTarget;

	@ManyToOne
	@ForeignKey(name = "ITEM_SCORE_ITEM")
	private CollectionItem collectionItem;
	
	@Column(nullable=false)
	private Double value;
	
	@Column(nullable=false)
	private Long rank;
	
//	@Override
//	public int compareTo(CollectionItemScore o) {
//		return -this.value.compareTo(o.value);
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CollectionItem getCollectionItem() {
		return collectionItem;
	}

	public void setCollectionItem(CollectionItem collectionItem) {
		this.collectionItem = collectionItem;
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

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}
	
}
