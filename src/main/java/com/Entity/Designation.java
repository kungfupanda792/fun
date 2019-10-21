package com.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/* ......................... DESIGNATION TABLE...................*/
@Entity
@Table (name="Designation")
public class Designation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JoinColumn
	@JsonProperty("id")
	private Integer jid;                                             //  Foreign key

	@Column(name="jobTitle")
	private String jobTitle;

	@Column(name="level")
	private float lid;

	public float getLid() {
		return lid;
	}

	public void setLid(float lid) {
	   this.lid = lid;
	}

	public String getJobTitle() {
	    return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
	    this.jobTitle = jobTitle;
	}

	public Integer getJid() {
	return jid;
	}

    public void setJid(Integer jid) {
	  this.jid = jid;
	}
}
