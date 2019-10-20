package com.service;


import com.controller.control;
import com.f.Designation;
import com.f.Employee;
import com.f.Infos;
import com.inter.Emp_repo;
import com.inter.des_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class put_Service {


    @Autowired
    Emp_repo emprepo;
    @Autowired
    des_repo desrepo;
    @Autowired
    control con;
    public ResponseEntity put(Infos t,int id){
        Employee employee=emprepo.findById(id);
        if(employee==null)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if(employee.getJobTitle().equals("Director"))
        {
            return new ResponseEntity("can't change director",HttpStatus.BAD_REQUEST);
        }

        if(t.getName()==null && t.getJobTitle()==null && t.getManagerId()==-1 )
        {
            return new ResponseEntity("No Data Entered",HttpStatus.BAD_REQUEST);
        }
        if(t.isReplace())
        {
            Designation designation2=desrepo.findByJobTitle(t.getJobTitle());
            if(designation2==null) {
                return new ResponseEntity("Designation is not present",HttpStatus.BAD_REQUEST);
            }

            if(t.getManagerId()<-1 || t.getManagerId()==null){
                return  new ResponseEntity("Not Valid Manager Id ",HttpStatus.BAD_REQUEST);
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
                return con.get(employee1.getId());
            }
            else {
                return new ResponseEntity("cannot be replaced",HttpStatus.BAD_REQUEST);
            }

        }

        else
        {
            Employee employee1=emprepo.findById(id);

            if(t.getJobTitle()!=null)
            {
                Designation designation2=desrepo.findByJobTitle(t.getJobTitle());
                if(designation2==null)
                {
                    return new ResponseEntity("No designation present",HttpStatus.BAD_REQUEST);
                }
                float employ=employee1.getJid().getLid();
                float child=0;
                List<Employee> lis=emprepo.findAllByManagerId(id,Sort.by("jid"));
                if(lis.size()>0){
                    child=lis.get(0).getJid().getLid();
                }
                float self=desrepo.findByJobTitle(t.getJobTitle()).getLid();
                float parent=emprepo.findById(employee.getManagerId()).getJid().getLid();
                if(self<child && self>parent)
                {
                    employee1.setJid(desrepo.findByJobTitle(t.getJobTitle()));
                }
                else
                {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }

            else
            {
                employee1.setJid(employee.getJid());
            }
            emprepo.save(employee1);

            if(t.getManagerId()!=null){

                employee1.setManagerId(t.getManagerId());
            }
            else{
                employee1.setManagerId(employee.getManagerId());
            }

            if(t.getName()!=null){
                employee1.setName(t.getName());
            }
            else
            {
                employee1.setName(employee.getName());
            }
            emprepo.save(employee1);
            return con.get(employee1.getId());

        }
    }
}
