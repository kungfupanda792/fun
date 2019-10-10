package com.f;


import com.f.detail2;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="details")
public class Empl {
    @Id
    @Column(name="e_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
    @Column(name="Name",nullable = false)
private String name;
    @Column(name="desi",nullable = false)
    private String desi ;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name="jid")
public detail2 jid;

    public detail2 getJid() {
	return jid;
}

public void setJid(detail2 jid) {
	this.jid = jid;
}
 @JsonIgnore
	@Column(name="pid")
 private int pid;
 
 public Empl() {}
 
 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesi() {
        return desi;
    }

    public void setDesi(String desi) {
        this.desi = desi;
    }



    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
