package com.head.h1.fun2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="detail2")
public class detail2 {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="j_id")
private int j_id;
@Column(name="desig")
private String desig;
@Column(name="lid")
private float lid;

public int getLid() {
	return lid;
}
public void setLid(int lid) {
	this.lid = lid;
}
public String getDesig() {
	return desig;
}
public void setDesig(String desig) {
	this.desig = desig;
}
public int getJ_id() {
	return j_id;
}
public void setJ_id(int j_id) {
	this.j_id = j_id;
}
	
	
}
