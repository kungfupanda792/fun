package com.f;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;

@Entity
@Table(name="EMPLOYEE")
public class Employee {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="name",nullable = false)
    private String name;

    @JsonProperty("managerId")
    @JsonIgnore
    @Column(name="manager")
    private Integer managerId=-1;

    @OneToOne
    @JoinColumn(name="designation")
    @JsonIgnore
    private Designation jid;

    @JsonIgnore
    @JsonProperty("jobTitle")
    @Transient
    private String jobTitle;


    public Designation getJid()
    {
	  return jid;
    }

    public void setJid(Designation jid)
    {
        this.jid = jid;
    }

     public Employee() {}

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getManagerId()
    {
        return managerId;
    }

    public void setManagerId(int managerId)
    {
        this.managerId = managerId;
    }

    public String getJobTitle()
    {
        return jid.getJobTitle();
    }
}
