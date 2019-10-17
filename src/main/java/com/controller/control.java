package com.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.f.Employee;
import com.f.Designation;
import com.inter.record;
import com.inter.record2;
import com.f.Infos;

@RestController
public class control {

	@Autowired
	public record emprepo;
	Designation designation1 = new Designation();

	@Autowired
	public record2 desrepo;
	Designation designation = new Designation();

    // Add data (get call)

	public List<Employee> friend(List<Employee> ob, int id) {
		List<Employee> a = new ArrayList<>();
		for (int i = 0; i < ob.size(); i++) {
			Employee e = ob.get(i);
			if (e.getId() != id) {
				a.add(e); }
		}
		return a;
	}


	// Request and Response for get call

	@GetMapping(path = "/employees/{id}")
	public ResponseEntity get(@PathVariable("id") int empid) {
		Employee e2 = emprepo.findById(empid);
		Map<String, Object> hm = new LinkedHashMap<>();

		if(empid<=0)
		{
			return new ResponseEntity("Invalid Employee Id,can't be zero or -ive",HttpStatus.BAD_REQUEST);
		}

		if(e2==null){
			 return  new ResponseEntity("No Record Exist",HttpStatus.NOT_FOUND);
		}

		//detail
		String g = e2.getName();
		hm.put("employee", e2);
		int p = e2.getManagerId();


		//manager
		Employee e4 = emprepo.findById(p);
		if (e4 == null) {
		} else {
			hm.put("manager", e4);
		}

		//colleague
		List<Employee> e1 = emprepo.findAllByManagerId(p,Sort.by("jid","name").ascending());
		if ((e1 != null)) {
			List<Employee> p2 = friend(e1, empid);
			hm.put("colleagues", p2);
		}

		//child
		List<Employee> e3 = emprepo.findAllByManagerId(empid,Sort.by("jid","name").ascending());
		if (e3 != null) {
			hm.put("subordinates", e3);
		}

		return new ResponseEntity(hm,HttpStatus.OK);
	}


    // Get All Mapping
    // Request and Response for all information

	@RequestMapping (value = "/employees",method = RequestMethod.GET)
	public ResponseEntity getAll()
	{
		Iterable<Employee> l = emprepo.findAllByOrderByJid_lidAscNameAsc();
		return new ResponseEntity(l,HttpStatus.OK);
	}


	//Response and Request for post call
	//Post Mapping

	@PostMapping(path="/employees")
	public ResponseEntity post(@RequestBody Infos em) {
		int a = 0;
		float l = 0;
		if(em.getName()==null)
		{
			return new ResponseEntity("NO name entered",HttpStatus.BAD_REQUEST);
		}
		if(em.getManagerId()==null)
		{
			return new ResponseEntity("No manager id ",HttpStatus.BAD_REQUEST);
		}
		if(em.getJobTitle()==null)
		{
			return new ResponseEntity("No job title",HttpStatus.BAD_REQUEST);
		}

		if(!em.getName().matches("^[ A-Za-z]+$"))
		{
			System.err.println(em.getName());
			return new ResponseEntity(
					"Name not found",HttpStatus.BAD_REQUEST);
		}

		List<Employee> emplist = emprepo.findAll();
		if (emplist.isEmpty()) {
			if (em.getJobTitle().equals("Director")) {
				if(em.getManagerId()==-1) {
					Employee e = new Employee();
					e.setName(em.getName());
					Designation designation = desrepo.findByJobTitle(em.getJobTitle());
					e.setJid(designation);
					e.setManagerId(-1);
					emprepo.save(e);
					return new ResponseEntity("Director Inserted", HttpStatus.CREATED);
				}
				else
				{
					return new ResponseEntity("Director can not have any Manager",HttpStatus.BAD_REQUEST);
				}
			}

			else{
				return  new ResponseEntity("No designation before director can be inserted",HttpStatus.BAD_REQUEST);
			}
		}

		if(em.getJobTitle().equals("Director"))
		{
				return new ResponseEntity("Director is already present",HttpStatus.BAD_REQUEST);
		}


    	/*Employee e1= emprepo.findByJid(g);
    	if(e1.getManagerId()==-1){
    		return new ResponseEntity("only one director can exist",HttpStatus.BAD_REQUEST);
		}*/
			Employee e = new Employee();

			if(em.getManagerId()<0){
				return  new ResponseEntity("Invalid Manager id",HttpStatus.BAD_REQUEST);
			}

		    e.setName(em.getName());
			int p = em.getManagerId();

			// to get record related to entered designation
			designation1 = desrepo.findByJobTitle(em.getJobTitle());

			// Get record related to Parent
			Employee e2 = emprepo.findById(p);

			if (designation1 == null)
			{
				return new ResponseEntity("Designation  does not exist", HttpStatus.BAD_REQUEST);
			}
			e.setManagerId(em.getManagerId());
			e.setJid(designation1);
			a = designation1.getJid();

			if (e2 != null)
			{
				int b = e2.getJid().getJid();
				if (a <= b)
				{
					return new ResponseEntity("Designation cannot be same or higher", HttpStatus.BAD_REQUEST);
				}
			}
			else
			{
				System.err.println(e.getName());
				return  new ResponseEntity("no such record",HttpStatus.BAD_REQUEST);
			}

			emprepo.save(e);
			return new ResponseEntity(e,HttpStatus.CREATED);
		}


    //  Request and Response for delete call
    //  Delete Mapping

