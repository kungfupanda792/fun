package com.head.h1.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.head.h1.fun2.Empl;

import java.util.List;
import java.util.Optional;
@Repository
public interface record extends JpaRepository<Empl, Integer> {
	Empl findById(int id);
//	@Query(value="select details.name,details.desi,details.e_id from details where p_id=?1", nativeQuery = true)

List<Empl> findAllByPid(int id);
//@Query("update details set details.j_id=detail2.j_id from details inner join detail2 on details.desi=detail2")
Empl findByPid(int id);

}
