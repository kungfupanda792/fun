package com.service;


import com.MessageUtil;
import com.controller.control;
import com.Entity.Designation;
import com.Entity.Employee;
import com.Entity.Infos;
import com.repository.Emp_repo;
import com.repository.des_repo;
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
    @Autowired
    get_Service serv_get;
    @Autowired
    MessageUtil message;
    public ResponseEntity put(Infos t,int id){
        Employee employee=emprepo.findById(id);
        Employee employee1 = new Employee();

        if(employee==null || t==null)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if(employee.getJobTitle().equals("Director"))
        {
            return new ResponseEntity(message.getMessage("update_director_designation"),HttpStatus.BAD_REQUEST);
        }

        if(t.getName()==null && t.getJobTitle()==null )
        {
            return new ResponseEntity(message.getMessage("no_data"),HttpStatus.BAD_REQUEST);
        }

        if(t.isReplace())
        {
            Designation designation2=desrepo.findByJobTitle(t.getJobTitle());
            if(designation2==null) {
                return new ResponseEntity(message.getMessage("designation_not_present"),HttpStatus.BAD_REQUEST);
            }

            if(t.getManagerId()==null){
                  Integer par=employee.getManagerId();
                  employee1.setName(t.getName());
                  employee1.setManagerId(par);
                  employee1.setJid(desrepo.findByJobTitle(t.getJobTitle()));
                  emprepo.save(employee1);
                  int emp=employee1.getId();
                  List<Employee> ls=emprepo.findAllByManagerId(id,Sort.by("name"));
                  for(int i=0;i<ls.size();i++){
                      Employee employee2=ls.get(i);
                      employee2.setManagerId(emp);
                  }
                  emprepo.deleteById(id);
                  return serv_get.get(employee1.getId());
            }

            if(t.getManagerId()<-1){
                return  new ResponseEntity(message.getMessage("invalid_manager"),HttpStatus.BAD_REQUEST);
            }

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
                return new ResponseEntity(message.getMessage("cannot_replace"),HttpStatus.BAD_REQUEST);
            }

        }

        else
        {

            Employee employee3=emprepo.findById(id);

            if(t.getJobTitle()!=null)
            {
                Designation designation2=desrepo.findByJobTitle(t.getJobTitle());
                if(designation2==null)
                {
                    return new ResponseEntity(message.getMessage("designation_not_present"),HttpStatus.BAD_REQUEST);
                }
                float employ=employee3.getJid().getLid();
                float child=0;
                List<Employee> lis=emprepo.findAllByManagerId(id,Sort.by("jid"));
                if(lis.size()>0){
                    child=lis.get(0).getJid().getLid();
                }
                float self=desrepo.findByJobTitle(t.getJobTitle()).getLid();
                float parent=emprepo.findById(employee.getManagerId()).getJid().getLid();
                if(self<child && self>parent)
                {
                    employee3.setJid(desrepo.findByJobTitle(t.getJobTitle()));
                }
                else
                {
                    return new ResponseEntity(message.getMessage("replace"),HttpStatus.BAD_REQUEST);
                }
            }

            else
            {
                employee3.setJid(employee.getJid());
            }
            emprepo.save(employee3);

            if(t.getManagerId()!=null){

                employee3.setManagerId(t.getManagerId());
            }
            else{
                employee3.setManagerId(employee.getManagerId());
            }

            if(t.getName()!=null){
                employee3.setName(t.getName());
            }
            else
            {
                employee3.setName(employee.getName());
            }
            emprepo.save(employee3);
            return con.get(employee3.getId());

        }
    }
}
