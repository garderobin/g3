package com.score.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="THOUGHT")
public class Thought 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@ForeignKey(name = "THOUGHT_COLLECTION_ITEM")
	private CollectionItem collectionItem;

	@ManyToOne
	@ForeignKey(name = "THOUGHT_INFO_PROVIDER")
	private SysUserOut infoProvider;
	
	private Integer score;
	
	private Boolean publish;

	@Column(nullable=false)
	private String message;
	
	private String gitIdentifier;
	
	@ManyToOne
	@ForeignKey(name = "THOUGHT_THOUGHT")
	private Thought fatherThought;
	
	@OneToMany (cascade=CascadeType.ALL, mappedBy="fatherThought", fetch=FetchType.EAGER)
	private List<Thought> comments;

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

	public SysUserOut getInfoProvider() {
		return infoProvider;
	}

	public void setInfoProvider(SysUserOut infoProvider) {
		this.infoProvider = infoProvider;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Boolean getPublish() {
		return publish;
	}

	public void setPublish(Boolean publish) {
		this.publish = publish;
	}
	
	public String getGitIdentifier() {
		return gitIdentifier;
	}

	public void setGitIdentifier(String gitIdentifier) {
		this.gitIdentifier = gitIdentifier;
	}

	public Thought getFatherThought() {
		return fatherThought;
	}

	public void setFatherThought(Thought fatherThought) {
		this.fatherThought = fatherThought;
	}

	public List<Thought> getComments() {
		return comments;
	}

	public void setComments(List<Thought> comments) {
		this.comments = comments;
	}
	
}
