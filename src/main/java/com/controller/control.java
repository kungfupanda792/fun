package com.controller;

import java.util.*;

import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.f.Employee;
import com.f.Designation;
import com.inter.Emp_repo;
import com.inter.des_repo;
import com.f.Infos;

@RestController
public class control {

	@Autowired
	public Emp_repo emprepo;
	Designation designation1 = new Designation();
	@Autowired
	public des_repo desrepo;
	Designation designation = new Designation();
	@Autowired
	post_Service serve;
	@Autowired
	put_Service serv_put;
	@Autowired
	get_Service serv_get;
	@Autowired
	delete_Service serv_del;

	// Request and Response for get call

	@GetMapping(path = "/employees/{id}")
	public ResponseEntity get(@PathVariable("id") int empid) {
		return serv_get.get(empid);
	}

    // Get All Mapping
    // Request and Response for all information

	@RequestMapping (value = "/employees",method = RequestMethod.GET)
	public ResponseEntity getAll()
	{
		Iterable<Employee> l = emprepo.findAllByOrderByJid_lidAscNameAsc();   // fetching all records in database
		return new ResponseEntity(l,HttpStatus.OK);
	}

	//Response and Request for post call
	//Post Mapping

	@PostMapping(path="/employees")
	public ResponseEntity post(@RequestBody Infos em) {
        return serve.post_service(em);
		}

    //  Request and Response for delete call
    //  Delete Mapping

	@DeleteMapping(value="/employees/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
		return serv_del.delete(id);
	}

      // Request and Response for put call
	 //  Put Mapping

    @PutMapping ("/employees/{id}")
    public ResponseEntity put(@RequestBody Infos t,@PathVariable("id") int id)
     {
		 return serv_put.put(t,id);
     }
 }



	
