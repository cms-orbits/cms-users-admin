package com.joelgtsantos.cmsusers.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
* The persistent class for the PARTICIPATIONS database table.
* 
*/
@Entity
@Table(name="PARTICIPATIONS")
@NamedQuery(name="Participation.findAll", query="SELECT t FROM Participation t")
@TypeDef(name="interval", typeClass = Interval.class)
public class Participation {
	private static final long serialVersionUID = 1L;

	public Participation() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(sequenceName = "participations_id_seq", allocationSize = 1, name = "USER_SEQ")
	private long id;
	
	@Column(name="delay_time")
	@Type(type = "interval")    
	private Integer delayTime = 0;
	
	@Column(name="extra_time")
	@Type(type = "interval")    
	private Integer extraTime = 0;
	
	private String password = "";
	
	private boolean hidden = false;
	
	private boolean unrestricted = false;
	
	@Column(name="contest_id")
	private Long contestId;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="team_id")
	private Long teamId;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/*public PGcidr getIp() {
		return ip;
	}

	public void setIp(PGcidr ip) {
		this.ip = ip;
	}*/

	/*public Instant getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(Instant startingTime) {
		this.startingTime = startingTime;
	}*/

	public Integer getDelayTime() {
		return this.delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public Integer getExtraTime() {
		return this.extraTime;
	}

	public void setExtraTime(Integer extraTime) {
		this.extraTime = extraTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean getUnrestricted() {
		return unrestricted;
	}

	public void setUnrestricted(boolean unrestricted) {
		this.unrestricted = unrestricted;
	}

	public Long getContestId() {
		return contestId;
	}

	public void setContestId(Long contestId) {
		this.contestId = contestId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
