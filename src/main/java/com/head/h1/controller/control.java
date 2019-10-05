package com.head.h1.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiDevice.Info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.head.h1.fun2.Empl;
import com.head.h1.fun2.detail2;
import com.head.h1.inter.record;
import com.head.h1.inter.record2;
import com.head.h1.fun2.Infos;

@RestController
public class control {
	
@Autowired	
public record r;

public detail2 d=new detail2();
@Autowired
public record2 rs;


public List<Empl> friend(List<Empl> ob,int id){
	List<Empl> a=new ArrayList<>();
	for(int i=0;i<ob.size();i++) {
		Empl e=ob.get(i);
		if(e.getId()!=id) {
			a.add(e);
		}	}
	return a;
}

@RequestMapping(path="/ind/{empid}")
public Map<String, Object> get(@PathVariable("empid") int empid) {
	Map<String,Object> hm=new HashMap<>();

	Empl e=r.findById(empid);
    
	//detail
	String i=e.getName();
    hm.put(i,e);
    int p=e.getPid();
    
    //colleague
    List<Empl> e1=r.findAllByPid(p);
    List<Empl> p2=friend(e1,empid);
    hm.put("colleague", p2);
    
    //child
	List<Empl> e2=r.findAllByPid(empid);
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
public ResponseEntity save(@RequestBody Infos em) {
	if(em.getName()==null || em.getDesi()==null || em.getPid()==null) {
		return new ResponseEntity("fields can't be empty",HttpStatus.BAD_REQUEST);
	}
	Empl e=new Empl();
	e.setName(em.getName());
	e.setPid(em.getPid());
	d=rs.findBydesi(em.getDesi());
	e.setDesi(em.getDesi());
	e.setJid(d);
	Empl e2=r.findById(e.getPid());
	int a=d.getJid();
	int b=e2.getJid().getJid();
	if(a<=b) {
		return new ResponseEntity("designation cannot be same or higher",HttpStatus.BAD_REQUEST);
	}
	
	r.save(e);
	return new ResponseEntity(e,HttpStatus.OK);
	
	}



@DeleteMapping(value="/del/{id}")
public ResponseEntity delete(@PathVariable("id") int id) {
	Empl e=r.findById(id);
	if(e==null) 
	{
	return new ResponseEntity("no such record is present",HttpStatus.BAD_REQUEST);	
	
	}
	
	else {
    int p=e.getPid();
	String s=e.getDesi();
	System.out.print("world");
	if(s.equals("Director")) {
		return new ResponseEntity("invalid record for deletion",HttpStatus.BAD_REQUEST);
	}
	
	List<Empl> l=r.findAllByPid(id);
	for(int i=0;i<l.size();i++) {
		Empl e2=l.get(i);
		e2.setPid(p);
	}

	r.deleteById(id);
	return new ResponseEntity( "record deleted",HttpStatus.OK);
}
}
}
	