    @DeleteMapping(value="/employees/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
		if(id<=0)
		{
			return new ResponseEntity("Invalid id",HttpStatus.BAD_REQUEST);
		}
		Employee e=emprepo.findById(id);

		if(e==null)
		{
			return new ResponseEntity("No such record exist",HttpStatus.NOT_FOUND);
		}

		if(e.getJid().getJobTitle().equals("Director"))
		{
			return new ResponseEntity("Invalid record for deletion, can't delete Director",HttpStatus.BAD_REQUEST);
		}


           int p=e.getManagerId();
		List<Employee> l=emprepo.findAllByManagerId(id,Sort.by("name"));
	       for(int i=0;i<l.size();i++)
	       {
		        Employee e2=l.get(i);
		        e2.setManagerId(p);
	       }
	       emprepo.deleteById(id);
	       return new ResponseEntity(e,HttpStatus.NO_CONTENT);

    }


      // Request and Response for put call
	 //  Put Mapping

    @PutMapping ("/employees/{id}")
    public ResponseEntity put(@RequestBody Infos t,@PathVariable("id") int id)
     {
     	//int u=id;
		 Employee employee=emprepo.findById(id);
		 if(t.getName()==null && t.getJobTitle()==null && t.getManagerId()==null && t.isReplace()==false)
		 {
		    return new ResponseEntity("No Data Entered",HttpStatus.BAD_REQUEST);
		 }
		 System.err.println(t.getManagerId());
		 if(t.getManagerId()<0)
		 {
		    return new ResponseEntity("Invalid ManagerID",HttpStatus.BAD_REQUEST);
		 }

		 if(employee!=null)
		 {
            if(t.isReplace())
               {
            	 if(t.getJobTitle().equals("Director")){
            	 	return new ResponseEntity("Director desi can't be changed",HttpStatus.BAD_REQUEST);
				 }
            	 Designation designation2=desrepo.findByJobTitle(t.getJobTitle());
            	 if(designation2==null)
				 {
					return new ResponseEntity("Designation is not present",HttpStatus.BAD_REQUEST);
				 }

            	 Employee employee1 = new Employee();
            	 float employ=employee.getJid().getLid();
            	 float user=desrepo.findByJobTitle(t.getJobTitle()).getLid();
				 float employ2=emprepo.findById(employee.getManagerId()).getJid().getLid();
            	 if(employ>=user && user>employ2)
				 {   Integer manager=employee.getManagerId();
					emprepo.delete(employee);
					employee1.setName(t.getName());
					employee1.setManagerId(manager);
					employee1.setJid(desrepo.findByJobTitle(t.getJobTitle()));
					emprepo.save(employee1);
					List<Employee> l = emprepo.findAllByManagerId(id, Sort.by("name"));
					for (int i = 0; i < l.size(); i++) {
						Employee e3 = l.get(i);
						e3.setManagerId(employee1.getId());
						emprepo.save(e3);
					}
					return new ResponseEntity(employee1,HttpStatus.OK);
				 }
            	 else {
					return new ResponseEntity("cannot be replaced",HttpStatus.BAD_REQUEST);
				 }
				/*if (t.getManagerId() <= 0) {
					employee1.setManagerId(employee.getManagerId());
				} else {
					employee1.setManagerId(t.getManagerId());
				}*/
               }

            else
            	{
             		Employee employee1=emprepo.findById(id);

				 if(t.getJobTitle()!=null)
				 {
					Designation designation2=desrepo.findByJobTitle(t.getJobTitle());
					if(employee.getJobTitle().equals("Director")){
						return new ResponseEntity("can't change director",HttpStatus.BAD_REQUEST);
					}
					if(designation2==null)
					{
					return new ResponseEntity("No designation present",HttpStatus.BAD_REQUEST);
					}
					float employ=employee1.getJid().getLid();
					float child=0;
					List<Employee> lis=emprepo.findAllByManagerId(id,Sort.by("name"));
					if(lis.size()>0){
						child=lis.get(0).getJid().getLid();
					}
					float self=desrepo.findByJobTitle(t.getJobTitle()).getLid();
					float parent=emprepo.findById(employee.getManagerId()).getJid().getLid();
					if(employ<child && self>parent)
					{
						employee1.setJid(desrepo.findByJobTitle(t.getJobTitle()));
					}
					else
					{
						return new ResponseEntity(HttpStatus.BAD_REQUEST);
					}

				 }
				 if(t.getManagerId()!=null){
                                          employee1.setManagerId(t.getManagerId());
/*
						float employ=employee1.getJid().getLid();
						Employee employee3=emprepo.findByIdOrderById(t.getManagerId());
						String str=employee3.getJid().getJobTitle();
						float parent=desrepo.findByJobTitle(str).getLid();
						if(employ>parent)
						{
							employee1.setJid(desrepo.findByJobTitle(t.getJobTitle()));
						}
						else
						{
							return new ResponseEntity(HttpStatus.BAD_REQUEST);
						}*/
				 }
				 else{
				 employee1.setManagerId(employee.getManagerId());
				 }

				 if(t.getName()!=null){
				 	employee1.setName(t.getName());
				 }
				 emprepo.save(employee1);
				 return new ResponseEntity(employee1,HttpStatus.OK);

            	}}

		          /*if(a<b)
		          {
		              return new ResponseEntity("designation cannot be same or higher",HttpStatus.BAD_REQUEST);
		          }*/


			 return new ResponseEntity("User doesn't exist",HttpStatus.OK);




		 }

     }



	
