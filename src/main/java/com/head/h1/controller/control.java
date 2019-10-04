package com.head.h1.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.head.h1.fun2.Empl;
import com.head.h1.inter.record;

@RestController
public class control {
	
@Autowired	
public record r;

public List<Empl> friend(List<Empl> ob,int id){
	List<Empl> a=new ArrayList<>();
	for(int i=0;i<ob.size();i++) {
		Empl e=ob.get(i);
		if(e.getId()!=id) {
			a.add(e);
		}
	}
	return a;
}

@RequestMapping(path="/ind/{empid}")
public Map<String, Object> get(@PathVariable("empid") int empid) {
	Map<String,Object> hm=new HashMap<>();

	Empl e=r.findById(empid);
    String i=e.getName();
    hm.put(i,e);
    int p=e.getPid();
    
    //colleague
    List<Empl> e1=r.findAllBypid(p);
    List<Empl> p2=friend(e1,empid);
    hm.put("colleague", p2);
    
    //child
	List<Empl> e2=r.findAllBypid(empid);
	hm.put("Reportees", e2);
	
	//manager
	Empl e4=r.findById(p);
	hm.put("manager", e4);
			
			
    return hm;
}

@RequestMapping("/all")
public List<Empl> getAll() {
	return r.findAll();
}


@PostMapping(path="/mera",consumes= {"application/json"})
public Empl save(@RequestBody Empl em) {
r.save(em);
return em;
}

@DeleteMapping(value="/del/{id}")
public String  delete(@PathVariable("id") int id) {
	
	
	r.deleteById(id);
	return "deleted";
}

}
	
