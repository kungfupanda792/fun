package com.Entity;

/*.......................... Temporary Setter and Getter Table............................*/
public class Infos {

private Integer id=-1;

private String name=null;

private String jobTitle =null;

private Integer managerId=null;
private boolean replace=false;

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	private boolean delete=false;

    public Infos(String name,String jobTitle,Integer managerId,boolean replace)
	{
	   this.name=name;
	   this.jobTitle=jobTitle;
	   this.managerId=managerId;
	   this.replace=replace;
    }

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getJobTitle() {
	return jobTitle;
}
public void setJobTitle(String jobTitle) {
	this.jobTitle = jobTitle;
}
public Integer getManagerId() {
	return managerId;
}
public void setManagerId(int managerId) {
	this.managerId = managerId;
}
public boolean isReplace() {
	return replace;
}
public void setReplace(boolean replace) {
	this.replace = replace;
}

}
