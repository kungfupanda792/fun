package com.head.h1.controller;

import java.util.*;

import javax.sound.midi.MidiDevice.Info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
detail2 d2=new detail2();
// Add data (get call)

public List<Empl> friend(List<Empl> ob,int id){
	List<Empl> a=new ArrayList<>();
	for(int i=0;i<ob.size();i++) {
		Empl e=ob.get(i);
		if(e.getId()!=id) {
			a.add(e);
		}	}
	return a;
}


// Request and Response for get call

@GetMapping(path="/rest/employees/get/{empid}")
public Map<String, Object> get(@PathVariable("empid") int empid) {
	Map<String,Object> hm=new LinkedHashMap<>();
	Empl e=r.findById(empid);
	if(e==null) {
		hm.put("No such record exists",e);
		return hm;
	}



	//detail

	String i=e.getName();
    hm.put(i,e);
    int p=e.getPid();


	//manager

	Empl e4=r.findById(p);
	if(e4==null){}
	else { hm.put("manager", e4);}


	//colleague

    List<Empl> e1=r.findAllByPidOrderByIdAscNameAsc(p);
	if(e1==null) {
		List<Empl> p2 = friend(e1, empid);
		if (p2 == null) {
		} else {
			hm.put("colleague", p2);
		}
	}
    //child

	List<Empl> e2=r.findAllByPidOrderByIdAscNameAsc(empid);
    if(e2==null){}
	else{hm.put("Reportees", e2);}


    return hm;
}


//Get Mapping

@GetMapping("/rest/all")
public List<Empl> getAll() {
	List<Empl> l=r.findAll();
	return l;
}


//Response and Request for post call

//Post Mapping

@PostMapping(path="/rest/post",consumes= {"application/json"})
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



// Request and Response for delete call

// Delete Mapping

@DeleteMapping(value="/rest/del/{id}")
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


// Request and Response for put call

// Put Mapping

@PutMapping ("/rest/put/{id}")
public ResponseEntity put(@RequestBody Infos t,@PathVariable("id") int id) {


//	int u=id;
 if(t.isReplace()) {
		Empl e=r.findById(id);

	 Empl e2=new Empl();
	 e2.setName(t.getName());
	 if(!t.getDesi().equals(e.getDesi())){ }
	 e2.setDesi(t.getDesi());
	 e2.setPid(e.getPid());
	 d2=rs.findBydesi(t.getDesi());
	 e2.setJid(d2);
	 r.save(e2);
	 int a=e2.getId();

	 List<Empl> l=r.findAllByPid(id);
	 for(int i=0;i<l.size();i++)
	 {
		 Empl e3=l.get(i);
		 e3.setPid(a);
	 }
	 r.deleteById(id);

	 return new ResponseEntity("Record Replaced",HttpStatus.OK);
	 }


 else {
	 if(t==null) {
		 return new ResponseEntity("no data entered ? Insert Data",HttpStatus.BAD_REQUEST);
	 }

	 /*e.setName(n.getName());
	 e.setPid(n.getPid());
	 d2=rs.findBydesi(n.getDesi());
	 e.setDesi(n.getDesi());
	 e.setJid(d2);
	 Empl e2=r.findByid(e.getPid());
		int a=d.getJid();
		int b=e2.getJid().getJid();
		if(a<=b) {
			return new ResponseEntity("designation cannot be same or higher",HttpStatus.BAD_REQUEST);
		}
	*/


	 return new ResponseEntity("Record inserted",HttpStatus.OK);
 }
 }
}

	
