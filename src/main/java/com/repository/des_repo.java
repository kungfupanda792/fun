package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Entity.Designation;

public interface des_repo extends JpaRepository<Designation, Integer> {
    Designation findByJobTitle(String str);
}
