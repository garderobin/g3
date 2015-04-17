package com.score.bean;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="COLLECTION_ITEM_INFO_IMAGE")
public class CollectionItemInfoImage 
{
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@ForeignKey(name="ITEM_INFO_IMAGE_ITEM")
	private CollectionItemInfo collectionItemInfo;
	
	@Lob
	private Blob image;
	
	@Lob
	private Blob smallImage;
	
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CollectionItemInfo getCollectionItemInfo() {
		return collectionItemInfo;
	}

	public void setCollectionItemInfo(CollectionItemInfo collectionItemInfo) {
		this.collectionItemInfo = collectionItemInfo;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image)
	{
		this.image = image;
	}

	public Blob getSmallImage() {
		return this.smallImage;
	}
	
	public void setSmallImage(Blob smallImage)
	{
		this.smallImage = smallImage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
