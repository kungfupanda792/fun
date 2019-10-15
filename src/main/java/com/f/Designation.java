package com.f;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table (name="detail2")
public class detail2 {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JoinColumn
	@JsonProperty("id")
	private int jid;

	@Column(name="title")
	private String desi;

	@Column(name="level")
	private float lid;

	public float getLid() {
		return lid;
	}

	public void setLid(float lid) {
	   this.lid = lid;
	}

	public String getDesi() {
	    return desi;
	}

	public void setDesi(String desi) {
	    this.desi = desi;
	}

	public int getJid() {
	return jid;
	}

    public void setJid(int jid) {
	  this.jid = jid;
	}
}
