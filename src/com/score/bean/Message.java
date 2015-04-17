package com.score.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="MESSAGE")
public class Message 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@ForeignKey(name = "MESSAGE_INFO_TARGET")
	private SysUserOut infoTarget;

	@ManyToOne
	@ForeignKey(name = "MESSAGE_INFO_PROVIDER")
	private SysUserOut infoProvider;

	@Column(nullable=false)
	private String message;
	
	@Column
	private String title;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SysUserOut getInfoTarget() {
		return infoTarget;
	}

	public void setInfoTarget(SysUserOut infoTarget) {
		this.infoTarget = infoTarget;
	}

	public SysUserOut getInfoProvider() {
		return infoProvider;
	}

	public void setInfoProvider(SysUserOut infoProvider) {
		this.infoProvider = infoProvider;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
