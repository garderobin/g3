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
@Table(name="TOTAL_SCORE")
public class TotalScore implements ScoreInterface
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@ForeignKey(name = "TOTAL_SCORE_INFO_TARGET")
	private SysUserOut infoTarget;

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

	public SysUserOut getInfoTarget() {
		return infoTarget;
	}

	public void setInfoTarget(SysUserOut infoTarget) {
		this.infoTarget = infoTarget;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

}
