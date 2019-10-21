package com.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Entity.Employee;

import java.util.List;

@Repository
public interface Emp_repo extends JpaRepository<Employee, Integer> {
	Employee findById(int id);
	Employee findByIdOrderById(Integer id);
	Employee findByManagerId(Integer i);
	List<Employee> findAllByManagerId(int id,Sort w);
    List<Employee> findAll(Sort s);
    List<Employee>  findAllByOrderByJid_lidAscNameAsc();

}
