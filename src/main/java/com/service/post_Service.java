package com.service;

import com.f.Designation;
import com.f.Employee;
import com.f.Infos;
import com.inter.Emp_repo;
import com.inter.des_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class post_Service {
    @Autowired
    Emp_repo emprepo;
    @Autowired
    des_repo desrepo;

    Designation designation1;

    public ResponseEntity post_service(Infos em){
         int a = 0;
         float l = 0;
         if(em.getName()==null)
         {
             return new ResponseEntity("No name entered", HttpStatus.BAD_REQUEST);
         }

         if(em.getManagerId()==null)
         {
             return new ResponseEntity("No manager id ",HttpStatus.BAD_REQUEST);
         }

         if(em.getJobTitle()==null)
         {
             return new ResponseEntity("No job title",HttpStatus.BAD_REQUEST);
         }

         if(!em.getName().matches("^[ A-Za-z]+$"))           // to check that name is a valid string
         {
             return new ResponseEntity("Name not found",HttpStatus.BAD_REQUEST);
         }

         List<Employee> emplist = emprepo.findAll();
         if (emplist.isEmpty()) {                                  // to insert first record in table
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

             else {
                 return  new ResponseEntity("No designation before director can be inserted",HttpStatus.BAD_REQUEST);
             }
         }

         if(em.getJobTitle().equals("Director"))           // to not insert another director if already present
         {
             return new ResponseEntity("Director is already present",HttpStatus.BAD_REQUEST);
         }

         Employee e = new Employee();
         if(em.getManagerId()<0){                                        // to check for invalid manager id
             return  new ResponseEntity("Invalid Manager id",HttpStatus.BAD_REQUEST);
         }

         e.setName(em.getName());
         int p = em.getManagerId();
         designation1 = desrepo.findByJobTitle(em.getJobTitle());       // to get record related to entered designation
         Employee e2 = emprepo.findById(p);                             // Get record related to Parent
         if (designation1 == null)
         {
             return new ResponseEntity("Designation  does not exist", HttpStatus.BAD_REQUEST);
         }
         e.setManagerId(em.getManagerId());                             // to set manager id and designation
         e.setJid(designation1);
         a = designation1.getJid();

         if (e2 != null)                                               //  to check whether parent exists or not
         {
             int b = e2.getJid().getJid();
             if (a <= b)
             {
                 return new ResponseEntity("Designation cannot be same or higher", HttpStatus.BAD_REQUEST);
             }
         }
         else
         {
             return  new ResponseEntity("no such record",HttpStatus.BAD_REQUEST);
         }

         emprepo.save(e);
         return new ResponseEntity(e,HttpStatus.CREATED);



     }

}
