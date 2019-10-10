package com.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.f.Empl;

import java.util.List;

@Repository
public interface record extends JpaRepository<Empl, Integer> {
	Empl findById(int id);
	List<Empl> findAllByPidOrderByNameAscDesiAsc(int id);



//List<Empl> findAllbypidOrderByidAscgroupByName(int id);
List<Empl> findAllByPid(int id);
Empl findByPid(int id);
}
