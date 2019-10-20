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
    private Integer managerId=null;

    @OneToOne
    @JoinColumn(name="designation")
    @JsonIgnore
    private Designation jid;                                //  Foreign Key with other table

    @JsonIgnore
    @JsonProperty("jobTitle")
    @Transient
    private String jobTitle;

    public Employee() {}

    public Designation getJid() {
        return jid;
    }

    public void setJid(Designation jid) {
        this.jid = jid;
    }

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

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getJobTitle() {
        return jid.getJobTitle();
    }
}
