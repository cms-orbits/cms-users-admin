package com.cms.users.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
* The persistent class for the PARTICIPATIONS database table.
* 
*/
@Entity
@Table(name="PARTICIPATIONS")
@NamedQuery(name="Participation.findAll", query="SELECT t FROM Participation t")
public class Participation {
	private static final long serialVersionUID = 1L;

	public Participation() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(sequenceName = "participations_id_seq", allocationSize = 1, name = "USER_SEQ")
	private long id;
	
	//private PGcidr ip;
	
	private @Column(name="starting_time")
	Timestamp  startingTime;
	
	//@Column(name="delay_time")
	//private Time delayTime;
	
	//@Column(name="extra_time")
	//private Time extraTime;
	
	private String password;
	
	private boolean hidden;
	
	private boolean unrestricted;
	
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

	public Timestamp getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(Timestamp startingTime) {
		this.startingTime = startingTime;
	}

	/*public Time getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Time delayTime) {
		this.delayTime = delayTime;
	}

	public Time getExtraTime() {
		return extraTime;
	}

	public void setExtraTime(Time extraTime) {
		this.extraTime = extraTime;
	}*/

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
