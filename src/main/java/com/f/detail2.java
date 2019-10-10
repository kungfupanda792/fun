package com.f;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="detail2")
public class detail2 {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@JoinColumn
private int jid;

@Column(name="desi")
private String desi;

@Column(name="lid")
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
public void setDesi(String desig) {
	this.desi = desig;
}
public int getJid() {
	return jid;
}
public void setJ_id(int j_id) {
	this.jid = jid;
}}
