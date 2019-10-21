package com.service;

import com.Entity.Employee;
import com.MessageUtil;
import com.repository.Emp_repo;
import com.repository.des_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class delete_Service {
    @Autowired
    Emp_repo emprepo;
    @Autowired
    des_repo desrepo;
    @Autowired
    MessageUtil message;
    public ResponseEntity delete(int id){
        if(id<=0)                                                // to check for invalid id
        {
            return new ResponseEntity(message.getMessage("invalid_id"), HttpStatus.BAD_REQUEST);
        }

        Employee e=emprepo.findById(id);
        if(e==null)                                              // to check whether such employee exist or not
        {
            return new ResponseEntity(message.getMessage("no_record"),HttpStatus.NOT_FOUND);
        }

        if(e.getJid().getJobTitle().equals("Director"))          // to check if director is getting deleted
        {
            return new ResponseEntity(message.getMessage("delete_director"),HttpStatus.BAD_REQUEST);
        }

        int p=e.getManagerId();
        List<Employee> l=emprepo.findAllByManagerId(id, Sort.by("name"));   // to find all record having this id as parent
        for(int i=0;i<l.size();i++)                                     // to update all subordinates parent
        {
            Employee e2=l.get(i);
            e2.setManagerId(p);
        }
        emprepo.deleteById(id);                                         // to delete that particular record
        return new ResponseEntity(message.getMessage("record_deleted"),HttpStatus.NO_CONTENT);
    }
}
