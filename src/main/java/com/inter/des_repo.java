package com.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import com.f.Designation;

public interface des_repo extends JpaRepository<Designation, Integer> {
    Designation findByJobTitle(String str);
}
