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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class get_Service {
    @Autowired
    Emp_repo emprepo;
    @Autowired
    des_repo desRepo;
    @Autowired
    MessageUtil message;

    public List<Employee> friend(List<Employee> ob, int id) {                            // Add data (get call)
        List<Employee> a = new ArrayList<>();
        for (int i = 0; i < ob.size(); i++) {
            Employee e = ob.get(i);
            if (e.getId() != id) {
                a.add(e); }
        }
        return a;
    }

    public ResponseEntity get(int empid){
        Employee e2 = emprepo.findById(empid);
        Map<String, Object> hm = new LinkedHashMap<>();
        if(empid<=0) {                                                       //  to check for invalid id
            return new ResponseEntity(message.getMessage("invalid_id"), HttpStatus.BAD_REQUEST);
        }

        if(e2==null){                                                       // to check if such record exists or not
            return  new ResponseEntity(message.getMessage("no_record"),HttpStatus.NOT_FOUND);
        }

        //detail
        String g = e2.getName();
        hm.put("employee", e2);                                             // to add employee data in map
        int p = e2.getManagerId();

        //manager
        Employee e4 = emprepo.findById(p);
        if (e4 == null) {
        } else {
            hm.put("manager", e4);                                           // to add manager record in map
        }

        //colleague
        List<Employee> e1 = emprepo.findAllByManagerId(p, Sort.by("jid","name").ascending());  // to fetch record having same parent as current
        if ((e1 != null)) {
            List<Employee> p2 = friend(e1, empid);
            hm.put("colleagues", p2);
        }

        //child
        List<Employee> e3 = emprepo.findAllByManagerId(empid,Sort.by("jid","name").ascending());  // to fetch all child of current employee
        if (e3 != null) {
            hm.put("subordinates", e3);
        }
        return new ResponseEntity(hm,HttpStatus.OK);
    }
}
