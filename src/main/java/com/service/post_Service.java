package com.service;

import com.Entity.Designation;
import com.Entity.Employee;
import com.Entity.Infos;
import com.MessageUtil;
import com.repository.Emp_repo;
import com.repository.des_repo;
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
    @Autowired
    MessageUtil message;

    public ResponseEntity post_service(Infos em){
         int a = 0;
         float l = 0;
         if(em.getName()==null)
         {
             return new ResponseEntity(message.getMessage("no_name"), HttpStatus.BAD_REQUEST);
         }

         if(em.getManagerId()==null)
         {
             return new ResponseEntity(message.getMessage("no_manager"),HttpStatus.BAD_REQUEST);
         }

         if(em.getJobTitle()==null)                                                    // to check that jobtitle is entered
         {
             return new ResponseEntity(message.getMessage("no_designation"),HttpStatus.BAD_REQUEST);
         }

         if(!em.getName().matches("^[ A-Za-z]+$"))                               // to check that name is a valid string
         {
             return new ResponseEntity(message.getMessage("invalid_name"),HttpStatus.BAD_REQUEST);
         }

         List<Employee> emplist = emprepo.findAll();
         if (emplist.isEmpty()) {                                                       // to insert first record in table
             if (em.getJobTitle().equals("Director")) {
                 if(em.getManagerId()==-1) {
                     Employee e = new Employee();
                     e.setName(em.getName());
                     Designation designation = desrepo.findByJobTitle(em.getJobTitle());
                     e.setJid(designation);
                     e.setManagerId(-1);
                     emprepo.save(e);
                     return new ResponseEntity(message.getMessage("director_inserted"), HttpStatus.CREATED);
                 }
                 else
                 {
                     return new ResponseEntity(message.getMessage("no_manager_director"),HttpStatus.BAD_REQUEST);
                 }
             }

             else {
                 return  new ResponseEntity(message.getMessage("empty_table"),HttpStatus.BAD_REQUEST);
             }
         }

         if(em.getJobTitle().equals("Director"))                          // to not insert another director if already present
         {
             return new ResponseEntity(message.getMessage("director_present"),HttpStatus.BAD_REQUEST);
         }

         Employee e = new Employee();
         if(em.getManagerId()<0){                                        // to check for invalid manager id
             return  new ResponseEntity(message.getMessage("invalid_manager"),HttpStatus.BAD_REQUEST);
         }

         e.setName(em.getName());
         int p = em.getManagerId();
         designation1 = desrepo.findByJobTitle(em.getJobTitle());       // to get record related to entered designation
         Employee e2 = emprepo.findById(p);                             // Get record related to Parent
         if (designation1 == null)
         {
             return new ResponseEntity(message.getMessage("designation_not_present"), HttpStatus.BAD_REQUEST);
         }
         e.setManagerId(em.getManagerId());                             // to set manager id and designation
         e.setJid(designation1);
         a = designation1.getJid();

         if (e2 != null)                                               //  to check whether parent exists or not
         {
             int b = e2.getJid().getJid();
             if (a <= b)
             {
                 return new ResponseEntity(message.getMessage("designation_replace"), HttpStatus.BAD_REQUEST);
             }
         }
         else
         {
             return  new ResponseEntity(message.getMessage("no_record"),HttpStatus.BAD_REQUEST);
         }

         emprepo.save(e);                                            // record saved
         return new ResponseEntity(e,HttpStatus.CREATED);
    }

}
