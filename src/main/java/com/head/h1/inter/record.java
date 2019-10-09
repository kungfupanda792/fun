package com.head.h1.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.head.h1.fun2.Empl;

import java.util.List;
import java.util.Optional;
@Repository
public interface record extends JpaRepository<Empl, Integer> {
	Empl findById(int id);
	List<Empl> findAllByPidOrderByNameAscDesiAsc(int id);
	List<Empl> findAll(int id);


//List<Empl> findAllbypidOrderByidAscgroupByName(int id);
List<Empl> findAllByPid(int id);
Empl findByPid(int id);
}
