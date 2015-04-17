package com.score.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="COLLECTION_ITEM_INFO")
public class CollectionItemInfo 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@ForeignKey(name = "ITEM_INFO_INFO_PROVIDER")
	private SysUserOut infoProvider;

	@ManyToOne
	@ForeignKey(name = "ITEM_INFO_ITEM")
	private CollectionItem collectionItem;
	
	private Integer value;
	
	private String text;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="collectionItemInfo", fetch=FetchType.LAZY)
	private List<CollectionItemInfoImage> images;

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

	public CollectionItem getCollectionItem() {
		return collectionItem;
	}

	public void setCollectionItem(CollectionItem collectionItem) {
		this.collectionItem = collectionItem;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<CollectionItemInfoImage> getImages() {
		return images;
	}

	public void setImages(List<CollectionItemInfoImage> images) {
		this.images = images;
	}
	
}
